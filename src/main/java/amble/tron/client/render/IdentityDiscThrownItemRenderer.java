package amble.tron.client.render;

import amble.tron.core.entities.IdentityDiscThrownEntity;
import amble.tron.core.items.IdentityDiscItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;

/**
 * @author Loqor
 * @license GNU General Public License v3.0
 */
@Environment(value=EnvType.CLIENT)
public class IdentityDiscThrownItemRenderer<T extends Entity>
        extends EntityRenderer<T> {
    private static final float MIN_DISTANCE = 10.25f;
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean lit;

    public IdentityDiscThrownItemRenderer(EntityRendererFactory.Context ctx, float scale, boolean lit) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
        this.scale = scale;
        this.lit = lit;
    }

    public IdentityDiscThrownItemRenderer(EntityRendererFactory.Context context) {
        this(context, 1.0f, false);
    }

    @Override
    protected int getBlockLight(T entity, BlockPos pos) {
        return this.lit ? 15 : super.getBlockLight(entity, pos);
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

        if (!(entity instanceof IdentityDiscThrownEntity flyingItem)) {
            return;
        }

        boolean bl = !flyingItem.isInGround();

        matrices.push();
        matrices.scale(0.8f, 0.8f, 0.8f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180 + entity.getYaw()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getPitch()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));
        if (bl) {
            float spinAngle = (float) (entity.age * entity.getVelocity().lengthSquared() * 2.0f);
            float sinAngle = (float) (Math.sin(entity.age * 0.1) * 45.0f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(sinAngle));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(spinAngle));
        }

        ItemStack stack = flyingItem.asItemStack();

        if (!(stack.getItem() instanceof IdentityDiscItem identityDiscItem)) return;
        identityDiscItem.setRGB(identityDiscItem.getRGB(stack), stack);
        identityDiscItem.setBladeRetracted(stack, false);

        this.itemRenderer.renderItem(
                stack,
                ModelTransformationMode.FIXED,
                light,
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                entity.getWorld(),
                entity.getId()
        );
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public boolean shouldRender(T entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public Identifier getTexture(Entity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
