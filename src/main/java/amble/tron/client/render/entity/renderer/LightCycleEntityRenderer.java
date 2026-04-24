package amble.tron.client.render.entity.renderer;

import amble.tron.Tron;
import amble.tron.client.models.LightCycleModel;
import amble.tron.core.entities.LightCycleEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import amble.tron.client.render.entity.renderer.TrailRenderer;
import amble.tron.core.TronAttachmentUtil;
import net.minecraft.entity.player.PlayerEntity;
import org.joml.Vector3f;

public class LightCycleEntityRenderer<T extends LightCycleEntity> extends EntityRenderer<T> {

    private LightCycleModel model;

    public LightCycleEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new LightCycleModel(LightCycleModel.getTexturedModelData().createModel());
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(yaw));

        float currentTilt = net.minecraft.util.math.MathHelper.lerp(tickDelta, entity.prevTilt, entity.tilt);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(currentTilt));

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180f));
        matrices.translate(0, -1.5, 0);
        this.model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(this.getTexture(entity))), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);

        Vector3f color = entity.getColor();
        this.model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEyes(this.getEmission())), light, OverlayTexture.DEFAULT_UV, color.x, color.y, color.z, 1);
        /*MinecraftClient.getInstance().getBlockRenderManager().renderBlock(Blocks.REDSTONE_LAMP.getDefaultState().with(RedstoneLampBlock.LIT, true), entity.getBlockPos(),
                entity.getWorld(), matrices, vertexConsumers.getBuffer(RenderLayers.getBlockLayer(Blocks.REDSTONE_LAMP.getDefaultState())), false, entity.getWorld().getRandom());
        */
        matrices.pop();

        // Render light trail
        TrailRenderer.render(entity.visualTrail, vertexConsumers, matrices.peek(), color);

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public boolean shouldRender(T entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public Identifier getTexture(T entity) {
        return Tron.of("textures/entity/lightcycle.png");
    }

    public Identifier getEmission() {
        return Tron.of("textures/entity/lightcycle_emission.png");
    }
}
