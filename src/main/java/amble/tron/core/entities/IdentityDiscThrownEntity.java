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
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class IdentityDiscThrownEntity extends PersistentProjectileEntity {

    private static final TrackedData<Boolean> IN_GROUND = DataTracker.registerData(IdentityDiscThrownEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Vector3f> COLOR = DataTracker.registerData(IdentityDiscThrownEntity.class, TrackedDataHandlerRegistry.VECTOR3F);
    //private static final TrackedData<Boolean> RETRACTED = DataTracker.registerData(IdentityDiscThrownEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private ItemStack cleaverStack;
    private boolean dealtDamage;
    public int returnTimer;
    private final List<Entity> trackedTargets = new ArrayList<>();
    private int targetsHit = 0;
    private int targetCount = 0;
    private int homingTicks = 0;

    public IdentityDiscThrownEntity(EntityType<IdentityDiscThrownEntity> entityType, World world) {
        super(entityType, world);
        this.cleaverStack =  new ItemStack(TronItems.IDENTITY_DISC);
    }

    public IdentityDiscThrownEntity(World world, LivingEntity owner) {
        super(TronEntities.IDENTITY_DISC, owner, world);
        this.cleaverStack =  new ItemStack(TronItems.IDENTITY_DISC);
    }

    public IdentityDiscThrownEntity(World world, double x, double y, double z) {
        super(TronEntities.IDENTITY_DISC, x, y, z, world);
        this.cleaverStack =  new ItemStack(TronItems.IDENTITY_DISC);
    }

    public IdentityDiscThrownEntity(World world, PlayerEntity player, ItemStack itemStack) {
        super(TronEntities.IDENTITY_DISC, player, world);
        this.cleaverStack = itemStack;
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

    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F, 0.0F);
            }
        }

    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    public ItemStack asItemStack() {
        return this.cleaverStack;
    }

    @Override
    public void onDataTrackerUpdate(List<DataTracker.SerializedEntry<?>> dataEntries) {
        this.setColor(this.getDataTracker().get(COLOR));
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity == this.getOwner()) return;
        float f = 8.0F;
        if (entity instanceof LivingEntity livingEntity) {
            f += EnchantmentHelper.getAttackDamage(this.cleaverStack, livingEntity.getGroup());
        }

        Entity owner = this.getOwner();
        DamageSource damageSource = this.getDamageSources().thrown(this, owner == null ? this : owner);
        SoundEvent soundEvent = SoundEvents.ITEM_TRIDENT_HIT;

        double radius = 20;
        // Initialize targets if not already
        if (trackedTargets.isEmpty()) {
            for (Entity nearby : this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(radius))) {
                if (nearby == owner || !(nearby instanceof LivingEntity) || nearby.isRemoved()) continue;
                trackedTargets.add(nearby);
            }
            targetCount = trackedTargets.size();
            targetsHit = 0;
            homingTicks = 0;
        }

        // Remove the just-hit entity from the list
        trackedTargets.remove(entity);

        // Damage the current entity
        if (entity.damage(damageSource, 7.0f)) {
            if (entity.getType() != EntityType.ENDERMAN && owner instanceof LivingEntity) {
                EnchantmentHelper.onUserDamaged((LivingEntity) entity, owner);
                EnchantmentHelper.onTargetDamaged((LivingEntity) owner, entity);
            }
            if (entity instanceof LivingEntity livingEntity2) {
                if (owner instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity2, owner);
                    EnchantmentHelper.onTargetDamaged((LivingEntity)owner, livingEntity2);
                }
                this.onHit(livingEntity2);
            }
            targetsHit++;
        }

        // Set velocity to zero and wait for tick to move to next target

        this.setVelocity(0, 0, 0);
        // If there are more targets, immediately home to the next one
        if (!trackedTargets.isEmpty()) {
            Entity nextTarget = null;
            double minDist = Double.MAX_VALUE;
            for (Entity target : trackedTargets) {
                if (target.isRemoved() || !target.isAlive()) continue;
                double dist = this.getPos().distanceTo(target.getEyePos());
                if (dist < minDist) {
                    minDist = dist;
                    nextTarget = target;
                }
            }
            if (nextTarget instanceof LivingEntity living) {
                Vec3d toTarget = living.getEyePos().subtract(this.getPos()).normalize();
                this.setVelocity(toTarget.multiply(1.5));
            }
            homingTicks = 0;
            this.dealtDamage = false;
        } else {
            // If no more targets, start returning to owner
            if (owner != null) {
                Vec3d toOwner = owner.getEyePos().subtract(this.getPos()).normalize();
                this.setVelocity(toOwner.multiply(1.5));
            }
            this.returnTimer = Math.max(0, this.returnTimer - 10);
            targetCount = 0;
            targetsHit = 0;
            this.dealtDamage = true;
        }
        homingTicks = 0;

        float g = 1.0F;
        this.playSound(soundEvent, g, 1.0F);
    }

    @Override
    public void tick() {
        if (!trackedTargets.isEmpty()) {
            homingTicks++;
            // If we have more targets, home to the next one after a short delay
            if (this.getVelocity().lengthSquared() == 0 && homingTicks > 3) {
                Entity nextTarget = null;
                double minDist = Double.MAX_VALUE;
                for (Entity target : trackedTargets) {
                    if (target.isRemoved() || !target.isAlive()) continue;
                    double dist = this.getPos().distanceTo(target.getEyePos());
                    if (dist < minDist) {
                        minDist = dist;
                        nextTarget = target;
                    }
                }
                if (nextTarget != null) {
                    Vec3d toTarget = nextTarget.getEyePos().subtract(this.getPos()).normalize();
                    toTarget = new Vec3d(toTarget.x, toTarget.y, toTarget.z).normalize();
                    this.setVelocity(toTarget.multiply(1.5));
                    homingTicks = 0;
                }
            }
            if (targetsHit >= targetCount || homingTicks > 40) {
                trackedTargets.clear();
                targetCount = 0;
                targetsHit = 0;
                homingTicks = 0;
                this.dealtDamage = true;
            }
        }
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity entity = this.getOwner();
        boolean shouldReturnQuickly = this.age > 4 && !this.isInGroundTracked();

        // Sync tracked inGround value
        this.setInGround(this.inGround);

        if ((this.dealtDamage || shouldReturnQuickly) && entity != null && !this.isRemoved()) {
            if (!this.isOwnerAlive()) {
                if (!this.getWorld().isClient && this.pickupType == PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1F);
                }
                this.discard();
            } else {
                if (!this.isInGroundTracked()) {
                    Vec3d vec3d = entity.getEyePos().subtract(this.getPos());
                    this.setYaw((float)(Math.toDegrees(Math.atan2(vec3d.z, vec3d.x)) - 90.0));
                    this.setPitch((float)(-Math.toDegrees(Math.atan2(vec3d.y, Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z)))));
                    double speedMultiplier = shouldReturnQuickly ? 1.5 : 0.05;
                    if (this.returnTimer < 20) {
                        speedMultiplier = 0.05; // Slow return for first 20 ticks
                    } else {
                        speedMultiplier = 0.5; // Fast return after timer elapses
                    }
                    this.setPos(this.getX(), this.getY() + vec3d.y * 0.015 * 0.1d, this.getZ());
                    if (this.getWorld().isClient) {
                        this.lastRenderY = this.getY();
                    }
                    this.setVelocity(this.getVelocity().multiply(0.95).add(vec3d.normalize().multiply(speedMultiplier)));
                    if (this.returnTimer == 0) {
                        this.playSound(SoundEvents.ITEM_TRIDENT_HIT,8.0f,1.4f);
                    }
                    if (this.returnTimer < 40) { // Increase to require more ticks before fast return
                        ++this.returnTimer;
                    }

                    // Check if cleaver intersects owner's hitbox to give back the cleaver
                    if (entity instanceof PlayerEntity player) {
                        if (player.getBoundingBox().intersects(this.getBoundingBox())) {
                            if (this.tryPickup(player)) {
                                this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1, 1);
                                this.discard();
                                return;
                            }
                        }
                    }
                }
            }
        }
        super.tick();
        Vec3d vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.getWorld().addParticle(ParticleTypes.CRIT, d - vec3d.x * 0.25 +
                this.random.nextDouble() * 0.6 - 0.3, e - vec3d.y * 0.25 - 0.5, f - vec3d.z * 0.25 +
                this.random.nextDouble() * 0.6 - 0.3, vec3d.x, vec3d.y, vec3d.z);
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
        return this.dealtDamage ? null : super.getEntityCollision(currentPosition, nextPosition);
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        boolean bl = switch (this.pickupType) {
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
        // In creative mode, allow pickup for discard logic, but don't insert into inventory
        return this.isInGroundTracked() && (bl || player.isCreative()) || (/*this.isNoClip() && */this.isOwner(player));
    }


    protected SoundEvent getHitSound() {
        return SoundEvents.ITEM_TRIDENT_HIT;
    }

    public void onPlayerCollision(PlayerEntity player) {
        if ((this.isOwner(player) || this.getOwner() == null) && !this.getWorld().isClient) {
            if ((this.isInGroundTracked() /*|| this.isNoClip()*/) && this.shake <= 0) {
                if (this.tryPickup(player)) {
                    /*if (!player.isCreative()) {
                        player.sendPickup(this, 1);
                    }*/
                    this.discard();
                }
            }
        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("disc", 10)) {
            this.cleaverStack = ItemStack.fromNbt(nbt.getCompound("disc"));
        }

        this.dealtDamage = nbt.getBoolean("DealtDamage");
        if (nbt.contains("InGround")) {
            this.setInGround(nbt.getBoolean("InGround"));
        }
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("disc", this.cleaverStack.writeNbt(new NbtCompound()));
        nbt.putBoolean("DealtDamage", this.dealtDamage);
        nbt.putBoolean("InGround", this.isInGroundTracked());
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
}