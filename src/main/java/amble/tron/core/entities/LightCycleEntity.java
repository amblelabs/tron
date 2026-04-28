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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Collections;
import java.util.LinkedList;
import java.util.UUID;

public class LightCycleEntity extends LivingEntity {
    private static final TrackedData<Vector3f> FACTION_COLOR = DataTracker.registerData(LightCycleEntity.class, TrackedDataHandlerRegistry.VECTOR3F);
    private static final TrackedData<Boolean> BEAM_ACTIVE = DataTracker.registerData(LightCycleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> SPAWN_TICKS = DataTracker.registerData(LightCycleEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> DEATH_TICKS = DataTracker.registerData(LightCycleEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final int SPAWN_ANIMATION_TICKS = 40;
    public static final int DEATH_ANIMATION_TICKS = 30;

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

    // Smooth turning with momentum
    private float yawVelocity = 0.0f;
    private boolean lastBeamActiveState = false;
    private boolean finalizingDeath;
    private boolean recallToBatonPending;
    @Nullable
    private UUID recallPlayerUuid;

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

            // Immediately sync the player's faction color to the bike
            Vector3f factionColor = TronAttachmentUtil.getFactionColor(player);
            if (factionColor != null) {
                this.setColor(factionColor);
            }
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
        this.dataTracker.startTracking(SPAWN_TICKS, 0);
        this.dataTracker.startTracking(DEATH_TICKS, 0);
    }

    public boolean isBeamActive() {
        return this.dataTracker.get(BEAM_ACTIVE);
    }

    public void setBeamActive(boolean flag) {
        boolean wasActive = this.isBeamActive();
        if (wasActive == flag) {
            return;
        }

        this.dataTracker.set(BEAM_ACTIVE, flag);

        // Always break segment continuity on toggle so off->on never reconnects old and new beams.
        this.serverTrailPoints.clear();
        if (this.getWorld().isClient()) {
            this.addVisualTrailGapMarker();
        }
        this.lastBeamActiveState = flag;
    }

    private void addVisualTrailGapMarker() {
        double yawRad = Math.toRadians(this.getYaw());
        double backX = this.getX() + Math.sin(yawRad) * 1.5;
        double backZ = this.getZ() - Math.cos(yawRad) * 1.5;
        double y = this.getY();

        Vector4f v1 = new Vector4f((float) backX, (float) (y + 0.1), (float) backZ, 1.0f);
        Vector4f v2 = new Vector4f((float) backX, (float) (y + 1.1), (float) backZ, 1.0f);
        this.visualTrail.add(v1, v2, 0.0f);
    }

    public Vector3f getColor() {
        return this.dataTracker.get(FACTION_COLOR);
    }

    public void setColor(Vector3f color) {
        this.dataTracker.set(FACTION_COLOR, color);
    }

    public Trail getVisualTrail() {
        return this.visualTrail;
    }

    public int getSpawnTicks() {
        return this.dataTracker.get(SPAWN_TICKS);
    }

    public void setSpawnTicks(int ticks) {
        this.dataTracker.set(SPAWN_TICKS, MathHelper.clamp(ticks, 0, SPAWN_ANIMATION_TICKS));
    }

    public void beginSpawnAnimation() {
        this.setSpawnTicks(SPAWN_ANIMATION_TICKS);
    }

    public float getSpawnProgress(float tickDelta) {
        return 1.0f - (MathHelper.clamp(this.getSpawnTicks() - tickDelta, 0.0f, (float) SPAWN_ANIMATION_TICKS) / (float) SPAWN_ANIMATION_TICKS);
    }

    public int getDeathTicks() {
        return this.dataTracker.get(DEATH_TICKS);
    }

    public void setDeathTicks(int ticks) {
        this.dataTracker.set(DEATH_TICKS, MathHelper.clamp(ticks, 0, DEATH_ANIMATION_TICKS));
    }

    public boolean isDying() {
        return this.getDeathTicks() > 0;
    }

    public void beginDeathAnimation() {
        this.beginDeathAnimation(true);
    }

    private void beginDeathAnimation(boolean dismountNow) {
        if (this.isDying() || this.finalizingDeath) {
            return;
        }
        this.setDeathTicks(DEATH_ANIMATION_TICKS);
        this.setBeamActive(false);
        this.clearTrailState();
        if (dismountNow) {
            this.removeAllPassengers();
        }
        this.setVelocity(Vec3d.ZERO);
    }

    public void beginRecallAnimation(ServerPlayerEntity player) {
        if (this.isDying() || this.finalizingDeath) {
            return;
        }

        this.recallToBatonPending = true;
        this.recallPlayerUuid = player.getUuid();
        this.beginDeathAnimation(false);
    }

    public float getDeathProgress(float tickDelta) {
        return 1.0f - (MathHelper.clamp(this.getDeathTicks() - tickDelta, 0.0f, (float) DEATH_ANIMATION_TICKS) / (float) DEATH_ANIMATION_TICKS);
    }

    public void ejectPassengersWithMomentum() {
        if (this.getWorld().isClient()) {
            return;
        }

        Vec3d horizontal = new Vec3d(this.getVelocity().x, 0.0, this.getVelocity().z);
        if (horizontal.lengthSquared() < 1.0E-4) {
            double yawRad = Math.toRadians(this.getYaw());
            horizontal = new Vec3d(-Math.sin(yawRad), 0.0, Math.cos(yawRad)).multiply(0.7);
        } else {
            double launchSpeed = MathHelper.clamp(horizontal.length() * 1.25, 0.8, 1.6);
            horizontal = horizontal.normalize().multiply(launchSpeed);
        }

        for (Entity passenger : new java.util.ArrayList<>(this.getPassengerList())) {
            passenger.stopRiding();
            passenger.setVelocity(passenger.getVelocity().add(horizontal.x, 0.45, horizontal.z));
        }
    }

    private void clearTrailState() {
        this.visualTrail.clear();
        this.serverTrailPoints.clear();
        this.serverTrailColliders.clear();
        this.trailSegmentCounter = 0;
    }

    @Override
    public void kill() {
        if (this.finalizingDeath) {
            super.kill();
            return;
        }
        this.beginDeathAnimation();
    }

    private LightCycleMovingSoundInstance movingInstance;

    @Override
    public void tick() {
        super.tick();

        if (this.isDying()) {
            if (!this.getWorld().isClient()) {
                this.setDeathTicks(this.getDeathTicks() - 1);
            }
            if (!this.getWorld().isClient() && this.getDeathTicks() <= 0) {
                if (this.recallToBatonPending) {
                    if (this.recallPlayerUuid != null && this.getWorld().getServer() != null) {
                        ServerPlayerEntity recallPlayer = this.getWorld().getServer().getPlayerManager().getPlayer(this.recallPlayerUuid);
                        ItemStack baton = new ItemStack(amble.tron.core.TronItems.LIGHTCYCLE_BATON);
                        if (recallPlayer != null) {
                            recallPlayer.stopRiding();
                            if (!recallPlayer.getInventory().insertStack(baton)) {
                                recallPlayer.dropItem(baton, false);
                            }
                        } else {
                            this.dropStack(baton);
                        }
                    }
                    this.recallToBatonPending = false;
                    this.recallPlayerUuid = null;
                }

                this.finalizingDeath = true;
                super.kill();
                this.finalizingDeath = false;
            }
            return;
        }

        LivingEntity controllingPassenger = this.getControllingPassenger();
        boolean hasControllingPassenger = controllingPassenger != null;

        // Dismount stops new emission, but existing segments remain until recall/death.
        if (!hasControllingPassenger && !this.getWorld().isClient() && this.isBeamActive()) {
            this.setBeamActive(false);
        }

        // Clear steering momentum when unridden, but let world physics (gravity/water) still apply.
        if (!hasControllingPassenger) {
            this.yawVelocity = 0.0f;
        }

        // DataTracker updates do not call setBeamActive on clients; detect transitions here too.
        boolean beamActiveNow = this.isBeamActive();
        if (beamActiveNow != this.lastBeamActiveState) {
            this.serverTrailPoints.clear();
            if (this.getWorld().isClient()) {
                this.addVisualTrailGapMarker();
            }
            this.lastBeamActiveState = beamActiveNow;
        }

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
            boolean isEmitting = (this.getVelocity().lengthSquared() > 0.01 || speedSq > 0.001) && this.isBeamActive();
            // Only add segments while actively emitting so existing trail chunks persist instead of rapidly fading out.
            if (isEmitting) {
                this.getVisualTrail().add(v1, v2, 1.0f);
            }

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
            if (isEmitting) {
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

        } else {
            if (controllingPassenger instanceof PlayerEntity player) {
                Vector3f factionColor = TronAttachmentUtil.getFactionColor(player);
                if (factionColor != null && !factionColor.equals(this.getColor())) {
                    this.setColor(factionColor);
                }
            }

            if (this.getSpawnTicks() > 0) {
                this.setSpawnTicks(this.getSpawnTicks() - 1);
                return; // Skip trail and collision detection during spawn animation
            }

            boolean beamActive = this.isBeamActive();
            Vec3d backPos = new Vec3d(this.getX() + Math.sin(Math.toRadians(this.getYaw())) * 1.5, this.getY(), this.getZ() - Math.cos(Math.toRadians(this.getYaw())) * 1.5);

            // Stop emitting while off, but keep existing colliders so disconnected visible trail chunks can still damage.
            if (!beamActive) {
                this.serverTrailPoints.clear();
            }


            // Create collision boxes (matching logic from working commit fdf8da5)
            boolean shouldAddBox = false;
            if (beamActive) {
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
                                cycle.ejectPassengersWithMomentum();
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
                this.ejectPassengersWithMomentum();
                this.damage(this.getDamageSources().generic(), Float.MAX_VALUE);

                // Drop the baton item when the bike gets derezzed
                ItemStack baton = new ItemStack(amble.tron.core.TronItems.LIGHTCYCLE_BATON);
                this.dropItem(baton.getItem());
                this.kill();
            }

            // Check against other cycles' beams
            java.util.List<LightCycleEntity> cycles = this.getWorld().getEntitiesByClass(LightCycleEntity.class, myBox.expand(256.0), e -> true);
            for (LightCycleEntity otherCycle : cycles) {
                if (otherCycle == this) continue;
                for (TrailCollider collider : otherCycle.serverTrailColliders) {
                    if (collider.canKill && sweptBox.intersects(collider.box)) {
                        this.ejectPassengersWithMomentum();
                        this.damage(this.getDamageSources().generic(), Float.MAX_VALUE);
                        this.kill();
                        break;
                    }
                }
            }
        }
    }

    protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) {
        super.tickControlled(controllingPlayer, movementInput);

        float forwardInput = controllingPlayer.forwardSpeed;
        float sidewaysInput = controllingPlayer.sidewaysSpeed;
        boolean isBraking = forwardInput < -0.1F;

        // Calculate current speed for turn rate scaling
        Vec3d currentVel = this.getVelocity();
        double currentSpeed = Math.sqrt(currentVel.x * currentVel.x + currentVel.z * currentVel.z);

        // Smooth steering with momentum - target steer based on input
        float maxTurnRate = 4.0F; // Maximum degrees per tick the bike can turn
        float turnAccel = 0.4F;   // How quickly yaw velocity changes
        float targetSteer;

        // Only allow steering when moving
        if (currentSpeed > 0.02) {
            // Target steering angle based on sideways input
            targetSteer = -sidewaysInput * maxTurnRate;
        } else {
            targetSteer = 0.0F;
        }

        // Smoothly interpolate yaw velocity toward target (momentum)
        this.yawVelocity = MathHelper.lerp(turnAccel, this.yawVelocity, targetSteer);

        // Clamp yaw velocity to prevent snapping
        this.yawVelocity = MathHelper.clamp(this.yawVelocity, -maxTurnRate, maxTurnRate);

        // Apply yaw velocity to bike yaw
        float newYaw = this.getYaw() + this.yawVelocity;

        // Clamp yaw within range relative to controller's facing
        float maxRange = 45.0F;
        float centerYaw = controllingPlayer.getYaw();
        float diff = MathHelper.wrapDegrees(newYaw - centerYaw);
        if (diff < -maxRange) diff = -maxRange;
        if (diff > maxRange) diff = maxRange;
        float clampedYaw = centerYaw + diff;

        this.setYaw(clampedYaw);

        // Update player's yaw to follow the bike smoothly
        float playerYawDiff = MathHelper.wrapDegrees(this.getYaw() - controllingPlayer.getYaw());
        if (Math.abs(playerYawDiff) > 5.0F) {
            controllingPlayer.setYaw(controllingPlayer.getYaw() + playerYawDiff * 0.15F);
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

            // Apply friction (stronger when braking)
            double friction = isBraking ? 0.90 : 0.95;
            vel = new Vec3d(vel.x * friction, vel.y, vel.z * friction);

            // Calculate lateral and forward components in local space
            Vec3d fwdVec = new Vec3d(-Math.sin(yawRad), 0.0, Math.cos(yawRad));
            Vec3d rightVec = new Vec3d(Math.cos(yawRad), 0.0, Math.sin(yawRad));
            double forwardComp = vel.dotProduct(fwdVec);
            double rightComp = vel.dotProduct(rightVec);

            // Skidding: when turning hard while braking or at high speed, reduce grip
            boolean isTurningHard = Math.abs(this.yawVelocity) > maxTurnRate * 0.5F;
            double lateralDamping;

            if (isBraking && isTurningHard) {
                // Skid! Less lateral grip, slide sideways
                lateralDamping = 0.85;
            } else if (isTurningHard && currentSpeed > 0.5) {
                // High-speed turn, slight drift
                lateralDamping = 0.7;
            } else {
                // Normal grip
                lateralDamping = 0.5;
            }

            rightComp *= lateralDamping;
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
        return this.getControllingPassenger() != null;
    }

    @Override
    public void animateDamage(float yaw) {
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("SpawnTicks", this.getSpawnTicks());
        nbt.putInt("DeathTicks", this.getDeathTicks());
        nbt.putBoolean("RecallToBatonPending", this.recallToBatonPending);
        if (this.recallPlayerUuid != null) {
            nbt.putUuid("RecallPlayer", this.recallPlayerUuid);
        }
        nbt.putBoolean("BeamActive", this.isBeamActive());
        nbt.put("VisualTrail", this.visualTrail.toNbt());

        NbtList points = new NbtList();
        for (Vec3d point : this.serverTrailPoints) {
            NbtCompound pointNbt = new NbtCompound();
            pointNbt.putDouble("x", point.x);
            pointNbt.putDouble("y", point.y);
            pointNbt.putDouble("z", point.z);
            points.add(pointNbt);
        }
        nbt.put("ServerTrailPoints", points);

        NbtList colliders = new NbtList();
        for (TrailCollider collider : this.serverTrailColliders) {
            NbtCompound colliderNbt = new NbtCompound();
            colliderNbt.putDouble("minX", collider.box.minX);
            colliderNbt.putDouble("minY", collider.box.minY);
            colliderNbt.putDouble("minZ", collider.box.minZ);
            colliderNbt.putDouble("maxX", collider.box.maxX);
            colliderNbt.putDouble("maxY", collider.box.maxY);
            colliderNbt.putDouble("maxZ", collider.box.maxZ);
            colliderNbt.putInt("age", collider.age);
            colliderNbt.putBoolean("canKill", collider.canKill);
            colliderNbt.putInt("segmentIndex", collider.segmentIndex);
            colliders.add(colliderNbt);
        }
        nbt.put("ServerTrailColliders", colliders);
        nbt.putInt("TrailSegmentCounter", this.trailSegmentCounter);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("SpawnTicks")) {
            this.setSpawnTicks(nbt.getInt("SpawnTicks"));
        }
        if (nbt.contains("DeathTicks")) {
            this.setDeathTicks(nbt.getInt("DeathTicks"));
        }
        this.recallToBatonPending = nbt.getBoolean("RecallToBatonPending");
        if (nbt.containsUuid("RecallPlayer")) {
            this.recallPlayerUuid = nbt.getUuid("RecallPlayer");
        } else {
            this.recallPlayerUuid = null;
        }
        if (nbt.contains("BeamActive")) {
            this.setBeamActive(nbt.getBoolean("BeamActive"));
        }
        if (nbt.contains("VisualTrail", 10)) {
            this.visualTrail.fromNbt(nbt.getCompound("VisualTrail"));
        }

        this.serverTrailPoints.clear();
        if (nbt.contains("ServerTrailPoints", 9)) {
            NbtList points = nbt.getList("ServerTrailPoints", 10);
            for (int i = 0; i < points.size(); i++) {
                NbtCompound pointNbt = points.getCompound(i);
                this.serverTrailPoints.add(new Vec3d(pointNbt.getDouble("x"), pointNbt.getDouble("y"), pointNbt.getDouble("z")));
            }
        }

        this.serverTrailColliders.clear();
        if (nbt.contains("ServerTrailColliders", 9)) {
            NbtList colliders = nbt.getList("ServerTrailColliders", 10);
            for (int i = 0; i < colliders.size(); i++) {
                NbtCompound colliderNbt = colliders.getCompound(i);
                Box box = new Box(
                        colliderNbt.getDouble("minX"),
                        colliderNbt.getDouble("minY"),
                        colliderNbt.getDouble("minZ"),
                        colliderNbt.getDouble("maxX"),
                        colliderNbt.getDouble("maxY"),
                        colliderNbt.getDouble("maxZ")
                );
                TrailCollider collider = new TrailCollider(box, colliderNbt.getInt("segmentIndex"));
                collider.age = colliderNbt.getInt("age");
                collider.canKill = colliderNbt.getBoolean("canKill");
                this.serverTrailColliders.add(collider);
            }
        }

        if (nbt.contains("TrailSegmentCounter")) {
            this.trailSegmentCounter = nbt.getInt("TrailSegmentCounter");
        }

        this.lastBeamActiveState = this.isBeamActive();
    }
}
