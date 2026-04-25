package amble.tron.client.features;

import amble.tron.core.TronAttachmentTypes;
import amble.tron.core.TronAttachmentUtil;
import amble.tron.core.entities.LightCycleEntity;
import amble.tron.core.items.LightSuitItem;
import amble.tron.Tron;
import amble.tron.core.items.IdentityDiscItem;
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
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;

@Environment(value = EnvType.CLIENT)
public class LightSuitFeatureRenderer<T extends AbstractClientPlayerEntity, M extends PlayerEntityModel<T> & ModelWithArms>
        extends
            FeatureRenderer<T, M> {

    public static final Identifier LIGHTSUIT_TEXTURE = new Identifier(Tron.MOD_ID, "textures/entity/lightsuit.png");
    public static final Identifier CYCLE_SUIT = new Identifier(Tron.MOD_ID, "textures/entity/cyclesuit.png");
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

        ItemStack stack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);

        boolean bl = stack.getItem() instanceof LightSuitItem;

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

        this.model.jacket.copyTransform(getContextModel().jacket);
        this.model.leftSleeve.copyTransform(getContextModel().leftSleeve);
        this.model.rightSleeve.copyTransform(getContextModel().rightSleeve);
        this.model.leftPants.copyTransform(getContextModel().leftPants);
        this.model.rightPants.copyTransform(getContextModel().rightPants);

        if (bl) {
            this.model.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(livingEntity.getVehicle() instanceof LightCycleEntity
                    ? CYCLE_SUIT : LIGHTSUIT_TEXTURE)), i, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1f);
        }

        Vector3f defaultProgram = new Vector3f(0.5f, 0.7f, 1.0f);
        Vector3f rectified = new Vector3f(1f, 0.5f, 0.1f);
        Vector3f utility = new Vector3f(0.2f, 0.9f, 0.5f);
        Vector3f user = new Vector3f(1f, 1f, 1f);
        Vector3f theoSpecific = new Vector3f(1, 0, 0);
        Vector3f finalProgram = rectified;
        if (stack.getItem() instanceof LightSuitItem lightSuitItem) {
            /*if (finalProgram != lightSuitItem.getRGB(stack)) {
                lightSuitItem.setRGB(finalProgram, stack);
            }*/
            this.model.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEyes(LIGHTSUIT_LIGHTS)), i, OverlayTexture.DEFAULT_UV,
                    lightSuitItem.getRGB(stack).x, lightSuitItem.getRGB(stack).y, lightSuitItem.getRGB(stack).z, 1f);
        }

        matrixStack.pop();

        if (bl && !(livingEntity.getMainHandStack().getItem() instanceof IdentityDiscItem) && !livingEntity.getItemCooldownManager().isCoolingDown(amble.tron.core.TronItems.IDENTITY_DISC)) {
            matrixStack.push();

            this.getContextModel().body.rotate(matrixStack);

            matrixStack.scale(0.6f, 0.6f, 0.6f);
            matrixStack.translate(0, 0.3f, 0.25f);
            matrixStack.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));

            ItemStack renderDisc = new ItemStack(amble.tron.core.TronItems.IDENTITY_DISC);
            Vector3f factionColor = null;
            if (stack.getItem() instanceof LightSuitItem lightSuitItem) {
                factionColor = lightSuitItem.getRGB(stack);
            } else {
                factionColor = TronAttachmentUtil.getFactionColor(livingEntity);
            }
            if (factionColor != null && renderDisc.getItem() instanceof IdentityDiscItem fakeDisc) {
                fakeDisc.__setRGB(factionColor, renderDisc);
                fakeDisc.__setBladeRetracted(renderDisc, true);
                MinecraftClient.getInstance().getItemRenderer().renderItem(livingEntity, renderDisc, ModelTransformationMode.FIXED,
                        false, matrixStack, vertexConsumerProvider, null, i, OverlayTexture.DEFAULT_UV, 0);
            }
            matrixStack.pop();
        }
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
