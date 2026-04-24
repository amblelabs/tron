package amble.tron.mixin;

import amble.tron.client.models.LightCycleModel;
import amble.tron.core.items.LightSuitItem;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerEntityModel.class, priority = 1001)
public class PlayerEntityModelMixin<T extends LivingEntity, M extends BipedEntityModel<T>> {
    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    public void tron$setAngles(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        PlayerEntityModel model = (PlayerEntityModel) (Object) this;
        boolean bl = livingEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof LightSuitItem;

        if (livingEntity.getVehicle() instanceof amble.tron.core.entities.LightCycleEntity cycle) {
            model.head.pitch = j * 0.017453292F + 0.26F;
            model.head.yaw = i * 0.017453292F;
            model.head.roll = 0.0F;

            model.body.pitch = 1.35F;
            model.body.yaw = 0.0F;
            model.body.roll = 0.0F;

            model.rightArm.pitch = -0.7F;
            model.rightArm.yaw = 0.48F;
            model.rightArm.roll = 0.0F;

            model.leftArm.pitch = -0.7F;
            model.leftArm.yaw = -0.48F;
            model.leftArm.roll = 0.0F;

            model.rightLeg.pitch = 0.9F;
            model.rightLeg.yaw = -0.21F;
            model.rightLeg.roll = 0.42F;

            model.leftLeg.pitch = 0.9F;
            model.leftLeg.yaw = 0.21F;
            model.leftLeg.roll = -0.42F;

            // Re-sync overlay parts to avoid floating secondary layers
            model.hat.copyTransform(model.head);
            model.jacket.copyTransform(model.body);
            model.rightSleeve.copyTransform(model.rightArm);
            model.leftSleeve.copyTransform(model.leftArm);
            model.rightPants.copyTransform(model.rightLeg);
            model.leftPants.copyTransform(model.leftLeg);
            LightCycleModel.transformMixinModel(model);
        }

        //model.hat.visible = !bl;
        model.jacket.visible = !bl;
        model.rightSleeve.visible = !bl;
        model.leftSleeve.visible = !bl;
        model.leftPants.visible = !bl;
        model.rightPants.visible = !bl;
    }
}
