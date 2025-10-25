package amble.tron.mixin;

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
        //model.hat.visible = !bl;
        model.jacket.visible = !bl;
        model.rightSleeve.visible = !bl;
        model.leftSleeve.visible = !bl;
        model.leftPants.visible = !bl;
        model.rightPants.visible = !bl;
    }
}
