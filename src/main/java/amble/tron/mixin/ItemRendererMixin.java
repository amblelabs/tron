package amble.tron.mixin;

import amble.tron.client.models.IdentityDiscModel;
import amble.tron.core.TronItems;
import amble.tron.core.items.IdentityDiscItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static amble.tron.client.models.IdentityDiscModel.IDENTITY_DISC_EMISSION;
import static amble.tron.client.models.IdentityDiscModel.IDENTITY_DISC_TEXTURE;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Unique
    private final IdentityDiscModel discModel = new IdentityDiscModel(IdentityDiscModel.getTexturedModelData().createModel());

    @Inject(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V", at = @At("HEAD"), cancellable = true)
    public void renderItem(LivingEntity entity, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, @Nullable World world, int light, int overlay, int seed, CallbackInfo ci) {
        if (stack.isEmpty()) return;

        if (stack.isOf(TronItems.IDENTITY_DISC)) {
            this.tron$handleDiscRendering(entity, stack, renderMode, leftHanded, matrices, vertexConsumers, world, light, overlay, seed, ci);
        }
    }

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"), cancellable = true)
    private void renderItem(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        if (stack.isEmpty()) return;

        if (stack.isOf(TronItems.IDENTITY_DISC)) {
            this.tron$handleDiscRendering(null, stack, renderMode, leftHanded, matrices, vertexConsumers, null, light, overlay, 0, ci);
        }
    }

    @Unique private void tron$handleDiscRendering(LivingEntity entity, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, @Nullable World world, int light, int overlay, int seed, CallbackInfo ci) {
        if (!stack.isOf(TronItems.IDENTITY_DISC))
            return;

        if (!(stack.getItem() instanceof IdentityDiscItem discItem)) return;

        matrices.push();

        matrices.translate(-0.5f, -0.5f, -0.5f);
        matrices.scale(1.0f, -1.0f, -1.0f);

        discModel.setAngles(matrices, renderMode, leftHanded, discItem.isBladeRetracted(stack));

        discModel.render(matrices, vertexConsumers.getBuffer(discModel.getLayer(IDENTITY_DISC_TEXTURE)), light, overlay, 1, 1, 1, 1);
        RenderLayer renderLayer = renderMode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND || renderMode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND ? RenderLayer.getEntityCutoutNoCullZOffset(IDENTITY_DISC_EMISSION) : RenderLayer.getEyes(IDENTITY_DISC_EMISSION);
        discModel.render(matrices, vertexConsumers.getBuffer(renderLayer), 0xf000f0, overlay, discItem.getRGB(stack).x, discItem.getRGB(stack).y, discItem.getRGB(stack).z, 1);

        matrices.pop();
        ci.cancel();
    }
}
