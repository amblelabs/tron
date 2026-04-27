package amble.tron.client.render.entity.renderer;

import amble.tron.Tron;
import amble.tron.core.entities.lighttrail.Trail;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Vector3f;

public class TrailRenderer {
    private static final Identifier identifier = Tron.of("textures/entity/trail.png");

    public static void render(Trail trail, VertexConsumerProvider vertexConsumerProvider, MatrixStack.Entry matrices, Vector3f color) {
        int numPoints = Math.min(trail.entries, trail.size);
        if (trail.nullEntries >= trail.size || numPoints < 2) {
            return;
        }

        VertexConsumer lineVertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getBeaconBeam(identifier, true));
        int light = 0xf000f0;

        Vec3d pos = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();
        Matrix3f matrix = matrices.getNormalMatrix();

        int startIdx = trail.entries < trail.size ? 0 : trail.lastIndex;

        for (int j = 0; j < numPoints - 1; j++) {
            int preIndex = (startIdx + j) % trail.size;
            int curIndex = (startIdx + j + 1) % trail.size;

            int pre = preIndex * 7;
            int index = curIndex * 7;

            // A zero-alpha point is an intentional gap marker; never connect across it.
            if (trail.buffer[pre + 6] <= 0.0f || trail.buffer[index + 6] <= 0.0f) {
                continue;
            }

            float a1 = trail.buffer[pre + 6];
            float a2 = trail.buffer[index + 6];
            if (a1 <= 0.0f && a2 <= 0.0f) {
                continue;
            }

            float dx = trail.buffer[index] - trail.buffer[pre];
            float dz = trail.buffer[index + 2] - trail.buffer[pre + 2];
            float len = (float) Math.sqrt(dx * dx + dz * dz);
            float nx = 0, nz = 0;
            if (len > 0.0001f) {
                nx = -(dz / len) * 0.05f; // half of 0.1 thickness
                nz = (dx / len) * 0.05f;
            }

            // Left side
            vertex(trail, lineVertexConsumer, matrix, 0, 0, pre, pos, a1, light, color, nx, nz);
            vertex(trail, lineVertexConsumer, matrix, 0, 1, pre + 3, pos, a1, light, color, nx, nz);
            vertex(trail, lineVertexConsumer, matrix, 1, 1, index + 3, pos, a2, light, color, nx, nz);
            vertex(trail, lineVertexConsumer, matrix, 1, 0, index, pos, a2, light, color, nx, nz);
            
            // Left side (inner face)
            vertex(trail, lineVertexConsumer, matrix, 1, 0, index, pos, a2, light, color, nx, nz);
            vertex(trail, lineVertexConsumer, matrix, 1, 1, index + 3, pos, a2, light, color, nx, nz);
            vertex(trail, lineVertexConsumer, matrix, 0, 1, pre + 3, pos, a1, light, color, nx, nz);
            vertex(trail, lineVertexConsumer, matrix, 0, 0, pre, pos, a1, light, color, nx, nz);

            // Right side
            vertex(trail, lineVertexConsumer, matrix, 1, 0, index, pos, a2, light, color, -nx, -nz);
            vertex(trail, lineVertexConsumer, matrix, 1, 1, index + 3, pos, a2, light, color, -nx, -nz);
            vertex(trail, lineVertexConsumer, matrix, 0, 1, pre + 3, pos, a1, light, color, -nx, -nz);
            vertex(trail, lineVertexConsumer, matrix, 0, 0, pre, pos, a1, light, color, -nx, -nz);

            // Top side
            vertex(trail, lineVertexConsumer, matrix, 0, 0, pre + 3, pos, a1, light, color, nx, nz);
            vertex(trail, lineVertexConsumer, matrix, 0, 1, pre + 3, pos, a1, light, color, -nx, -nz);
            vertex(trail, lineVertexConsumer, matrix, 1, 1, index + 3, pos, a2, light, color, -nx, -nz);
            vertex(trail, lineVertexConsumer, matrix, 1, 0, index + 3, pos, a2, light, color, nx, nz);

            // Top side (Inner face)
            vertex(trail, lineVertexConsumer, matrix, 0, 0, pre + 3, pos, a1, light, color, -nx, -nz);
            vertex(trail, lineVertexConsumer, matrix, 0, 1, pre + 3, pos, a1, light, color, nx, nz);
            vertex(trail, lineVertexConsumer, matrix, 1, 1, index + 3, pos, a2, light, color, nx, nz);
            vertex(trail, lineVertexConsumer, matrix, 1, 0, index + 3, pos, a2, light, color, -nx, -nz);

            // Bottom side
            vertex(trail, lineVertexConsumer, matrix, 0, 0, pre, pos, a1, light, color, -nx, -nz);
            vertex(trail, lineVertexConsumer, matrix, 0, 1, pre, pos, a1, light, color, nx, nz);
            vertex(trail, lineVertexConsumer, matrix, 1, 1, index, pos, a2, light, color, nx, nz);
            vertex(trail, lineVertexConsumer, matrix, 1, 0, index, pos, a2, light, color, -nx, -nz);

            // Bottom side (Inner face)
            vertex(trail, lineVertexConsumer, matrix, 0, 0, pre, pos, a1, light, color, nx, nz);
            vertex(trail, lineVertexConsumer, matrix, 0, 1, pre, pos, a1, light, color, -nx, -nz);
            vertex(trail, lineVertexConsumer, matrix, 1, 1, index, pos, a2, light, color, -nx, -nz);
            vertex(trail, lineVertexConsumer, matrix, 1, 0, index, pos, a2, light, color, nx, nz);
        }
    }

    private static void vertex(Trail trail, VertexConsumer lineVertexConsumer, Matrix3f matrix, float u, float v, int index, Vec3d pos, float a, int light, Vector3f color, float ox, float oz) {
        Vector3f p = new Vector3f((float) (trail.buffer[index] - pos.x) + ox, (float) (trail.buffer[index + 1] - pos.y), (float) (trail.buffer[index + 2] - pos.z) + oz);
        matrix.transform(p);
        
        // Multiply color by alpha for additive fading effect
        float r = color.x;
        float g = color.y;
        float b = color.z;
        
        lineVertexConsumer.vertex(p.x, p.y, p.z, r, g, b, a, u, v, OverlayTexture.DEFAULT_UV, light, 1, 0, 0);
    }
}