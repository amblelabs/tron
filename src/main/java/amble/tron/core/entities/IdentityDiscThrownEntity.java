package amble.tron.core.entities;

import amble.tron.core.TronEntities;
import amble.tron.core.TronItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Identity Disc with clear, physically consistent per-tick integration and bounce handling.
 *
 * Key choices and invariants:
 * - Integration: semi-implicit (symplectic) Euler per tick. Apply acceleration, then update position via base class.
 * - Gravity is an acceleration (units/tick^2) and applied independent of mass.
 * - Linear drag is an exponential-style damping applied to full velocity each tick.
 * - On block collision, velocity is decomposed into normal and tangential components:
 *     * Normal component is reversed and scaled by restitution (v_n' = -restitution * v_n) only when moving into the surface.
 *     * Tangential component is reduced by a tangential damping factor (simple friction model).
 * - Disc settles when surface contact or kinetic energy falls below a small threshold.
 * - Spin is independent and damped multiplicatively per tick.
 */
public class IdentityDiscThrownEntity extends PersistentProjectileEntity {

    private static final TrackedData<Boolean> IN_GROUND = DataTracker.registerData(IdentityDiscThrownEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Vector3f> COLOR = DataTracker.registerData(IdentityDiscThrownEntity.class, TrackedDataHandlerRegistry.VECTOR3F);

    private ItemStack discStack;
    private boolean dealtDamage = false;

    // PHYSICS PARAMETERS (tweak as needed)
    private final double mass = 1.0;                   // (kept for future impulses) [unused for gravity here]
    private final double gravity = -0.04;              // acceleration (units / tick^2) downward
    private final double linearDrag = 0.02;            // fraction removed per tick (0..1)
    private final double restitution = 0.45;           // bounciness (0..1)
    private final double tangentialDamping = 0.60;     // fraction of tangential velocity removed on contact (0..1)
    private final double settleSpeedThreshold = 0.01;  // speed below which entity will settle
    private final int maxBouncesBeforeSettle = 6;      // safety cap to stop prolonged bouncing

    // rotational state
    private float spin = 0.0f;                         // angular speed (arbitrary units)
    private float spinDamping = 0.98f;                 // multiplicative damping per tick (close to 1)

    // bounce tracking
    private int bounceCount = 0;

    // tracking list (kept minimal)
    private final List<Entity> trackedTargets = new ArrayList<>();

    public IdentityDiscThrownEntity(EntityType<IdentityDiscThrownEntity> entityType, World world) {
        super(entityType, world);
        this.discStack = new ItemStack(TronItems.IDENTITY_DISC);
    }

    public IdentityDiscThrownEntity(World world, LivingEntity owner) {
        super(TronEntities.IDENTITY_DISC, owner, world);
        this.discStack = new ItemStack(TronItems.IDENTITY_DISC);
    }

    public IdentityDiscThrownEntity(World world, double x, double y, double z) {
        super(TronEntities.IDENTITY_DISC, x, y, z, world);
        this.discStack = new ItemStack(TronItems.IDENTITY_DISC);
    }

    public IdentityDiscThrownEntity(World world, PlayerEntity player, ItemStack itemStack) {
        super(TronEntities.IDENTITY_DISC, player, world);
        this.discStack = itemStack;
        this.spin = 20.0f + this.random.nextFloat() * 40.0f;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(IN_GROUND, false);
        this.dataTracker.startTracking(COLOR, new Vector3f(1, 1, 1));
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.asItemStack();
        return itemStack.isEmpty() ? ParticleTypes.ELECTRIC_SPARK : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }

    public void setInGround(boolean value) {
        this.dataTracker.set(IN_GROUND, value);
    }

    public boolean isInGroundTracked() {
        return this.dataTracker.get(IN_GROUND);
    }

    public void setColor(Vector3f color) {
        this.dataTracker.set(COLOR, color);
    }

    public Vector3f getColor() {
        return this.dataTracker.get(COLOR);
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    public ItemStack asItemStack() {
        return TronItems.IDENTITY_DISC.getDefaultStack();
    }

    @Override
    public void onDataTrackerUpdate(List<DataTracker.SerializedEntry<?>> dataEntries) {
        super.onDataTrackerUpdate(dataEntries);
        // Keep local state consistent with tracked color (no logic here beyond sync).
        this.setColor(this.getDataTracker().get(COLOR));
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity target = entityHitResult.getEntity();
        if (target == this.getOwner()) return;

        Entity owner = this.getOwner();
        DamageSource damageSource = this.getDamageSources().thrown(this, owner == null ? this : owner);
        float baseDamage = 7.0f;

        if (target instanceof LivingEntity living) {
            baseDamage += EnchantmentHelper.getAttackDamage(this.discStack, living.getGroup());
        }

        if (target.damage(damageSource, baseDamage)) {
            if (target instanceof LivingEntity livingTarget && owner instanceof LivingEntity) {
                EnchantmentHelper.onUserDamaged(livingTarget, owner);
                EnchantmentHelper.onTargetDamaged((LivingEntity) owner, livingTarget);
                this.onHit(livingTarget);
            }
            this.dealtDamage = true;
        }

        // Reduce speed after hitting an entity to simulate energy loss
        Vec3d prevVel = this.getVelocity();
        this.setVelocity(prevVel.multiply(0.55)); // lose ~45% of speed on impact with entity
        this.playSound(getHitSound(), 1.0F, 1.0F);
    }

    @Override
    public void tick() {

        if (!this.getWorld().isClient() && this.getOwner() != null) {
            if (this.getOwner() instanceof PlayerEntity player) {
                Vec3d vec3d = player.getCameraPosVec(1.0f);
                Vec3d vec3d2 = player.getRotationVec(1.0F);
                Vec3d vec3d3 = vec3d.add(vec3d2.x * 10, vec3d2.y * 10, vec3d2.z * 10);
                Box box = player.getBoundingBox().stretch(vec3d2.multiply(10)).expand(1.0, 1.0, 1.0);

                EntityHitResult entityHitResult = ProjectileUtil.raycast(player, vec3d, vec3d3, box, (entity) -> entity == this, 1000);
                if (entityHitResult != null) {
                    if (player.isSneaking()) {
                        this.tryPickup(player);
                        this.discard();
                    }
               }
            }
        }
        // Ensure tracked flag stays in-sync with internal state
        this.setInGround(this.inGround);

        // If settled, keep minimal behavior and allow pickup
        if (this.isInGroundTracked()) {
            this.spin *= spinDamping;
            if (this.getWorld().isClient && this.random.nextInt(20) == 0) {
                this.getWorld().addParticle(ParticleTypes.CRIT, this.getX(), this.getY() + 0.1, this.getZ(),
                        0.0, 0.0, 0.0);
            }
            super.tick();
            return;
        }

        // --- PHYSICS INTEGRATION: Semi-implicit Euler (symplectic) ---
        // 1) Apply accelerations to velocity (gravity)
        Vec3d vel = this.getVelocity();
        Vec3d accel = new Vec3d(0.0, gravity, 0.0); // gravity is negative for downward acceleration
        vel = vel.add(accel); // dt = 1 tick

        // 2) Apply linear drag (simple multiplicative damping)
        double dragFactor = Math.max(0.0, 1.0 - linearDrag); // clamp to prevent negative
        vel = vel.multiply(dragFactor);

        // 3) Update spin (purely visual/rotational, not affecting translation)
        this.spin *= spinDamping;
        if (Math.abs(this.spin) < 0.01f) this.spin = 0.0f;

        // 4) Commit velocity and let base class handle movement and collisions
        this.setVelocity(vel);
        super.tick();

        // After movement/collision callbacks, evaluate settling conditions:
        Vec3d postVel = this.getVelocity();
        double speedSq = postVel.lengthSquared();

        // If on ground or very slow, settle and zero velocity
        if (this.isOnGround() || speedSq < settleSpeedThreshold * settleSpeedThreshold) {
            this.inGround = true;
            this.setInGround(true);
            this.setVelocity(Vec3d.ZERO);
            this.spin = 0.0f;
            return;
        }

        // spawn particles client-side while flying
        if (this.getWorld().isClient) {
            Vec3d pos = this.getPos();
            this.getWorld().addParticle(getParticleParameters(), pos.x, pos.y, pos.z,
                    postVel.x * 0.1, postVel.y * 0.1, postVel.z * 0.1);
        }

        // Note: return-to-owner is intentionally disabled; placeholder left for future use.
    }

    /**
     * Consistent bounce handling:
     * - Decompose current velocity into normal and tangential parts relative to the collision normal.
     * - If the disc is moving into the surface (v_n < 0), reverse and scale normal component by restitution.
     * - Reduce tangential component by a tangential damping factor (friction approximation).
     * - If after-bounce kinetic energy is below threshold or bounce count exceeded, settle on surface.
     */
    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (this.isRemoved()) return;

        Vec3d vel = this.getVelocity();
        Direction side = blockHitResult.getSide();
        Vec3d normal = new Vec3d(side.getOffsetX(), side.getOffsetY(), side.getOffsetZ()).normalize();

        // Project velocity onto normal (v_n) and tangential (v_t = v - v_n * n)
        double v_n = vel.dotProduct(normal); // positive when moving in direction of normal
        Vec3d normalComponent = normal.multiply(v_n);
        Vec3d tangential = vel.subtract(normalComponent);

        // Ensure the entity is not marked as grounded before applying bounce response
        this.inGround = false;
        this.setInGround(false);

        // Local bounce parameters
        double restitutionLocal;
        double tangentialFactor;
        double globalDamping = 0.995; // slight global damping to avoid perpetual micro-bounce

        if (side.getAxis() == Direction.Axis.Y) {
            if (side == Direction.DOWN) {
                // floor bounce: allow bounce (don't discard) and keep it slightly bouncier
                restitutionLocal = Math.min(1.0, restitution * 1.2);
                tangentialFactor = 0.85;
            } else {
                // ceiling
                restitutionLocal = restitution;
                tangentialFactor = Math.max(0.0, 1.0 - tangentialDamping);
            }
        } else {
            // walls: be bouncier and preserve tangential speed
            restitutionLocal = Math.min(1.0, restitution * 1.35);
            tangentialFactor = 0.9;
        }

        Vec3d newNormalComp;
        if (v_n < 0) {
            // moving into surface: reflect with restitution
            double v_n_after = -v_n * restitutionLocal;
            newNormalComp = normal.multiply(v_n_after);
            this.bounceCount++;
        } else {
            // moving away from surface: keep normal part
            newNormalComp = normalComponent;
        }

        // Apply tangential damping (friction) and global damping
        Vec3d newTangential = tangential.multiply(tangentialFactor);
        Vec3d after = newNormalComp.add(newTangential).multiply(globalDamping);

        // If speed is below threshold or too many bounces, settle
        if (after.lengthSquared() < settleSpeedThreshold * settleSpeedThreshold || this.bounceCount > maxBouncesBeforeSettle) {
            this.setVelocity(Vec3d.ZERO);
            this.inGround = true;
            this.setInGround(true);
            this.spin = 0.0f;
            this.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.01f, 1.0F);
        } else {
            this.setVelocity(after);
            this.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.01f, 0.9F + this.random.nextFloat() * 0.2F);
        }

        // Call super after applying custom bounce so base logic doesn't prematurely zero velocity.
        //super.onBlockHit(blockHitResult);
    }

    private boolean isOwnerAlive() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    public boolean isInGround() {
        return this.inGround;
    }

    @Nullable
    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        return super.getEntityCollision(currentPosition, nextPosition);
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        boolean allowed = switch (this.pickupType) {
            case ALLOWED -> {
                boolean isFull = player.getInventory().getEmptySlot() == -1;
                boolean inserted = !player.isCreative() && !isFull && player.getInventory().insertStack(this.asItemStack());
                if (!inserted && !player.isCreative() && !this.isRemoved() && !isFull) {
                    this.dropStack(this.asItemStack(), 0.1F);
                    this.discard();
                }
                yield inserted;
            }
            case CREATIVE_ONLY -> player.getAbilities().creativeMode;
            default -> false;
        };
        return this.isInGroundTracked() && (allowed || player.isCreative()) || this.isOwner(player);
    }

    protected SoundEvent getHitSound() {
        return SoundEvents.BLOCK_WOOL_FALL;
    }

    public void onPlayerCollision(PlayerEntity player) {
        if ((this.isOwner(player) || this.getOwner() == null) && !this.getWorld().isClient) {
            if ((this.isInGroundTracked() /*|| this.isNoClip()*/) && this.shake <= 0) {
                if (this.tryPickup(player)) {
                    this.discard();
                }
            }
        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("disc", 10)) {
            this.discStack = ItemStack.fromNbt(nbt.getCompound("disc"));
        }

        this.dealtDamage = nbt.getBoolean("DealtDamage");
        if (nbt.contains("InGround")) {
            this.setInGround(nbt.getBoolean("InGround"));
            this.inGround = nbt.getBoolean("InGround");
        }

        if (nbt.contains("BounceCount")) {
            this.bounceCount = nbt.getInt("BounceCount");
        }
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("disc", this.discStack.writeNbt(new NbtCompound()));
        nbt.putBoolean("DealtDamage", this.dealtDamage);
        nbt.putBoolean("InGround", this.isInGroundTracked());
        nbt.putInt("BounceCount", this.bounceCount);
    }

    protected float getDragInWater() {
        return 0.99F;
    }

    public void age() {
        if (this.pickupType != PickupPermission.ALLOWED) {
            super.age();
        }
    }

    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    /**
     * Placeholder for future return-to-owner steering. Left intentionally empty.
     */
    private void attemptReturnToOwner() {
        // Intentionally blank: physics-first disc does not auto-return.
    }
}