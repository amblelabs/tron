package amble.tron.mixin;
import amble.tron.core.entities.LightCycleEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow private float pitch;
    @Shadow private float yaw;
    @Inject(method = "update", at = @At("TAIL"))
    private void tron$update(net.minecraft.world.BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
    }
}
