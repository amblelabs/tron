package amble.tron.core.entities;

import amble.tron.client.sound.LightCycleMovingSoundInstance;
import amble.tron.core.entities.lighttrail.Trail;
import amble.tron.core.TronAttachmentUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Collections;
import java.util.LinkedList;

public class LightCycleEntity extends LivingEntity {
    private static final TrackedData<Vector3f> FACTION_COLOR = DataTracker.registerData(LightCycleEntity.class, TrackedDataHandlerRegistry.VECTOR3F);
    private static final TrackedData<Boolean> BEAM_ACTIVE = DataTracker.registerData(LightCycleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public static class TrailCollider {
        public final Box box;
        public int age;
        public boolean canKill;
        public final int segmentIndex;
        public TrailCollider(Box box, int segmentIndex) {
            this.box = box;
            this.age = 0;
            this.canKill = false;
            this.segmentIndex = segmentIndex;
        }
    }

    public final Trail visualTrail = new Trail(512);
    public final LinkedList<TrailCollider> serverTrailColliders = new LinkedList<>();
    public final LinkedList<Vec3d> serverTrailPoints = new LinkedList<>();
    private int trailSegmentCounter = 0;

    public float tilt = 0.0f;
    public float prevTilt = 0.0f;

    private boolean boosting = false;

    @Override
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() - 0.75; // Adjusted for proper seat positioning
    }

    public LightCycleEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.setStepHeight(1f);
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
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FACTION_COLOR, new Vector3f(1.0f, 1.0f, 1.0f));
        this.dataTracker.startTracking(BEAM_ACTIVE, false);
    }

    public boolean isBeamActive() {
        return this.dataTracker.get(BEAM_ACTIVE);
    }

    public void setBeamActive(boolean flag) {
        this.dataTracker.set(BEAM_ACTIVE, flag);
    }

    public Vector3f getColor() {
        return this.dataTracker.get(FACTION_COLOR);
    }

    public void setColor(Vector3f color) {
        this.dataTracker.set(FACTION_COLOR, color);
    }

    private LightCycleMovingSoundInstance movingInstance;

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            this.prevTilt = this.tilt;
            float deltaYaw = net.minecraft.util.math.MathHelper.wrapDegrees(this.getYaw() - this.prevYaw);
            float targetTilt = net.minecraft.util.math.MathHelper.clamp(deltaYaw * 3.5f, -45.0f, 45.0f);
            this.tilt = net.minecraft.util.math.MathHelper.lerp(0.3f, this.tilt, targetTilt);

            double yawRad = Math.toRadians(this.getYaw());

            // Adjust the offset behind the cycle
            double backX = this.getX() + Math.sin(yawRad) * 1.5;
            double backZ = this.getZ() - Math.cos(yawRad) * 1.5;
            double y = this.getY();

            // Calculate tilted up-vector logic for the beam
            double tiltRad = Math.toRadians(this.tilt);
            double upX = -Math.sin(tiltRad) * Math.cos(yawRad);
            double upY = Math.cos(tiltRad);
            double upZ = -Math.sin(tiltRad) * Math.sin(yawRad);

            // Height of the trail slice (e.g. from y+0.1 to y+1.1)
            Vector4f v1 = new Vector4f((float) (backX + upX * 0.1), (float) (y + upY * 0.1), (float) (backZ + upZ * 0.1), 1.0f);
            Vector4f v2 = new Vector4f((float) (backX + upX * 1.1), (float) (y + upY * 1.1), (float) (backZ + upZ * 1.1), 1.0f);

            double speedSq = (this.getX() - this.prevX) * (this.getX() - this.prevX) + (this.getZ() - this.prevZ) * (this.getZ() - this.prevZ);
            // Trail disappears if not moving fast enough or beam is toggled off
            float alpha = ((this.getVelocity().lengthSquared() > 0.01 || speedSq > 0.001) && this.isBeamActive()) ? 1.0f : 0.0f;
            this.visualTrail.add(v1, v2, alpha);

            SoundManager soundManager = MinecraftClient.getInstance().getSoundManager();

            if (speedSq > 0.001 || this.getVelocity().lengthSquared() > 0.01) {
                if (this.movingInstance == null || !soundManager.isPlaying(this.movingInstance)) {
                    this.movingInstance = new LightCycleMovingSoundInstance(this);
                    soundManager.play(this.movingInstance);
                }
            } else if (this.movingInstance != null && soundManager.isPlaying(this.movingInstance)) {
                soundManager.stop(this.movingInstance);
                this.movingInstance = null;
            }
            
            // Client-side physics colliders for prediction
            if ((speedSq > 0.001 || this.getVelocity().lengthSquared() > 0.01) && this.isBeamActive()) {
                Vec3d curPoint = new Vec3d(backX, this.getY(), backZ);

                if (!this.serverTrailPoints.isEmpty()) {
                    Vec3d prevPoint = this.serverTrailPoints.getFirst();
                    double minX = Math.min(prevPoint.x, curPoint.x) - 0.2;
                    double maxX = Math.max(prevPoint.x, curPoint.x) + 0.2;
                    double minY = Math.min(prevPoint.y, curPoint.y);
                    double maxY = Math.max(prevPoint.y, curPoint.y) + 1.2;
                    double minZ = Math.min(prevPoint.z, curPoint.z) - 0.2;
                    double maxZ = Math.max(prevPoint.z, curPoint.z) + 0.2;
                    Box segmentBox = new Box(minX, minY, minZ, maxX, maxY, maxZ);

                    this.serverTrailColliders.addFirst(new TrailCollider(segmentBox, this.trailSegmentCounter++));
                    if (this.serverTrailColliders.size() > 512) {
                        this.serverTrailColliders.removeLast();
                    }
                }

                this.serverTrailPoints.addFirst(curPoint);
                if (this.serverTrailPoints.size() > 513) {
                    this.serverTrailPoints.removeLast();
                }
            }

            // Client-side canKill logic for visual prediction (matches server logic)
            for (TrailCollider collider : this.serverTrailColliders) {
                collider.age++;
                if (!collider.canKill) {
                    double boxCenterX = (collider.box.minX + collider.box.maxX) / 2.0;
                    double boxCenterZ = (collider.box.minZ + collider.box.maxZ) / 2.0;
                    double distSq = (this.getX() - boxCenterX) * (this.getX() - boxCenterX) + (this.getZ() - boxCenterZ) * (this.getZ() - boxCenterZ);

                    if (collider.age > 4 || distSq > 0.8) {
                        collider.canKill = true;
                    }
                }
            }
            return;
        }

        if (!this.getWorld().isClient()) {
            Vec3d backPos = new Vec3d(this.getX() + Math.sin(Math.toRadians(this.getYaw())) * 1.5, this.getY(), this.getZ() - Math.cos(Math.toRadians(this.getYaw())) * 1.5);

            // Clear colliders when beam is not active
            if (!this.isBeamActive()) {
                this.serverTrailPoints.clear();
                this.serverTrailColliders.clear();
            }

            // Create collision boxes (matching logic from working commit fdf8da5)
            boolean shouldAddBox = false;
            if (this.isBeamActive()) {
                if (this.serverTrailPoints.isEmpty()) {
                    shouldAddBox = true;
                } else {
                    Vec3d lastPoint = this.serverTrailPoints.getFirst();
                    if (lastPoint.squaredDistanceTo(backPos) > 0.05) {
                        shouldAddBox = true;
                    }
                }
            }

            if (shouldAddBox) {
                if (!this.serverTrailPoints.isEmpty()) {
                    Vec3d prevPoint = this.serverTrailPoints.getFirst();
                    double minX = Math.min(prevPoint.x, backPos.x) - 0.2;
                    double maxX = Math.max(prevPoint.x, backPos.x) + 0.2;
                    double minY = Math.min(prevPoint.y, backPos.y);
                    double maxY = Math.max(prevPoint.y, backPos.y) + 1.2;
                    double minZ = Math.min(prevPoint.z, backPos.z) - 0.2;
                    double maxZ = Math.max(prevPoint.z, backPos.z) + 0.2;
                    Box segmentBox = new Box(minX, minY, minZ, maxX, maxY, maxZ);

                    this.serverTrailColliders.addFirst(new TrailCollider(segmentBox, this.trailSegmentCounter++));
                    if (this.serverTrailColliders.size() > 512) {
                        this.serverTrailColliders.removeLast();
                    }
                }

                this.serverTrailPoints.addFirst(backPos);
                if (this.serverTrailPoints.size() > 513) {
                    this.serverTrailPoints.removeLast();
                }
            }

            Box myBox = this.getBoundingBox();
            Box sweptBox = myBox.union(new Box(this.prevX - this.getWidth()/2, this.prevY, this.prevZ - this.getWidth()/2, this.prevX + this.getWidth()/2, this.prevY + this.getHeight(), this.prevZ + this.getWidth()/2));

            boolean collided = false;

            // Kill any entities touching our beam
            for (TrailCollider collider : this.serverTrailColliders) {
                collider.age++;
                if (!collider.canKill) {
                    double boxCenterX = (collider.box.minX + collider.box.maxX) / 2.0;
                    double boxCenterZ = (collider.box.minZ + collider.box.maxZ) / 2.0;
                    double distSq = (this.getX() - boxCenterX) * (this.getX() - boxCenterX) + (this.getZ() - boxCenterZ) * (this.getZ() - boxCenterZ);

                    // Age > 4 ticks OR distance > 0.8 blocks squared (from working commit)
                    if (collider.age > 4 || distSq > 0.8) {
                        collider.canKill = true;
                    }
                }

                if (collider.canKill) {
                    java.util.List<Entity> touchingEntities = this.getWorld().getOtherEntities(this, collider.box);
                    for (Entity e : touchingEntities) {
                        if (e instanceof LivingEntity && !this.getPassengerList().contains(e)) {
                            e.damage(this.getDamageSources().generic(), Float.MAX_VALUE);
                            if (e instanceof LightCycleEntity cycle) {
                                cycle.damage(this.getDamageSources().generic(), Float.MAX_VALUE);
                                cycle.kill();
                            }
                        }
                    }

                    // Use sweptBox to prevent tunneling
                    if (sweptBox.intersects(collider.box)) {
                        collided = true;
                    }
                }
            }

            if (collided) {
                this.damage(this.getDamageSources().generic(), Float.MAX_VALUE);
                this.kill();
            }

            // Check against other cycles' beams
            java.util.List<LightCycleEntity> cycles = this.getWorld().getEntitiesByClass(LightCycleEntity.class, myBox.expand(256.0), e -> true);
            for (LightCycleEntity otherCycle : cycles) {
                if (otherCycle == this) continue;
                for (TrailCollider collider : otherCycle.serverTrailColliders) {
                    if (collider.canKill && sweptBox.intersects(collider.box)) {
                        this.damage(this.getDamageSources().generic(), Float.MAX_VALUE);
                        this.kill();
                        break;
                    }
                }
            }
        }

        if (this.getControllingPassenger() instanceof PlayerEntity player) {
            Vector3f factionColor = TronAttachmentUtil.getFactionColor(player);
            if (factionColor != null && !factionColor.equals(this.getColor())) {
                this.setColor(factionColor);
            }

            if (this.isBeamActive()) {
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

            // Update the player's yaw to turn exactly with the bike
            float playerYaw = controllingPlayer.getYaw();
            controllingPlayer.setYaw(playerYaw + steer);
        }

        Vec2f rot = this.getControlledRotation(controllingPlayer);
        this.setRotation(this.getYaw(), rot.x);
        this.bodyYaw = this.headYaw = this.getYaw();

        if (this.isLogicalSideForUpdatingMovement()) {
            // Acceleration: forward applies thrust in vehicle forward direction
            double forward = Math.max(0.0F, forwardInput);
            double accel = forward * 0.2;

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
            if (this.isOnGround() && horiz > 0.01) {
                // Look ahead in the direction of travel using yaw, not velocity components
                double lookAhead = Math.max(0.6, horiz * 2);
                double nextX = this.getX() - Math.sin(yawRad) * lookAhead;
                double nextZ = this.getZ() + Math.cos(yawRad) * lookAhead;

                BlockPos aheadPos = BlockPos.ofFloored(nextX, this.getY(), nextZ);
                BlockState blockAtFeet = this.getWorld().getBlockState(aheadPos);
                BlockState blockAbove = this.getWorld().getBlockState(aheadPos.up());
                BlockState blockAbove2 = this.getWorld().getBlockState(aheadPos.up(2));

                // Check if there's a solid obstacle we can step onto
                boolean hasObstacle = blockAtFeet.isSolidBlock(this.getWorld(), aheadPos);
                boolean hasHeadroom = !blockAbove.isSolidBlock(this.getWorld(), aheadPos.up())
                                   && !blockAbove2.isSolidBlock(this.getWorld(), aheadPos.up(2));

                if (hasObstacle && hasHeadroom) {
                    // Teleport up smoothly rather than applying velocity
                    this.setPosition(this.getX(), this.getY() + 1.0, this.getZ());
                    vel = new Vec3d(vel.x, 0.0, vel.z);
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

        // Do the queue system for the points of which the lightcycle will store - Loqor

        // remember this segment's center for the next one
        this.lastTrailSegmentPos = new Vec3d(backX, y, backZ);
    }
}
