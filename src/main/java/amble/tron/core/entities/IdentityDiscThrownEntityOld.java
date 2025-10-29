package amble.tron.core.entities;

import amble.tron.core.TronEntities;
import amble.tron.core.TronItems;
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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.List;

/**
 * Identity Disc with clear, physically consistent per-tick integration and bounce handling.
 * <p>
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
public class IdentityDiscThrownEntityOld extends PersistentProjectileEntity {

    private static final TrackedData<Vector3f> COLOR = DataTracker.registerData(IdentityDiscThrownEntityOld.class, TrackedDataHandlerRegistry.VECTOR3F);

    // PHYSICS PARAMETERS (tweak as needed)
    private static final double gravity = -0.04;              // acceleration (units / tick^2) downward
    private static final Vec3d GRAVITY_VEC = new Vec3d(0, gravity, 0);

    private static final double LINEAR_DRAG = 0.02;            // fraction removed per tick (0..1)
    private static final double RESTITUTION = 0.45;           // bounciness (0..1)
    private static final double TANGENTIAL_DAMPING = 0.60;     // fraction of tangential velocity removed on contact (0..1)
    private static final double SETTLE_SPEED_THRESHOLD = 0.01;  // speed below which entity will settle
    private static final int MAX_BOUNCES_BEFORE_SETTLE = 6;      // safety cap to stop prolonged bouncing

    // rotational state
    private static final float SPIN_DAMPING = 0.98f;                 // multiplicative damping per tick (close to 1)

    private static final float PHYSICS_SUBSTEP_THRESHOLD = 4f;

    private ItemStack discStack;
    private boolean dealtDamage = false;

    private int bounceCount = 0;                                    // bounce tracking
    private float spin = 0.0f;                                      // angular speed (arbitrary units)

    public IdentityDiscThrownEntityOld(EntityType<IdentityDiscThrownEntityOld> entityType, World world) {
        super(entityType, world);
        this.discStack = new ItemStack(TronItems.IDENTITY_DISC);
    }

    public IdentityDiscThrownEntityOld(World world, PlayerEntity player, ItemStack itemStack) {
        super(TronEntities.IDENTITY_DISC, player, world);
        this.discStack = itemStack;
        this.spin = 20.0f + this.random.nextFloat() * 40.0f;
    }

    public boolean isInGround() {
        return inGround;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COLOR, new Vector3f(1, 1, 1));
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.asItemStack();
        return itemStack.isEmpty() ? ParticleTypes.ELECTRIC_SPARK : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
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

    @Override
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
        // If settled, keep minimal behavior and allow pickup
        if (this.inGround) {
            this.spin *= SPIN_DAMPING;
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
        vel = vel.add(GRAVITY_VEC); // dt = 1 tick

        // 2) Apply linear drag (simple multiplicative damping)
        double dragFactor = Math.max(0.0, 1.0 - LINEAR_DRAG); // clamp to prevent negative
        vel = vel.multiply(dragFactor);

        // 3) Update spin (purely visual/rotational, not affecting translation)
        this.spin *= SPIN_DAMPING;
        if (Math.abs(this.spin) < 0.01f) this.spin = 0.0f;

        // 4) Commit velocity and let base class handle movement and collisions
        double len = vel.lengthSquared();

        if (len >= PHYSICS_SUBSTEP_THRESHOLD) {
            int steps = (int) Math.ceil(len - (len % PHYSICS_SUBSTEP_THRESHOLD));

            while (steps > 0) {
                this.setVelocity(vel.multiply(1f / steps));
                super.tick();

                steps--;
            }
        } else {
            this.setVelocity(vel);
            super.tick();
        }

        // After movement/collision callbacks, evaluate settling conditions:
        Vec3d postVel = this.getVelocity();
        double speedSq = postVel.lengthSquared();

        // If on ground or very slow, settle and zero velocity
        if (this.isOnGround() || speedSq < SETTLE_SPEED_THRESHOLD * SETTLE_SPEED_THRESHOLD) {
            this.inGround = true;
            this.setVelocity(Vec3d.ZERO);
            this.spin = 0.0f;
            return;
        }

        // spawn particles client-side while flying
        if (this.getWorld().isClient()) {
            Vec3d pos = this.getPos();
            this.getWorld().addParticle(getParticleParameters(), pos.x, pos.y, pos.z,
                    postVel.x * 0.1, postVel.y * 0.1, postVel.z * 0.1);
        }

        // Note: return-to-owner is intentionally disabled; placeholder left for future use.
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (this.getWorld().isClient()) return ActionResult.SUCCESS;

        PickupPermission permission = this.pickupType;
        this.pickupType = PickupPermission.ALLOWED;

        if (!this.tryPickup(player)) {
            this.pickupType = permission;
            return ActionResult.FAIL;
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public boolean canHit() {
        return !this.isRemoved();
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) { }

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

        // Local bounce parameters
        double restitutionLocal;
        double tangentialFactor;
        double globalDamping = 0.995; // slight global damping to avoid perpetual micro-bounce

        if (side.getAxis() == Direction.Axis.Y) {
            if (side == Direction.DOWN) {
                // floor bounce: allow bounce (don't discard) and keep it slightly bouncier
                restitutionLocal = Math.min(1.0, RESTITUTION * 1.2);
                tangentialFactor = 0.85;
            } else {
                // ceiling
                restitutionLocal = RESTITUTION;
                tangentialFactor = Math.max(0.0, 1.0 - TANGENTIAL_DAMPING);
            }
        } else {
            // walls: be bouncier and preserve tangential speed
            restitutionLocal = Math.min(1.0, RESTITUTION * 1.35);
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
        if (after.lengthSquared() < SETTLE_SPEED_THRESHOLD * SETTLE_SPEED_THRESHOLD || this.bounceCount > MAX_BOUNCES_BEFORE_SETTLE) {
            this.setVelocity(Vec3d.ZERO);
            this.inGround = true;
            this.spin = 0.0f;
            this.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.01f, 1.0F);
        } else {
            this.setVelocity(after);
            this.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.01f, 0.9F + this.random.nextFloat() * 0.2F);
        }
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        if (this.isRemoved()) return false;
        if (player.isCreative()) return true;

        if (this.pickupType != PickupPermission.ALLOWED) return false;

        boolean inserted = player.getInventory().getEmptySlot() != -1
                && player.getInventory().insertStack(this.asItemStack());

        if (!inserted) this.dropStack(this.asItemStack(), 0.1F);

        this.discard();
        return inserted;
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.BLOCK_WOOL_FALL;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("disc", 10)) {
            this.discStack = ItemStack.fromNbt(nbt.getCompound("disc"));
        }

        this.dealtDamage = nbt.getBoolean("DealtDamage");

        if (nbt.contains("BounceCount")) {
            this.bounceCount = nbt.getInt("BounceCount");
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("disc", this.discStack.writeNbt(new NbtCompound()));
        nbt.putBoolean("DealtDamage", this.dealtDamage);
        nbt.putInt("BounceCount", this.bounceCount);
    }

    @Override
    protected float getDragInWater() {
        return 0.99F;
    }

    @Override
    public void age() {
        if (this.pickupType != PickupPermission.ALLOWED) {
            super.age();
        }
    }

    @Override
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