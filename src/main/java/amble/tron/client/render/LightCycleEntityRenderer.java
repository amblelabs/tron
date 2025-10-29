package amble.tron.client.render;

import amble.tron.core.entities.LightCycleEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class LightCycleEntityRenderer<T extends LightCycleEntity> extends EntityRenderer<T> {
    public LightCycleEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.translate(entity.getX(), entity.getY(), entity.getZ());
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(Blocks.REDSTONE_LAMP.getDefaultState(), new BlockPos(0, 0,0),
                entity.getWorld(), matrices, vertexConsumers.getBuffer(RenderLayers.getBlockLayer(Blocks.REDSTONE_LAMP.getDefaultState())), true, entity.getWorld().getRandom());
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(T entity) {
        return null;
    }
}
