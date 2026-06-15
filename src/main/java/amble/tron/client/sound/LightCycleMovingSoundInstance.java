package amble.tron.client.sound;
import amble.tron.core.TronSounds;
import amble.tron.core.entities.LightCycleEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
public class LightCycleMovingSoundInstance extends MovingSoundInstance {
    private final LightCycleEntity lightCycle;
    private float distance = 0.0f;
    public LightCycleMovingSoundInstance(LightCycleEntity lightCycle) {
        super(TronSounds.LIGHTCYCLE_LOOP, SoundCategory.NEUTRAL, net.minecraft.util.math.random.Random.create());
        this.lightCycle = lightCycle;
        this.repeat = true;
        this.repeatDelay = 50; // Needs work
        this.volume = 0.0f;
        this.pitch = 1.0f;
        this.x = (float) lightCycle.getX();
        this.y = (float) lightCycle.getY();
        this.z = (float) lightCycle.getZ();
    }
    @Override
    public boolean canPlay() {
        return !this.lightCycle.isSilent();
    }
    @Override
    public boolean shouldAlwaysPlay() {
        return true;
    }
    @Override
    public void tick() {
        if (this.lightCycle.isRemoved() || this.lightCycle.getVelocity().lengthSquared() < 0.001) {
            this.setDone();
            return;
        }
        this.x = (float) this.lightCycle.getX();
        this.y = (float) this.lightCycle.getY();
        this.z = (float) this.lightCycle.getZ();
        if (this.lightCycle.hasPassengers()) {
            float speed = (float) this.lightCycle.getVelocity().horizontalLength();
            if (speed > 0.01f) {
                this.distance = MathHelper.clamp(this.distance + 0.0025f, 0.0f, 1.0f);
                this.volume = MathHelper.lerp(MathHelper.clamp(speed * 3.0f, 0.0f, 1.0f), 0.0f, 0.2f);
                this.pitch = MathHelper.lerp(MathHelper.clamp(speed * 1.5f, 0.0f, 1.0f), 0.8f, 1.6f);
            } else {
                this.distance = 0.0f;
                this.volume = 0.2f;
                this.pitch = 0.8f; // Idle pitch
            }
        } else {
            this.volume = 0.0f;
            this.pitch = 0.8f;
        }
    }
}
