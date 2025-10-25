package amble.tron.client.features;

import amble.tron.LightSuitItem;
import amble.tron.Tron;
import amble.tron.client.models.IdentityDiscModel;
import amble.tron.core.items.IdentityDiscItem;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;

@Environment(value = EnvType.CLIENT)
public class LightSuitFeatureRenderer<T extends AbstractClientPlayerEntity, M extends PlayerEntityModel<T> & ModelWithArms>
        extends
            FeatureRenderer<T, M> {

    public static final Identifier LIGHTSUIT_TEXTURE = new Identifier(Tron.MOD_ID, "textures/entity/lightsuit.png");
    public static final Identifier LIGHTSUIT_LIGHTS = new Identifier(Tron.MOD_ID, "textures/entity/lightsuit_emission.png");

    private final M model;

    public LightSuitFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader, M model) {
        super(context);
        this.model = model;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity,
                       float f, float g, float h, float j, float k, float l) {

        if (!(livingEntity instanceof AbstractClientPlayerEntity))
            return;

        matrixStack.push();

        // god bless america
        /*for (BodyParts part : BodyParts.values()) {
            ItemStack stack = getModelForSlot(livingEntity, part);
            if (stack.getItem() instanceof LightSuitItem) {
                enablePart(this.model, part);
            } else {
                disablePart(this.model, part);
            }
        }*/

        boolean bl = livingEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof LightSuitItem;

        this.model.head.visible = bl;
        this.model.hat.visible = bl;
        this.model.body.visible = bl;
        this.model.jacket.visible = bl;
        this.model.leftArm.visible = bl;
        this.model.leftSleeve.visible = bl;
        this.model.rightArm.visible = bl;
        this.model.rightSleeve.visible = bl;
        this.model.leftLeg.visible = bl;
        this.model.leftPants.visible = bl;
        this.model.rightLeg.visible = bl;
        this.model.rightPants.visible = bl;

        this.model.head.copyTransform(getContextModel().head);
        this.model.body.copyTransform(getContextModel().body);
        this.model.leftArm.copyTransform(getContextModel().leftArm);
        this.model.rightArm.copyTransform(getContextModel().rightArm);
        this.model.leftLeg.copyTransform(getContextModel().leftLeg);
        this.model.rightLeg.copyTransform(getContextModel().rightLeg);

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(LIGHTSUIT_TEXTURE));
        VertexConsumer light = vertexConsumerProvider.getBuffer(RenderLayer.getEyes(LIGHTSUIT_LIGHTS));
        VertexConsumerProvider provider = layer1 -> vertexConsumer != light
                ? VertexConsumers.union(vertexConsumer, light)
                : vertexConsumer;
        this.model.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(LIGHTSUIT_TEXTURE)), i, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1f);

        float[] defaultProgram = new float[]{0.5f, 0.9f, 1.0f};
        float[] rectified = new float[]{1f, 0.5f, 0.1f};
        float[] user = new float[]{1f, 1f, 1f};
        float[] finalProgram = user;
        this.model.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEyes(LIGHTSUIT_LIGHTS)), 0xf000f0, OverlayTexture.DEFAULT_UV, finalProgram[0], finalProgram[1], finalProgram[2], 1f);

        matrixStack.pop();

        matrixStack.push();
        matrixStack.scale(0.6f, 0.6f, 0.6f);
        matrixStack.translate(0, 0.3f, 0.25f);
        matrixStack.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
        MinecraftClient.getInstance().getItemRenderer().renderItem(livingEntity, livingEntity.getInventory().getStack(0), ModelTransformationMode.FIXED, false, matrixStack, vertexConsumerProvider,null, i, OverlayTexture.DEFAULT_UV, 0);
        matrixStack.pop();
    }

    public void enablePart(M model, BodyParts part) {
        switch (part) {
            case HEAD:
                model.head.visible = true;
                break;
            case CHEST:
                model.body.visible = true;
                model.leftArm.visible = true;
                model.rightArm.visible = true;
                break;
            case LEGS:
                model.leftPants.visible = true;
                model.rightPants.visible = true;

                break;
            /*case FEET:
                model.LeftFoot.visible = true;
                model.RightFoot.visible = true;
                break;*/
        }
    }

    public void disablePart(M model, BodyParts part) {
        switch (part) {
            case HEAD:
                model.head.visible = false;
                break;
            case CHEST:
                model.body.visible = false;
                model.leftPants.visible = false;
                model.rightPants.visible = false;
                break;
            case LEGS:
                model.leftPants.visible = false;
                model.rightPants.visible = false;
                break;
            /*case FEET:
                model.LeftFoot.visible = false;
                model.RightFoot.visible = false;
                break;*/
        }
    }

    public static ItemStack getModelForSlot(LivingEntity entity, BodyParts parts) {
        return switch(parts) {
            case CHEST -> entity.getEquippedStack(EquipmentSlot.CHEST);
            case LEGS -> entity.getEquippedStack(EquipmentSlot.LEGS);
            //case FEET -> entity.getEquippedStack(EquipmentSlot.FEET);
            default -> entity.getEquippedStack(EquipmentSlot.HEAD);
        };
    }


    public enum BodyParts {
        HEAD,
        CHEST,
        LEGS,
        //FEET
    }
}
