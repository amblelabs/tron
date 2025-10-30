package amble.tron.core.entities.lighttrail;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class LightTrailSegmentEntity extends Entity {
    private final int maxAge;
    private int age;
    private final double width;
    private final double height;

    private static final TrackedData<Vector3f> LAST_SEGMENT_POSITION;
    private static final TrackedData<Vector3f> BIKE_POSITION;

    public LightTrailSegmentEntity(EntityType<?> type, World world, double x, double y, double z, double width, double height, int maxAge, Vector3f bikePos, Vector3f lastSegmentPos) {
        super(type, world);
        this.setPos(x, y, z);
        this.setBoundingBox(new Box(x - width/2, y, z - width/2, x + width/2, y + height, z + width/2));
        this.width = width;
        this.height = height;
        this.maxAge = maxAge;
        this.age = 0;
        this.noClip = false;
        this.setVelocity(Vec3d.ZERO);
        this.setNoGravity(true);
        this.setBikePos(bikePos);
        this.setBikePos(lastSegmentPos);
    }

    public LightTrailSegmentEntity(EntityType<? extends Entity> entityEntityType, World world) {
        super(entityEntityType, world);
        this.maxAge = 6000;
        this.width = 0.1;
        this.height = 1.5;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) return;

        // collision: damage entities intersecting the bounding box (exclude owner if you store it)
        for (Entity e : this.getWorld().getOtherEntities(this, this.getBoundingBox())) {
            // optionally skip owner by UUID
            e.damage(this.getWorld().getDamageSources().generic(), 20.0F);
        }

        if (++age >= maxAge) this.discard();
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(LAST_SEGMENT_POSITION, new Vector3f(0 ,0, 0));
        this.dataTracker.startTracking(BIKE_POSITION, new Vector3f(0 ,0, 0));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putFloat("bikeX", this.getBikePos().x);
        nbt.putFloat("bikeY", this.getBikePos().y);
        nbt.putFloat("bikeZ", this.getBikePos().z);
        nbt.putFloat("lastSegmentX", this.getLastSegmentPos().x);
        nbt.putFloat("lastSegmentY", this.getLastSegmentPos().y);
        nbt.putFloat("lastSegmentZ", this.getLastSegmentPos().z);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.setBikePos(new Vector3f(nbt.getFloat("bikeX"), nbt.getFloat("bikeY"), nbt.getFloat("bikeZ")));
        this.setLastSegmentPos(new Vector3f(nbt.getFloat("lastSegmentX"), nbt.getFloat("lastSegmentY"), nbt.getFloat("lastSegmentZ")));
    }

    private void setBikePos(Vector3f pos) {
        this.dataTracker.set(BIKE_POSITION, pos);
    }

    public Vector3f getBikePos() {
        return this.dataTracker.get(BIKE_POSITION);
    }

    private void setLastSegmentPos(Vector3f pos) {
        this.dataTracker.set(LAST_SEGMENT_POSITION, pos);
    }

    public Vector3f getLastSegmentPos() {
        return this.dataTracker.get(LAST_SEGMENT_POSITION);
    }

    static {
        BIKE_POSITION = DataTracker.registerData(LightTrailSegmentEntity.class, TrackedDataHandlerRegistry.VECTOR3F);
        LAST_SEGMENT_POSITION = DataTracker.registerData(LightTrailSegmentEntity.class, TrackedDataHandlerRegistry.VECTOR3F);
    }


}