package amble.tron.core.entities;

import amble.tron.core.TronEntities;
import amble.tron.core.entities.lighttrail.LightTrailSegmentEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Collections;

public class LightCycleEntity extends LivingEntity {
    public LightCycleEntity(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }


    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (!this.hasPassengers()) {
            this.putPlayerOnBack(player);
            return ActionResult.success(this.getWorld().isClient);
        }
        return super.interact(player, hand);
    }

    protected void putPlayerOnBack(PlayerEntity player) {
        if (!this.getWorld().isClient) {
            player.setYaw(this.getYaw());
            player.setPitch(this.getPitch());
            player.startRiding(this);
        }
    }

    @Override
    public Arm getMainArm() {
        return Arm.LEFT;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) return;
        if (this.getControllingPassenger() instanceof PlayerEntity player) {
            if (player.getMainHandStack().getItem() instanceof SwordItem) {
                this.spawnTrailIfNeeded();
            }
        }
    }

    protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) {
        super.tickControlled(controllingPlayer, movementInput);

        // Only allow steering when moving forward
        float forwardInput = controllingPlayer.forwardSpeed;
        boolean canTurn = forwardInput > 1.0E-4F;

        float steer = 0.0F;
        if (canTurn) {
            steer = -controllingPlayer.sidewaysSpeed * 3.0F; // turn sensitivity (reduced)
        }

        // Clamp yaw within a left/right range relative to the controller's facing
        float maxRange = 45.0F; // degrees allowed left/right from controller's yaw
        float targetYaw = this.getYaw() + steer;
        float centerYaw = controllingPlayer.getYaw();

        // Compute shortest angular difference and clamp it
        float diff = MathHelper.wrapDegrees(targetYaw - centerYaw);
        if (diff < -maxRange) diff = -maxRange;
        if (diff > maxRange) diff = maxRange;
        float clampedYaw = centerYaw + diff;

        if (canTurn) {
            this.setYaw(clampedYaw);

            // Do not force the player to match the vehicle yaw exactly.
            // Instead, lock the player's yaw to remain within \u00B1maxRange of the vehicle's yaw.
            float playerYaw = controllingPlayer.getYaw();
            float playerDiff = MathHelper.wrapDegrees(playerYaw - this.getYaw());
            if (playerDiff < -maxRange) {
                playerYaw = this.getYaw() - maxRange;
            } else if (playerDiff > maxRange) {
                playerYaw = this.getYaw() + maxRange;
            }

            // Only update the player's yaw if it was outside the allowed range
            if (Math.abs(MathHelper.wrapDegrees(playerYaw - controllingPlayer.getYaw())) > 1.0E-4F) {
                controllingPlayer.setYaw(playerYaw);
            }
        }

        Vec2f rot = this.getControlledRotation(controllingPlayer);
        this.setRotation(this.getYaw(), rot.x);
        this.prevYaw = this.bodyYaw = this.headYaw = this.getYaw();

        if (this.isLogicalSideForUpdatingMovement()) {
            // Acceleration: forward applies thrust in vehicle forward direction
            double forward = forwardInput;
            double accel = (forward > 0.0F ? forward : forward * 0.2F) * 0.2; // reverse slower
            double yawRad = Math.toRadians(this.getYaw());
            double ax = -Math.sin(yawRad) * accel;
            double az = Math.cos(yawRad) * accel;

            // Update velocity based on thrust
            Vec3d vel = this.getVelocity().add(ax, 0.0, az);

            // Apply stronger friction to reduce sliding
            vel = new Vec3d(vel.x * 0.95, vel.y, vel.z * 0.95);

            // Reduce lateral sliding by damping the rightward component in local space
            Vec3d fwdVec = new Vec3d(-Math.sin(yawRad), 0.0, Math.cos(yawRad));
            Vec3d rightVec = new Vec3d(Math.cos(yawRad), 0.0, Math.sin(yawRad));
            double forwardComp = vel.dotProduct(fwdVec);
            double rightComp = vel.dotProduct(rightVec);
            rightComp *= 0.5; // stronger damping on sideways velocity
            vel = fwdVec.multiply(forwardComp).add(rightVec.multiply(rightComp)).add(new Vec3d(0.0, vel.y, 0.0));

            double maxSpeed = 0.8;
            double horiz = Math.sqrt(vel.x * vel.x + vel.z * vel.z);
            if (horiz > maxSpeed) {
                double scale = maxSpeed / horiz;
                vel = new Vec3d(vel.x * scale, vel.y, vel.z * scale);
                horiz = maxSpeed;
            }

            // Step-up check: if grounded and moving into a low obstacle, try to step up one block
            if (this.isOnGround() && horiz > 1.0E-5) {
                double nextX = this.getX() + Math.signum(vel.x) * Math.min(Math.abs(vel.x), 0.5);
                double nextZ = this.getZ() + Math.signum(vel.z) * Math.min(Math.abs(vel.z), 0.5);
                BlockPos aheadPos = new BlockPos.Mutable(nextX, this.getY(), nextZ);
                boolean blockAtFeet = !this.getWorld().getBlockState(aheadPos).isAir();
                boolean spaceAbove = this.getWorld().getBlockState(aheadPos.up()).isAir();
                boolean headSpaceAbove2 = this.getWorld().getBlockState(aheadPos.up(2)).isAir();
                if (blockAtFeet && spaceAbove && headSpaceAbove2) {
                    // nudge upward to step up
                    vel = new Vec3d(vel.x, 0.9, vel.z);
                }
            }

            this.setVelocity(vel);
            this.move(MovementType.PLAYER, new Vec3d(vel.x, vel.y, vel.z));
        }

    }

    protected Vec2f getControlledRotation(LivingEntity controllingPassenger) {
        return new Vec2f(controllingPassenger.getPitch() * 0.5F, controllingPassenger.getYaw());
    }

    protected Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput) {
        float f = controllingPlayer.sidewaysSpeed * 0.5F;
        float g = controllingPlayer.forwardSpeed;
        if (g <= 0.0F) {
            g *= 0.25F;
        }

        return new Vec3d(f, 0.0, g);
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity firstPassenger = this.getFirstPassenger();
        if (firstPassenger instanceof MobEntity mobEntity) {
            return mobEntity;
        } else {
            firstPassenger = this.getFirstPassenger();
            if (firstPassenger instanceof PlayerEntity player) {
                return player;
            }

            return null;
        }
    }

    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Vec3d vec3d = getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), this.getYaw() + (passenger.getMainArm() == Arm.RIGHT ? 90.0F : -90.0F));
        Vec3d vec3d2 = this.locateSafeDismountingPos(vec3d, passenger);
        if (vec3d2 != null) {
            return vec3d2;
        } else {
            Vec3d vec3d3 = getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), this.getYaw() + (passenger.getMainArm() == Arm.LEFT ? 90.0F : -90.0F));
            Vec3d vec3d4 = this.locateSafeDismountingPos(vec3d3, passenger);
            return vec3d4 != null ? vec3d4 : this.getPos();
        }
    }

    @Nullable
    private Vec3d locateSafeDismountingPos(Vec3d offset, LivingEntity passenger) {
        double d = this.getX() + offset.x;
        double e = this.getBoundingBox().minY;
        double f = this.getZ() + offset.z;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (EntityPose entityPose : passenger.getPoses()) {
            mutable.set(d, e, f);
            double g = this.getBoundingBox().maxY + 0.75;

            while (true) {
                double h = this.getWorld().getDismountHeight(mutable);
                if ((double) mutable.getY() + h > g) {
                    break;
                }

                if (Dismounting.canDismountInBlock(h)) {
                    Box box = passenger.getBoundingBox(entityPose);
                    Vec3d vec3d = new Vec3d(d, (double) mutable.getY() + h, f);
                    if (Dismounting.canPlaceEntityAt(this.getWorld(), passenger, box.offset(vec3d))) {
                        passenger.setPose(entityPose);
                        return vec3d;
                    }
                }

                mutable.move(Direction.UP);
                if (!((double) mutable.getY() < g)) {
                    break;
                }
            }
        }

        return null;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return Collections.singleton(ItemStack.EMPTY);
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
    }

    @Override
    protected void jump() {
    }

    @Override
    public boolean doesRenderOnFire() {
        return false;
    }

    @Override
    public boolean hasNoDrag() {
        return true;
    }

    @Override
    public void animateDamage(float yaw) {
    }

    // trail state (server side)
    private int trailTicker = 0;
    private static final int TRAIL_TICK_INTERVAL = 4; // spawn every 4 ticks
    private static final double SEGMENT_SPACING = 0.4;
    // store last spawned segment world position to pass into the next segment
    private Vec3d lastTrailSegmentPos = null;

    protected void spawnTrailIfNeeded() {
        if (this.getWorld().isClient()) return;

        trailTicker++;
        if (trailTicker < 1) return;
        trailTicker = 0;

        // spawn position slightly behind the cycle
        double yawRad = Math.toRadians(this.getYaw());
        double backX = this.getX() + Math.sin(yawRad) * 1;
        double backZ = this.getZ() - Math.cos(yawRad) * 1;
        double y = this.getY(); // adjust to match foot height

        Vector3f prevPosVec;
        if (this.lastTrailSegmentPos != null) {
            prevPosVec = new Vector3f((float) this.lastTrailSegmentPos.x, (float) this.lastTrailSegmentPos.y, (float) this.lastTrailSegmentPos.z);
        } else {
            prevPosVec = this.getPos().toVector3f();
        }

        LightTrailSegmentEntity seg = new LightTrailSegmentEntity(TronEntities.LIGHT_TRAIL_SEGMENT, this.getWorld(), backX, y, backZ, 0.1, 1.5, 6000, this.getPos().toVector3f(), prevPosVec);
        seg.setYaw(this.getYaw());
        // optionally set owner UUID on seg to prevent self-collisions
        this.getWorld().spawnEntity(seg);

        // remember this segment's center for the next one
        this.lastTrailSegmentPos = new Vec3d(backX, y, backZ);
    }
}