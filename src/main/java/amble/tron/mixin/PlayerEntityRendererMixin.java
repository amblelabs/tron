package amble.tron.mixin;

import amble.tron.LightSuitItem;
import amble.tron.client.features.LightSuitFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Arm;

import static amble.tron.client.features.LightSuitFeatureRenderer.LIGHTSUIT_LIGHTS;
import static amble.tron.client.features.LightSuitFeatureRenderer.LIGHTSUIT_TEXTURE;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin
        extends
        LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    @Shadow
    protected abstract void setModelPose(AbstractClientPlayerEntity player);

    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx,
                                     PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void ait$PlayerEntityRenderer(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        PlayerEntityRenderer renderer = (PlayerEntityRenderer) (Object) this;

        this.addFeature(new LightSuitFeatureRenderer<>(renderer, ctx.getModelLoader(), getModel()));
    }

    @Inject(method = "renderArm", at = @At("HEAD"), cancellable = true)
    private void ait$renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve, CallbackInfo ci) {
        if (!(player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof LightSuitItem)) return;
        ci.cancel();

        PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel = this.getModel();
        this.setModelPose(player);
        playerEntityModel.handSwingProgress = 0.0f;
        playerEntityModel.sneaking = false;
        playerEntityModel.leaningPitch = 0.0f;
        playerEntityModel.setAngles(player, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        arm.pitch = 0.0f;

        boolean rightHanded = player.getMainArm() == Arm.RIGHT;

        if (rightHanded) {
            this.getModel().rightArm.copyTransform(arm);
            this.getModel().rightArm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(LIGHTSUIT_TEXTURE)), light, OverlayTexture.DEFAULT_UV);
            this.getModel().rightArm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEyes(LIGHTSUIT_LIGHTS)), 0xf000f0, OverlayTexture.DEFAULT_UV);
        } else {
            this.getModel().leftArm.copyTransform(arm);
            this.getModel().leftArm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(LIGHTSUIT_TEXTURE)), light, OverlayTexture.DEFAULT_UV);
            this.getModel().leftArm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEyes(LIGHTSUIT_LIGHTS)), 0xf000f0, OverlayTexture.DEFAULT_UV);
        }
    }
}
