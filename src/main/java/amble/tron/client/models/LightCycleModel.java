package amble.tron.client.models;

import amble.tron.core.entities.LightCycleEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class LightCycleModel extends EntityModel<LightCycleEntity> {
	private final ModelPart Player;
	private final ModelPart LeftLeg;
	private final ModelPart Waist;
	private final ModelPart Head;
	private final ModelPart Body;
	private final ModelPart RightArm;
	private final ModelPart LeftArm;
	private final ModelPart Right_Leg2;
	private final ModelPart bone7;
	private final ModelPart bone4;
	private final ModelPart bone;
	private final ModelPart bone3;
	private final ModelPart bone26;
	private final ModelPart bone9;
	private final ModelPart bone10;
	private final ModelPart bone14;
	private final ModelPart bone6;
	private final ModelPart bone11;
	private final ModelPart bone8;
	private final ModelPart bone5;
	private final ModelPart bone12;
	private final ModelPart bone2;
	private final ModelPart bone13;
	private final ModelPart bone15;
	private final ModelPart bone16;
	private final ModelPart bone17;
	private final ModelPart Handle;
	private final ModelPart bone21;
	private final ModelPart bone19;
	private final ModelPart bone18;
	private final ModelPart bone20;
	private final ModelPart Handle2;
	private final ModelPart bone22;
	private final ModelPart bone23;
	private final ModelPart bone24;
	private final ModelPart bone25;
	private final ModelPart bone27;
	private final ModelPart bb_main;
	public LightCycleModel(ModelPart root) {
		this.Player = root.getChild("Player");
		this.LeftLeg = this.Player.getChild("LeftLeg");
		this.Waist = this.Player.getChild("Waist");
		this.Head = this.Waist.getChild("Head");
		this.Body = this.Waist.getChild("Body");
		this.RightArm = this.Waist.getChild("RightArm");
		this.LeftArm = this.Waist.getChild("LeftArm");
		this.Right_Leg2 = this.Player.getChild("Right_Leg2");
		this.bone7 = root.getChild("bone7");
		this.bone4 = root.getChild("bone4");
		this.bone = this.bone4.getChild("bone");
		this.bone3 = root.getChild("bone3");
		this.bone26 = root.getChild("bone26");
		this.bone9 = root.getChild("bone9");
		this.bone10 = root.getChild("bone10");
		this.bone14 = root.getChild("bone14");
		this.bone6 = root.getChild("bone6");
		this.bone11 = root.getChild("bone11");
		this.bone8 = root.getChild("bone8");
		this.bone5 = root.getChild("bone5");
		this.bone12 = root.getChild("bone12");
		this.bone2 = root.getChild("bone2");
		this.bone13 = root.getChild("bone13");
		this.bone15 = root.getChild("bone15");
		this.bone16 = root.getChild("bone16");
		this.bone17 = root.getChild("bone17");
		this.Handle = root.getChild("Handle");
		this.bone21 = this.Handle.getChild("bone21");
		this.bone19 = this.bone21.getChild("bone19");
		this.bone18 = this.bone21.getChild("bone18");
		this.bone20 = this.Handle.getChild("bone20");
		this.Handle2 = root.getChild("Handle2");
		this.bone22 = this.Handle2.getChild("bone22");
		this.bone23 = this.bone22.getChild("bone23");
		this.bone24 = this.bone22.getChild("bone24");
		this.bone25 = this.Handle2.getChild("bone25");
		this.bone27 = root.getChild("bone27");
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Player = modelPartData.addChild("Player", ModelPartBuilder.create(), ModelTransform.pivot(-1.9F, 7.4923F, -1.1622F));

		ModelPartData LeftLeg = Player.addChild("LeftLeg", ModelPartBuilder.create(), ModelTransform.of(5.8F, 1.8263F, 2.3801F, 0.8995F, 0.2068F, -0.1617F));

		ModelPartData LeftLeg_r1 = LeftLeg.addChild("LeftLeg_r1", ModelPartBuilder.create().uv(169, 58).cuboid(-1.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 5.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		ModelPartData Waist = Player.addChild("Waist", ModelPartBuilder.create(), ModelTransform.of(1.9F, 2.5077F, 1.1622F, 0.9599F, 0.0F, 0.0F));

		ModelPartData Head = Waist.addChild("Head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

		ModelPartData Head_r1 = Head.addChild("Head_r1", ModelPartBuilder.create().uv(153, 10).cuboid(-4.0F, -3.75F, -5.5F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

		ModelPartData Body = Waist.addChild("Body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

		ModelPartData Body_r1 = Body.addChild("Body_r1", ModelPartBuilder.create().uv(169, 26).cuboid(-4.0F, -6.0F, -1.25F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 6.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ModelPartData RightArm = Waist.addChild("RightArm", ModelPartBuilder.create(), ModelTransform.of(-3.0711F, -10.0F, -0.6493F, 0.0F, 0.2618F, 0.0F));

		ModelPartData RightArm_r1 = RightArm.addChild("RightArm_r1", ModelPartBuilder.create().uv(193, 26).cuboid(-2.0F, 3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 0.75F, -1.6581F, 0.2182F, 0.0F));

		ModelPartData LeftArm = Waist.addChild("LeftArm", ModelPartBuilder.create(), ModelTransform.of(3.0711F, -10.0F, -0.6493F, 0.0F, -0.2618F, 0.0F));

		ModelPartData LeftArm_r1 = LeftArm.addChild("LeftArm_r1", ModelPartBuilder.create().uv(185, 58).cuboid(-2.0F, 3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 0.75F, -1.6581F, -0.2182F, 0.0F));

		ModelPartData Right_Leg2 = Player.addChild("Right_Leg2", ModelPartBuilder.create(), ModelTransform.of(-2.0F, 1.8263F, 2.3801F, 0.8995F, -0.2068F, 0.1617F));

		ModelPartData RightLeg_r1 = Right_Leg2.addChild("RightLeg_r1", ModelPartBuilder.create().uv(169, 58).mirrored().cuboid(-3.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		ModelPartData bone7 = modelPartData.addChild("bone7", ModelPartBuilder.create(), ModelTransform.of(6.9359F, 10.0157F, 3.0058F, 0.0F, 0.0F, -0.0873F));

		ModelPartData cube_r1 = bone7.addChild("cube_r1", ModelPartBuilder.create().uv(100, 54).cuboid(0.0F, -0.55F, -2.375F, 0.0F, 1.0F, 4.0F, new Dilation(0.01F)), ModelTransform.of(-0.075F, 0.0F, 0.0F, -2.6808F, 0.1393F, -0.2727F));

		ModelPartData cube_r2 = bone7.addChild("cube_r2", ModelPartBuilder.create().uv(22, 123).cuboid(0.7F, -2.75F, -2.0F, 0.0F, 5.0F, 4.0F, new Dilation(0.01F)), ModelTransform.of(-1.435F, -2.2439F, 3.9942F, 0.0F, 0.0F, -0.4363F));

		ModelPartData bone4 = modelPartData.addChild("bone4", ModelPartBuilder.create().uv(72, 24).cuboid(-14.0F, 10.4246F, -0.6465F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F))
		.uv(76, 102).cuboid(-14.0F, 8.1746F, 6.3035F, 14.0F, 5.0F, 5.0F, new Dilation(0.0F))
		.uv(76, 59).cuboid(-14.0F, -3.4749F, -0.6465F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(7.0F, 10.9896F, -26.6035F));

		ModelPartData cube_r3 = bone4.addChild("cube_r3", ModelPartBuilder.create().uv(88, 54).cuboid(-5.0F, 3.75F, -2.0F, 1.0F, 0.0F, 5.0F, new Dilation(0.01F))
		.uv(100, 69).cuboid(-5.25F, 3.75F, -2.5F, 2.0F, 0.0F, 6.0F, new Dilation(0.0F))
		.uv(74, 75).cuboid(-14.0F, 0.5F, -3.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 4.4749F, -2.0962F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r4 = bone4.addChild("cube_r4", ModelPartBuilder.create().uv(32, 57).cuboid(-9.4F, 3.3F, -1.75F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(32, 57).cuboid(-9.4F, 3.3F, -0.5F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(32, 57).cuboid(-9.4F, 3.3F, 0.75F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(32, 57).cuboid(-9.4F, 3.3F, 2.0F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(32, 75).cuboid(-14.0F, 0.5F, -2.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.4142F, 0.4142F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r5 = bone4.addChild("cube_r5", ModelPartBuilder.create().uv(102, 131).cuboid(-1.5F, -2.5F, -2.5F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-1.4944F, -1.3652F, 9.1936F, -0.5468F, -0.5956F, -0.7446F));

		ModelPartData cube_r6 = bone4.addChild("cube_r6", ModelPartBuilder.create().uv(0, 95).cuboid(-14.0F, -1.5F, -3.0F, 14.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.2929F, 7.4142F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r7 = bone4.addChild("cube_r7", ModelPartBuilder.create().uv(130, 37).cuboid(-0.5F, -3.0F, -4.0F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-1.0054F, 5.4749F, 11.7923F, -1.5708F, -0.6545F, 0.0F));

		ModelPartData cube_r8 = bone4.addChild("cube_r8", ModelPartBuilder.create().uv(32, 111).cuboid(-0.5F, -2.0F, -1.5F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-2.2567F, 0.5892F, 12.0926F, -1.1338F, -0.504F, -0.7687F));

		ModelPartData cube_r9 = bone4.addChild("cube_r9", ModelPartBuilder.create().uv(72, 0).cuboid(-14.0F, -5.5F, -2.0F, 14.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.4749F, 5.8033F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r10 = bone4.addChild("cube_r10", ModelPartBuilder.create().uv(32, 75).cuboid(-14.0F, 0.5F, -3.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 8.1213F, -1.0F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r11 = bone4.addChild("cube_r11", ModelPartBuilder.create().uv(0, 104).cuboid(-14.0F, 0.5F, -3.0F, 14.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 8.8284F, 7.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData bone = bone4.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(-7.5F, 4.8499F, -5.0962F));

		ModelPartData cube_r12 = bone.addChild("cube_r12", ModelPartBuilder.create().uv(32, 58).cuboid(-8.0F, 7.0F, 0.75F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(32, 58).cuboid(-8.0F, 7.0F, -0.5F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(32, 58).cuboid(-8.0F, 7.0F, -1.75F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(32, 58).cuboid(-8.0F, 7.0F, -3.0F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 0.625F, 7.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData bone3 = modelPartData.addChild("bone3", ModelPartBuilder.create().uv(72, 24).mirrored().cuboid(-7.8784F, 7.6168F, -3.5932F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)).mirrored(false)
		.uv(76, 59).mirrored().cuboid(-7.8784F, -6.2827F, -3.5932F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)).mirrored(false)
		.uv(116, 69).cuboid(-1.8784F, -8.2827F, -3.5932F, 2.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(-0.8716F, 13.7974F, 16.3432F, -3.1416F, 0.0F, 3.1416F));

		ModelPartData cube_r13 = bone3.addChild("cube_r13", ModelPartBuilder.create().uv(32, 75).mirrored().cuboid(-14.0F, 0.5F, -2.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(6.1216F, -2.3936F, -2.5325F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r14 = bone3.addChild("cube_r14", ModelPartBuilder.create().uv(102, 131).mirrored().cuboid(-1.5F, -2.5F, -2.5F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.6271F, -4.173F, 6.2469F, -0.5468F, -0.5956F, -0.7446F));

		ModelPartData cube_r15 = bone3.addChild("cube_r15", ModelPartBuilder.create().uv(88, 44).cuboid(-14.0F, -1.5F, -3.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(6.1216F, -3.1007F, 4.4675F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r16 = bone3.addChild("cube_r16", ModelPartBuilder.create().uv(130, 37).mirrored().cuboid(-0.5F, -3.0F, -4.0F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(5.1161F, 2.6671F, 8.8455F, -1.5708F, -0.6545F, 0.0F));

		ModelPartData cube_r17 = bone3.addChild("cube_r17", ModelPartBuilder.create().uv(32, 111).mirrored().cuboid(-0.5F, -2.0F, -1.5F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(3.8649F, -2.2186F, 9.1459F, -1.1338F, -0.504F, -0.7687F));

		ModelPartData cube_r18 = bone3.addChild("cube_r18", ModelPartBuilder.create().uv(88, 34).cuboid(-14.0F, -5.5F, -2.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(6.1216F, 0.6671F, 2.8566F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r19 = bone3.addChild("cube_r19", ModelPartBuilder.create().uv(21, 139).cuboid(-1.5F, 0.0F, -3.5F, 3.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-0.8784F, -3.8078F, -5.8181F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r20 = bone3.addChild("cube_r20", ModelPartBuilder.create().uv(21, 139).cuboid(-8.0F, 3.5F, -3.0F, 3.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(5.6216F, 1.6671F, -4.7929F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r21 = bone3.addChild("cube_r21", ModelPartBuilder.create().uv(74, 85).cuboid(-14.0F, 0.5F, -3.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(6.1216F, 1.6671F, -5.0429F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r22 = bone3.addChild("cube_r22", ModelPartBuilder.create().uv(32, 85).cuboid(-14.0F, 0.5F, -3.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(6.1216F, 5.3135F, -3.9467F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r23 = bone3.addChild("cube_r23", ModelPartBuilder.create().uv(72, 24).mirrored().cuboid(-7.0F, -1.5F, -3.5F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.8784F, 7.0813F, 4.821F, 0.7854F, 0.0F, 0.0F));

		ModelPartData bone26 = modelPartData.addChild("bone26", ModelPartBuilder.create().uv(10, 149).cuboid(3.75F, 1.55F, -4.0F, 0.0F, 2.0F, 6.0F, new Dilation(0.01F))
		.uv(10, 149).cuboid(-1.25F, 1.55F, -4.0F, 0.0F, 2.0F, 6.0F, new Dilation(0.01F))
		.uv(6, 160).cuboid(-1.25F, 0.0F, 2.325F, 5.0F, 5.0F, 2.0F, new Dilation(0.1F))
		.uv(2, 138).cuboid(-1.25F, 0.0F, -4.25F, 5.0F, 5.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(1.75F, 13.75F, -4.75F));

		ModelPartData cube_r24 = bone26.addChild("cube_r24", ModelPartBuilder.create().uv(10, 149).cuboid(0.0F, -1.0F, -4.0F, 0.0F, 2.0F, 6.0F, new Dilation(0.01F)), ModelTransform.of(0.0F, 5.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		ModelPartData cube_r25 = bone26.addChild("cube_r25", ModelPartBuilder.create().uv(10, 149).cuboid(0.0F, -1.0F, -4.0F, 0.0F, 2.0F, 6.0F, new Dilation(0.01F)), ModelTransform.of(2.4F, 5.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		ModelPartData cube_r26 = bone26.addChild("cube_r26", ModelPartBuilder.create().uv(10, 149).cuboid(0.0F, -1.0F, -4.0F, 0.0F, 2.0F, 6.0F, new Dilation(0.01F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		ModelPartData cube_r27 = bone26.addChild("cube_r27", ModelPartBuilder.create().uv(10, 149).cuboid(0.0F, -1.0F, -4.0F, 0.0F, 2.0F, 6.0F, new Dilation(0.01F)), ModelTransform.of(2.4F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		ModelPartData bone9 = modelPartData.addChild("bone9", ModelPartBuilder.create(), ModelTransform.of(-6.9359F, 10.0157F, 3.0058F, 0.0F, 0.0F, 0.0873F));

		ModelPartData cube_r28 = bone9.addChild("cube_r28", ModelPartBuilder.create().uv(100, 54).mirrored().cuboid(0.0F, -0.55F, -2.375F, 0.0F, 1.0F, 4.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(0.075F, 0.0F, 0.0F, -2.6808F, -0.1393F, 0.2727F));

		ModelPartData cube_r29 = bone9.addChild("cube_r29", ModelPartBuilder.create().uv(22, 123).mirrored().cuboid(-0.7F, -2.75F, -2.0F, 0.0F, 5.0F, 4.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(1.435F, -2.2439F, 3.9942F, 0.0F, 0.0F, 0.4363F));

		ModelPartData bone10 = modelPartData.addChild("bone10", ModelPartBuilder.create().uv(42, 95).cuboid(-8.0F, 6.2426F, -3.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 16.0074F, -23.25F, -0.3927F, 0.0F, 0.0F));

		ModelPartData cube_r30 = bone10.addChild("cube_r30", ModelPartBuilder.create().uv(86, 95).cuboid(-1.0F, 0.5F, -2.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, -4.7678F, -3.3535F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r31 = bone10.addChild("cube_r31", ModelPartBuilder.create().uv(86, 95).cuboid(-8.0F, 0.0F, -3.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.4142F, 4.4142F, 2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r32 = bone10.addChild("cube_r32", ModelPartBuilder.create().uv(42, 95).cuboid(-8.0F, 0.0F, -3.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 6.2426F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r33 = bone10.addChild("cube_r33", ModelPartBuilder.create().uv(42, 95).cuboid(-1.0F, 0.5F, -2.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, -1.0F, -5.7426F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r34 = bone10.addChild("cube_r34", ModelPartBuilder.create().uv(86, 95).cuboid(-1.0F, 0.5F, -2.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 3.3535F, -4.7678F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r35 = bone10.addChild("cube_r35", ModelPartBuilder.create().uv(42, 95).cuboid(-8.0F, 0.0F, -3.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.2426F, 0.0F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r36 = bone10.addChild("cube_r36", ModelPartBuilder.create().uv(86, 95).cuboid(-8.0F, 0.0F, -3.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 4.4142F, 4.4142F, 0.7854F, 0.0F, 0.0F));

		ModelPartData bone14 = modelPartData.addChild("bone14", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(10.0F, 7.4506F, 1.1035F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.pivot(-8.25F, 12.4896F, -25.9535F));

		ModelPartData cube_r37 = bone14.addChild("cube_r37", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(10.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, -0.7284F, 0.5086F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r38 = bone14.addChild("cube_r38", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(-0.5F, 0.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(10.5F, 0.3322F, 5.0521F, 2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r39 = bone14.addChild("cube_r39", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(3.0F, -1.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(7.0F, 2.9879F, 7.5663F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r40 = bone14.addChild("cube_r40", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(10.0F, -0.5F, 1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, 0.9879F, -1.8592F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r41 = bone14.addChild("cube_r41", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(10.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, 5.5829F, -1.6127F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r42 = bone14.addChild("cube_r42", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(3.0F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(7.0F, -1.4749F, 3.1035F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r43 = bone14.addChild("cube_r43", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(3.0F, -1.0F, -3.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(7.0F, 5.9364F, 7.1734F, 0.7854F, 0.0F, 0.0F));

		ModelPartData bone6 = modelPartData.addChild("bone6", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(10.0F, 7.4506F, 1.1036F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.pivot(-8.25F, 12.4896F, 14.0465F));

		ModelPartData cube_r44 = bone6.addChild("cube_r44", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(10.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, -0.7284F, 0.5086F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r45 = bone6.addChild("cube_r45", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(-0.5F, 0.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(10.5F, 0.3322F, 5.0521F, 2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r46 = bone6.addChild("cube_r46", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(3.0F, -1.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(7.0F, 2.9879F, 7.5663F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r47 = bone6.addChild("cube_r47", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(10.0F, -0.5F, 1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, 0.9879F, -1.8592F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r48 = bone6.addChild("cube_r48", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(10.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, 5.5829F, -1.6128F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r49 = bone6.addChild("cube_r49", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(3.0F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(7.0F, -1.4749F, 3.1035F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r50 = bone6.addChild("cube_r50", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(3.0F, -1.0F, -3.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(7.0F, 5.9364F, 7.1734F, 0.7854F, 0.0F, 0.0F));

		ModelPartData bone11 = modelPartData.addChild("bone11", ModelPartBuilder.create().uv(80, 121).mirrored().cuboid(3.0F, 9.0104F, 0.6036F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-7.0F, 11.2396F, 13.1464F));

		ModelPartData cube_r51 = bone11.addChild("cube_r51", ModelPartBuilder.create().uv(108, 122).mirrored().cuboid(3.0F, -1.5F, -2.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.25F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r52 = bone11.addChild("cube_r52", ModelPartBuilder.create().uv(108, 122).mirrored().cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(7.0F, 0.3536F, 8.0178F, 2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r53 = bone11.addChild("cube_r53", ModelPartBuilder.create().uv(80, 121).mirrored().cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(7.0F, 4.7678F, 9.8462F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r54 = bone11.addChild("cube_r54", ModelPartBuilder.create().uv(80, 121).mirrored().cuboid(3.0F, -1.5F, -2.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 3.7678F, -2.1391F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r55 = bone11.addChild("cube_r55", ModelPartBuilder.create().uv(108, 122).mirrored().cuboid(3.0F, -1.5F, -2.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 8.1213F, -1.1642F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r56 = bone11.addChild("cube_r56", ModelPartBuilder.create().uv(80, 121).mirrored().cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(7.0F, -1.4749F, 3.6036F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r57 = bone11.addChild("cube_r57", ModelPartBuilder.create().uv(108, 122).mirrored().cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(7.0F, 9.182F, 8.0178F, 0.7854F, 0.0F, 0.0F));

		ModelPartData bone8 = modelPartData.addChild("bone8", ModelPartBuilder.create().uv(116, 83).cuboid(2.0F, 10.0104F, 0.8535F, 11.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, 11.2396F, -26.8535F));

		ModelPartData cube_r58 = bone8.addChild("cube_r58", ModelPartBuilder.create().uv(76, 112).cuboid(2.0F, -0.5F, -2.0F, 11.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.25F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r59 = bone8.addChild("cube_r59", ModelPartBuilder.create().uv(76, 112).cuboid(-5.0F, -1.0F, -3.0F, 11.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 0.3536F, 8.0178F, 2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r60 = bone8.addChild("cube_r60", ModelPartBuilder.create().uv(0, 86).cuboid(-5.0F, -1.0F, -3.0F, 10.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 4.7678F, 9.8462F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r61 = bone8.addChild("cube_r61", ModelPartBuilder.create().uv(112, 10).cuboid(2.0F, -0.5F, -2.0F, 11.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.7678F, -2.1391F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r62 = bone8.addChild("cube_r62", ModelPartBuilder.create().uv(76, 112).cuboid(2.0F, -0.5F, -2.0F, 11.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 8.1213F, -1.1642F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r63 = bone8.addChild("cube_r63", ModelPartBuilder.create().uv(112, 10).cuboid(-5.0F, -1.0F, -3.0F, 11.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, -1.4749F, 3.6035F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r64 = bone8.addChild("cube_r64", ModelPartBuilder.create().uv(0, 86).cuboid(-5.0F, -1.0F, -3.0F, 10.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 9.182F, 8.0178F, 0.7854F, 0.0F, 0.0F));

		ModelPartData bone5 = modelPartData.addChild("bone5", ModelPartBuilder.create().uv(114, 19).cuboid(2.0F, 10.0104F, 0.6036F, 11.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, 11.2396F, 12.8964F));

		ModelPartData cube_r65 = bone5.addChild("cube_r65", ModelPartBuilder.create().uv(114, 102).cuboid(2.0F, -0.5F, -2.0F, 11.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.25F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r66 = bone5.addChild("cube_r66", ModelPartBuilder.create().uv(114, 102).cuboid(-5.0F, -1.0F, -3.0F, 11.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 0.3536F, 8.0178F, 2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r67 = bone5.addChild("cube_r67", ModelPartBuilder.create().uv(114, 19).cuboid(-5.0F, -1.0F, -3.0F, 11.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 4.7678F, 9.8462F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r68 = bone5.addChild("cube_r68", ModelPartBuilder.create().uv(114, 19).cuboid(2.0F, -0.5F, -2.0F, 11.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.7678F, -2.1391F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r69 = bone5.addChild("cube_r69", ModelPartBuilder.create().uv(114, 102).cuboid(2.0F, -0.5F, -2.0F, 11.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 8.1213F, -1.1642F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r70 = bone5.addChild("cube_r70", ModelPartBuilder.create().uv(114, 19).cuboid(-5.0F, -1.0F, -3.0F, 11.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, -1.4749F, 3.6036F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r71 = bone5.addChild("cube_r71", ModelPartBuilder.create().uv(114, 102).cuboid(-5.0F, -1.0F, -3.0F, 11.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 9.182F, 8.0178F, 0.7854F, 0.0F, 0.0F));

		ModelPartData bone12 = modelPartData.addChild("bone12", ModelPartBuilder.create().uv(42, 95).cuboid(-8.0F, 6.2426F, -3.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 16.0074F, 16.5F, -0.3927F, 0.0F, 0.0F));

		ModelPartData cube_r72 = bone12.addChild("cube_r72", ModelPartBuilder.create().uv(42, 95).cuboid(-1.0F, 0.5F, -2.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, -4.7678F, -3.3536F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r73 = bone12.addChild("cube_r73", ModelPartBuilder.create().uv(42, 95).cuboid(-8.0F, 0.0F, -3.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.4142F, 4.4142F, 2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r74 = bone12.addChild("cube_r74", ModelPartBuilder.create().uv(42, 95).cuboid(-8.0F, 0.0F, -3.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 6.2426F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r75 = bone12.addChild("cube_r75", ModelPartBuilder.create().uv(42, 95).cuboid(-1.0F, 0.5F, -2.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, -1.0F, -5.7426F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r76 = bone12.addChild("cube_r76", ModelPartBuilder.create().uv(42, 95).cuboid(-1.0F, 0.5F, -2.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 3.3535F, -4.7678F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r77 = bone12.addChild("cube_r77", ModelPartBuilder.create().uv(42, 95).cuboid(-8.0F, 0.0F, -3.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.2426F, 0.0F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r78 = bone12.addChild("cube_r78", ModelPartBuilder.create().uv(42, 95).cuboid(-8.0F, 0.0F, -3.0F, 16.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 4.4142F, 4.4142F, 0.7854F, 0.0F, 0.0F));

		ModelPartData bone2 = modelPartData.addChild("bone2", ModelPartBuilder.create(), ModelTransform.pivot(-7.0F, 10.9896F, -26.6035F));

		ModelPartData cube_r79 = bone2.addChild("cube_r79", ModelPartBuilder.create().uv(88, 54).mirrored().cuboid(4.0F, 3.75F, -2.0F, 1.0F, 0.0F, 5.0F, new Dilation(0.01F)).mirrored(false)
		.uv(100, 69).mirrored().cuboid(3.25F, 3.75F, -2.5F, 2.0F, 0.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 4.4749F, -2.0962F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r80 = bone2.addChild("cube_r80", ModelPartBuilder.create().uv(130, 37).mirrored().cuboid(-0.5F, -3.0F, -4.0F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.0054F, 5.4749F, 11.7923F, -1.5708F, 0.6545F, 0.0F));

		ModelPartData cube_r81 = bone2.addChild("cube_r81", ModelPartBuilder.create().uv(32, 111).mirrored().cuboid(-0.5F, -2.0F, -1.5F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.2567F, 0.5892F, 12.0926F, -1.1338F, 0.504F, 0.7687F));

		ModelPartData cube_r82 = bone2.addChild("cube_r82", ModelPartBuilder.create().uv(102, 131).mirrored().cuboid(-0.5F, -2.5F, -2.5F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.4944F, -1.3652F, 9.1936F, -0.5468F, 0.5956F, 0.7446F));

		ModelPartData bone13 = modelPartData.addChild("bone13", ModelPartBuilder.create(), ModelTransform.of(0.8716F, 13.7974F, 16.3432F, -3.1416F, 0.0F, -3.1416F));

		ModelPartData cube_r83 = bone13.addChild("cube_r83", ModelPartBuilder.create().uv(32, 111).cuboid(-0.5F, -2.0F, -1.5F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-3.8649F, -2.2186F, 9.1459F, -1.1338F, 0.504F, 0.7687F));

		ModelPartData cube_r84 = bone13.addChild("cube_r84", ModelPartBuilder.create().uv(102, 131).cuboid(-0.5F, -2.5F, -2.5F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-4.6271F, -4.173F, 6.2469F, -0.5468F, 0.5956F, 0.7446F));

		ModelPartData cube_r85 = bone13.addChild("cube_r85", ModelPartBuilder.create().uv(130, 37).cuboid(-0.5F, -3.0F, -4.0F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-5.1161F, 2.6671F, 8.8455F, -1.5708F, 0.6545F, 0.0F));

		ModelPartData bone15 = modelPartData.addChild("bone15", ModelPartBuilder.create().uv(108, 54).cuboid(-11.0F, 7.4506F, 1.1035F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.pivot(8.25F, 12.4896F, -25.9535F));

		ModelPartData cube_r86 = bone15.addChild("cube_r86", ModelPartBuilder.create().uv(130, 49).cuboid(-11.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, -0.7284F, 0.5086F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r87 = bone15.addChild("cube_r87", ModelPartBuilder.create().uv(108, 54).cuboid(-11.0F, -0.5F, 1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.9879F, -1.8592F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r88 = bone15.addChild("cube_r88", ModelPartBuilder.create().uv(130, 49).cuboid(-11.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 5.5829F, -1.6127F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r89 = bone15.addChild("cube_r89", ModelPartBuilder.create().uv(108, 54).cuboid(-4.0F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-7.0F, -1.4749F, 3.1035F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r90 = bone15.addChild("cube_r90", ModelPartBuilder.create().uv(130, 49).cuboid(-4.0F, -1.0F, -3.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-7.0F, 5.9364F, 7.1734F, 0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r91 = bone15.addChild("cube_r91", ModelPartBuilder.create().uv(108, 54).cuboid(-4.0F, -1.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-7.0F, 2.9879F, 7.5663F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r92 = bone15.addChild("cube_r92", ModelPartBuilder.create().uv(130, 49).cuboid(-0.5F, 0.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-10.5F, 0.3322F, 5.0521F, 2.3562F, 0.0F, 0.0F));

		ModelPartData bone16 = modelPartData.addChild("bone16", ModelPartBuilder.create().uv(108, 54).cuboid(-11.0F, 7.4506F, 1.1036F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.pivot(8.25F, 12.4896F, 14.0465F));

		ModelPartData cube_r93 = bone16.addChild("cube_r93", ModelPartBuilder.create().uv(130, 49).cuboid(-11.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, -0.7284F, 0.5086F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r94 = bone16.addChild("cube_r94", ModelPartBuilder.create().uv(108, 54).cuboid(-11.0F, -0.5F, 1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.9879F, -1.8592F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r95 = bone16.addChild("cube_r95", ModelPartBuilder.create().uv(130, 49).cuboid(-11.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 5.5829F, -1.6128F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r96 = bone16.addChild("cube_r96", ModelPartBuilder.create().uv(108, 54).cuboid(-4.0F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-7.0F, -1.4749F, 3.1035F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r97 = bone16.addChild("cube_r97", ModelPartBuilder.create().uv(130, 49).cuboid(-4.0F, -1.0F, -3.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-7.0F, 5.9364F, 7.1734F, 0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r98 = bone16.addChild("cube_r98", ModelPartBuilder.create().uv(108, 54).cuboid(-4.0F, -1.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-7.0F, 2.9879F, 7.5663F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r99 = bone16.addChild("cube_r99", ModelPartBuilder.create().uv(130, 49).cuboid(-0.5F, 0.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-10.5F, 0.3322F, 5.0521F, 2.3562F, 0.0F, 0.0F));

		ModelPartData bone17 = modelPartData.addChild("bone17", ModelPartBuilder.create().uv(80, 121).cuboid(-11.0F, 9.0104F, 0.6035F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(7.0F, 11.2396F, -26.8535F));

		ModelPartData cube_r100 = bone17.addChild("cube_r100", ModelPartBuilder.create().uv(108, 122).cuboid(-11.0F, -1.5F, -2.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.25F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r101 = bone17.addChild("cube_r101", ModelPartBuilder.create().uv(108, 122).cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 0.3536F, 8.0178F, 2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r102 = bone17.addChild("cube_r102", ModelPartBuilder.create().uv(80, 121).cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 4.7678F, 9.8462F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r103 = bone17.addChild("cube_r103", ModelPartBuilder.create().uv(80, 121).cuboid(-11.0F, -1.5F, -2.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.7678F, -2.1391F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r104 = bone17.addChild("cube_r104", ModelPartBuilder.create().uv(108, 122).cuboid(-11.0F, -1.5F, -2.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 8.1213F, -1.1642F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r105 = bone17.addChild("cube_r105", ModelPartBuilder.create().uv(80, 121).cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, -1.4749F, 3.6035F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r106 = bone17.addChild("cube_r106", ModelPartBuilder.create().uv(108, 122).cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 9.182F, 8.0178F, 0.7854F, 0.0F, 0.0F));

		ModelPartData Handle = modelPartData.addChild("Handle", ModelPartBuilder.create(), ModelTransform.pivot(8.871F, 14.8017F, -18.6382F));

		ModelPartData bone21 = Handle.addChild("bone21", ModelPartBuilder.create().uv(14, 14).cuboid(-0.7419F, -2.6033F, -0.3236F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.1209F, 0.3017F, 0.1118F));

		ModelPartData bone19 = bone21.addChild("bone19", ModelPartBuilder.create().uv(8, 22).cuboid(0.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.6109F, 0.3054F));

		ModelPartData bone18 = bone21.addChild("bone18", ModelPartBuilder.create().uv(8, 22).cuboid(-0.25F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.2581F, 0.3967F, 0.6764F, 0.0F, -0.1745F, 0.3054F));

		ModelPartData bone20 = Handle.addChild("bone20", ModelPartBuilder.create().uv(3, 6).cuboid(-5.0F, -1.5F, -0.25F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(10, 14).cuboid(-1.0F, -1.5F, -0.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(10, 14).cuboid(-1.0F, 1.5F, -0.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(19, 5).cuboid(-5.0F, -1.5F, -0.25F, 1.0F, 4.0F, 1.0F, new Dilation(0.1F))
		.uv(5, 14).cuboid(-3.75F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(5, 14).cuboid(-2.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.121F, -0.3017F, -0.1118F));

		ModelPartData Handle2 = modelPartData.addChild("Handle2", ModelPartBuilder.create(), ModelTransform.pivot(-8.871F, 14.8017F, -18.6382F));

		ModelPartData bone22 = Handle2.addChild("bone22", ModelPartBuilder.create().uv(14, 14).mirrored().cuboid(-0.2581F, -2.6033F, -0.3236F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-0.1209F, 0.3017F, 0.1118F));

		ModelPartData bone23 = bone22.addChild("bone23", ModelPartBuilder.create().uv(8, 22).mirrored().cuboid(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.6109F, -0.3054F));

		ModelPartData bone24 = bone22.addChild("bone24", ModelPartBuilder.create().uv(8, 22).mirrored().cuboid(-2.75F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.2581F, 0.3967F, 0.6764F, 0.0F, 0.1745F, -0.3054F));

		ModelPartData bone25 = Handle2.addChild("bone25", ModelPartBuilder.create().uv(3, 6).mirrored().cuboid(1.0F, -1.5F, -0.25F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(10, 14).mirrored().cuboid(0.0F, -1.5F, -0.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(10, 14).mirrored().cuboid(0.0F, 1.5F, -0.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(19, 5).mirrored().cuboid(4.0F, -1.5F, -0.25F, 1.0F, 4.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(5, 14).mirrored().cuboid(2.75F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(5, 14).mirrored().cuboid(1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.121F, -0.3017F, -0.1118F));

		ModelPartData bone27 = modelPartData.addChild("bone27", ModelPartBuilder.create().uv(10, 149).mirrored().cuboid(-3.75F, 1.55F, -4.0F, 0.0F, 2.0F, 6.0F, new Dilation(0.01F)).mirrored(false)
		.uv(10, 149).mirrored().cuboid(1.25F, 1.55F, -4.0F, 0.0F, 2.0F, 6.0F, new Dilation(0.01F)).mirrored(false)
		.uv(6, 160).mirrored().cuboid(-3.75F, 0.0F, 2.325F, 5.0F, 5.0F, 2.0F, new Dilation(0.1F)).mirrored(false)
		.uv(2, 138).mirrored().cuboid(-3.75F, 0.0F, -4.25F, 5.0F, 5.0F, 9.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-1.75F, 13.75F, -4.75F));

		ModelPartData cube_r107 = bone27.addChild("cube_r107", ModelPartBuilder.create().uv(10, 149).mirrored().cuboid(0.0F, -1.0F, -4.0F, 0.0F, 2.0F, 6.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		ModelPartData cube_r108 = bone27.addChild("cube_r108", ModelPartBuilder.create().uv(10, 149).mirrored().cuboid(0.0F, -1.0F, -4.0F, 0.0F, 2.0F, 6.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(-2.4F, 5.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		ModelPartData cube_r109 = bone27.addChild("cube_r109", ModelPartBuilder.create().uv(10, 149).mirrored().cuboid(0.0F, -1.0F, -4.0F, 0.0F, 2.0F, 6.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		ModelPartData cube_r110 = bone27.addChild("cube_r110", ModelPartBuilder.create().uv(10, 149).mirrored().cuboid(0.0F, -1.0F, -4.0F, 0.0F, 2.0F, 6.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(-2.4F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 36).cuboid(-4.0F, -8.301F, -8.4032F, 8.0F, 5.0F, 16.0F, new Dilation(0.0F))
		.uv(130, 27).cuboid(-3.0F, -15.7636F, -19.6934F, 6.0F, 4.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 57).cuboid(4.25F, -15.25F, -12.25F, 0.0F, 13.0F, 16.0F, new Dilation(0.0F))
		.uv(0, 57).mirrored().cuboid(-4.25F, -15.25F, -12.25F, 0.0F, 13.0F, 16.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData cube_r111 = bb_main.addChild("cube_r111", ModelPartBuilder.create().uv(76, 69).cuboid(-4.0F, -1.075F, 4.475F, 8.0F, 2.0F, 4.0F, new Dilation(0.06F))
		.uv(76, 69).cuboid(-4.0F, -1.075F, 1.225F, 8.0F, 2.0F, 4.0F, new Dilation(0.06F)), ModelTransform.of(0.0F, -19.5612F, 1.9125F, -0.3491F, 0.0F, 0.0F));

		ModelPartData cube_r112 = bb_main.addChild("cube_r112", ModelPartBuilder.create().uv(76, 69).cuboid(-4.0F, -0.9F, -2.0F, 8.0F, 2.0F, 4.0F, new Dilation(0.06F)), ModelTransform.of(0.0F, -19.5612F, 1.9125F, -0.3054F, 0.0F, 0.0F));

		ModelPartData cube_r113 = bb_main.addChild("cube_r113", ModelPartBuilder.create().uv(76, 69).cuboid(-4.0F, -0.5F, 1.25F, 8.0F, 2.0F, 4.0F, new Dilation(0.06F))
		.uv(76, 69).cuboid(-4.0F, -0.5F, -2.0F, 8.0F, 2.0F, 4.0F, new Dilation(0.06F))
		.uv(116, 0).cuboid(-4.0F, -0.475F, -4.0F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F))
		.uv(130, 91).mirrored().cuboid(-4.075F, -0.125F, -3.0F, 0.0F, 1.0F, 9.0F, new Dilation(0.0F)).mirrored(false)
		.uv(130, 91).cuboid(4.075F, -0.125F, -3.0F, 0.0F, 1.0F, 9.0F, new Dilation(0.0F))
		.uv(110, 112).cuboid(-4.0F, -0.5F, -2.0F, 8.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -21.4563F, -4.3252F, -0.2182F, 0.0F, 0.0F));

		ModelPartData cube_r114 = bb_main.addChild("cube_r114", ModelPartBuilder.create().uv(118, 131).mirrored().cuboid(-0.375F, -0.625F, -3.0F, 0.0F, 1.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-4.25F, -18.68F, 4.1179F, -0.354F, -0.1639F, 0.0602F));

		ModelPartData cube_r115 = bb_main.addChild("cube_r115", ModelPartBuilder.create().uv(42, 102).mirrored().cuboid(-5.0F, -1.0F, -6.0F, 3.0F, 2.0F, 14.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -17.3119F, 7.8767F, -0.3568F, -0.2048F, 0.0757F));

		ModelPartData cube_r116 = bb_main.addChild("cube_r116", ModelPartBuilder.create().uv(42, 102).cuboid(2.0F, -1.0F, -6.0F, 3.0F, 2.0F, 14.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -17.3119F, 7.8767F, -0.3568F, 0.2048F, -0.0757F));

		ModelPartData cube_r117 = bb_main.addChild("cube_r117", ModelPartBuilder.create().uv(118, 131).cuboid(0.375F, -0.625F, -3.0F, 0.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(4.25F, -18.68F, 4.1179F, -0.354F, 0.1639F, -0.0602F));

		ModelPartData cube_r118 = bb_main.addChild("cube_r118", ModelPartBuilder.create().uv(56, 121).cuboid(-0.5F, -3.4F, -6.5F, 1.0F, 3.0F, 11.0F, new Dilation(0.0F))
		.uv(118, 54).cuboid(-7.5F, -3.4F, -6.5F, 1.0F, 4.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(3.5F, -14.1156F, -20.8525F, 0.1309F, 0.0F, 0.0F));

		ModelPartData cube_r119 = bb_main.addChild("cube_r119", ModelPartBuilder.create().uv(72, 10).cuboid(-4.0F, -1.0F, -5.0F, 8.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -16.1368F, 12.0726F, -0.1309F, 0.0F, 0.0F));

		ModelPartData cube_r120 = bb_main.addChild("cube_r120", ModelPartBuilder.create().uv(80, 130).cuboid(-4.0F, -4.5F, -5.0F, 8.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -12.5033F, 4.1378F, -1.2217F, 0.0F, 0.0F));

		ModelPartData cube_r121 = bb_main.addChild("cube_r121", ModelPartBuilder.create().uv(32, 59).cuboid(-4.0F, -0.5F, -3.5F, 8.0F, 2.0F, 14.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -18.9788F, 4.7587F, -0.3491F, 0.0F, 0.0F));

		ModelPartData cube_r122 = bb_main.addChild("cube_r122", ModelPartBuilder.create().uv(32, 118).cuboid(-4.0F, -6.5F, -4.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -11.7533F, 5.1378F, -1.2654F, 0.0F, 0.0F));

		ModelPartData cube_r123 = bb_main.addChild("cube_r123", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -14.0F, 8.0F, 8.0F, 28.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -11.7036F, -3.742F, -0.1745F, 0.0F, 0.0F));

		ModelPartData cube_r124 = bb_main.addChild("cube_r124", ModelPartBuilder.create().uv(0, 111).cuboid(-4.0F, -0.5F, -5.5F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.801F, 11.3468F, -0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r125 = bb_main.addChild("cube_r125", ModelPartBuilder.create().uv(48, 36).cuboid(-4.0F, -2.5F, -6.0F, 8.0F, 11.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -10.3685F, -12.0873F, 0.3054F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 256, 256);
	}
	@Override
	public void setAngles(LightCycleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		Player.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone7.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone4.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone3.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone26.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone9.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone10.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone14.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone6.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone11.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone8.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone5.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone12.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone13.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone15.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone16.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone17.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Handle.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Handle2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone27.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}