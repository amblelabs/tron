package amble.tron.client.models;

import amble.tron.Tron;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class IdentityDiscModel extends Model {
	private final ModelPart identity_disc;
    public static final Identifier IDENTITY_DISC_TEXTURE = Tron.of("textures/item/identity_disc.png");
    public static final Identifier IDENTITY_DISC_EMISSION = Tron.of("textures/item/identity_disc_emission.png");
	public IdentityDiscModel(ModelPart root) {
        super(RenderLayer::getEntityTranslucent);
        this.identity_disc = root.getChild("identity_disc");
	}
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData identity_disc = modelPartData.addChild("identity_disc", ModelPartBuilder.create().uv(0, 13).cuboid(-3.5F, -3.5F, -0.5F, 7.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 9).cuboid(-3.5F, 1.5F, -0.5F, 7.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 17).cuboid(1.5F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(16, 8).cuboid(-3.5F, -3.5F, -0.5F, 7.0F, 7.0F, 1.0F, new Dilation(0.15F))
                .uv(17, 0).cuboid(-3.5F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 22).cuboid(-3.0F, -3.0F, -0.7F, 6.0F, 6.0F, 0.0F, new Dilation(0.1F))
                .uv(0, 0).cuboid(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new Dilation(0.01F))
                .uv(0, 22).cuboid(-3.0F, -3.0F, 0.7F, 6.0F, 6.0F, 0.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, 20.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        identity_disc.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

    public void setAngles(MatrixStack matrices, ModelTransformationMode renderMode, boolean left) {

         matrices.translate(-0.75, -0.5, -0.5);

        if (renderMode == ModelTransformationMode.GUI)
            matrices.translate(0, 0.2, 0);
        switch (renderMode) {
            case THIRD_PERSON_RIGHT_HAND, THIRD_PERSON_LEFT_HAND -> {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(60));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
                matrices.translate(2 / 16f, -1.1f, 1.25);
            }
            case FIRST_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND -> {
                matrices.translate(1.25, -1, -1);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(50));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0));
            }
            case GROUND -> {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0));
                matrices.translate(1.25, -1.25, 0);
            }
            case GUI -> {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-37.09F));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-19.06F));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-127.64F));
                matrices.translate(-4.5 / 16.0F, 2.25 / 16.0F, 0);
            }
            case HEAD -> {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0));
                matrices.translate(1.25, -1, 0.75f);
            }
            case FIXED -> {
                matrices.translate(20/ 16.0F, -30 / 16.0F, 0);
                matrices.scale(1.5f, 1.5f, 1.5f);
            }
            default -> {}
        }
    }
}