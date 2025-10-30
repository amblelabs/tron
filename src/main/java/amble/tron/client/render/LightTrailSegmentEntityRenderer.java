package amble.tron.client.render;

import amble.tron.core.entities.LightCycleEntity;
import amble.tron.core.entities.lighttrail.LightTrailSegmentEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class LightTrailSegmentEntityRenderer<T extends LightTrailSegmentEntity> extends EntityRenderer<T> {
    public LightTrailSegmentEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        /*matrices.push();
        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(yaw));
        matrices.translate(-0.5, 0, -0.5);
        matrices.scale(1, 1, 2);
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE.getDefaultState().with(StainedGlassPaneBlock.SOUTH, true).with(StainedGlassPaneBlock.NORTH, true), entity.getBlockPos(),
                entity.getWorld(), matrices, vertexConsumers.getBuffer(RenderLayers.getBlockLayer(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE.getDefaultState())), false, entity.getWorld().getRandom());
        matrices.pop();*/
        renderConnector(matrices, vertexConsumers, entity.getLastSegmentPos(), entity.getBikePos(), light);
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private void renderConnector(MatrixStack matrices, VertexConsumerProvider vertices, Vector3f p0, Vector3f p1, int light) {
        float dx = p1.x - p0.x;
        float dy = p1.y - p0.y;
        float dz = p1.z - p0.z;
        float v = dx * dx + dy * dy + dz * dz;
        if (v <= 1e-6f) return;

        matrices.push();
        matrices.translate(0, 1, 0);
        // make positions camera-relative
        Vec3d camPos = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();
        //matrices.translate(-camPos.x, -camPos.y, -camPos.z);

        VertexConsumer vc = vertices.getBuffer(RenderLayer.getLines());
        Matrix4f model = matrices.peek().getPositionMatrix();
        int overlay = 0;
        float r = 1f, g = 1f, b = 1f, a = 1f;

        // compute a camera-facing perpendicular to produce a ribbon-like connector
        double len = Math.sqrt(v);
        float ndx = (float)(dx / len);
        float ndy = (float)(dy / len);
        float ndz = (float)(dz / len);

        // midpoint and view vector (world space)
        float midX = (p0.x + p1.x) * 0.5f;
        float midY = (p0.y + p1.y) * 0.5f;
        float midZ = (p0.z + p1.z) * 0.5f;
        float vx = (float)(midX - camPos.x);
        float vy = (float)(midY - camPos.y);
        float vz = (float)(midZ - camPos.z);

        // perpendicular = dir cross view
        float px = ndy * vz - ndz * vy;
        float py = ndz * vx - ndx * vz;
        float pz = ndx * vy - ndy * vx;
        float plen = (float)Math.sqrt(px*px + py*py + pz*pz);
        if (plen < 1e-6f) {
            // fallback perpendicular
            px = -ndz;
            py = 0f;
            pz = ndx;
            plen = (float)Math.sqrt(px*px + py*py + pz*pz);
            if (plen < 1e-6f) {
                px = 0f; py = 1f; pz = 0f;
                plen = 1f;
            }
        }

        float thickness = 0.05f;
        float scale = (thickness * 0.5f) / plen;
// normalized perpendicular for normal attribute
        float nx = px / plen;
        float ny = py / plen;
        float nz = pz / plen;
        px *= scale;
        py *= scale;
        pz *= scale;

        int segments = Math.max(1, (int)(len * 10.0));
        for (int i = 0; i <= segments; ++i) {
            float t = (float)i / (float)segments;
            float x = p0.x + dx * t;
            float y = p0.y + dy * t;
            float z = p0.z + dz * t;

            // two vertices offset by the perpendicular to form a thin ribbon
            Matrix3f normalMatrix = matrices.peek().getNormalMatrix();
            vc.vertex(model, x - px, y - py, z - pz).color(r, g, b, a).texture(0f, 0f).overlay(overlay).light(light).normal(normalMatrix, nx, ny, nz).next();
            vc.vertex(model, x + px, y + py, z + pz).color(r, g, b, a).texture(0f, 0f).overlay(overlay).light(light).normal(normalMatrix, nx, ny, nz).next();
        }

        matrices.pop();
    }

    @Override
    public Identifier getTexture(T entity) {
        return null;
    }

    @Override
    public boolean shouldRender(T entity, Frustum frustum, double x, double y, double z) {
        return isInRenderDistance(entity, new Vec3d(x, y, z));
    }

    public int getRenderDistance() {
        return 256;
    }

    public boolean isInRenderDistance(T entity, Vec3d vec3d) {
        return entity.getPos().multiply(1.0, 0.0, 1.0).isInRange(vec3d.multiply(1.0, 0.0, 1.0), this.getRenderDistance());
    }
}
