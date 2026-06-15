package amble.tron.mixin;
import amble.tron.core.entities.LightCycleEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @ModifyArgs(method = "render*", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;setupTransforms(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/util/math/MatrixStack;FFF)V"))
    private void tron$modifySetupTransformsArgs(Args args) {
        LivingEntity entity = args.get(0);
        if (entity.getVehicle() instanceof LightCycleEntity cycle) {
            args.set(3, cycle.getYaw()); // Force exact cycle yaw as bodyYaw
        }
    }
}
