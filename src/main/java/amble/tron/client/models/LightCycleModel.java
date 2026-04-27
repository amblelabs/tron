package amble.tron.client.models;

import amble.tron.core.entities.LightCycleEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class LightCycleModel extends EntityModel<LightCycleEntity> {
	public final ModelPart Player;
	private final ModelPart LeftLeg;
	private final ModelPart Waist;
	private final ModelPart Head;
	private final ModelPart Body;
	private final ModelPart RightArm;
	private final ModelPart LeftArm;
	private final ModelPart Right_Leg2;
	private final ModelPart Body1;
	private final ModelPart FrontWheel;
	private final ModelPart limitter;
	private final ModelPart bone;
	private final ModelPart bone38;
	private final ModelPart bone39;
	private final ModelPart bone40;
	private final ModelPart bone41;
	private final ModelPart InnerFrontWheel;
	private final ModelPart bone17;
	private final ModelPart bone14;
	private final ModelPart bone15;
	private final ModelPart BackWheel;
	private final ModelPart bone28;
	private final ModelPart InnerBackWheel;
	private final ModelPart bone11;
	private final ModelPart bone6;
	private final ModelPart bone16;
	private final ModelPart bone43;
	private final ModelPart bone32;
	private final ModelPart extendedbody;
	private final ModelPart Handle;
	private final ModelPart bone21;
	private final ModelPart bone19;
	private final ModelPart bone18;
	private final ModelPart bone20;
	private final ModelPart bone2;
	private final ModelPart bone29;
	private final ModelPart Handle2;
	private final ModelPart bone22;
	private final ModelPart bone23;
	private final ModelPart bone24;
	private final ModelPart bone25;
	private final ModelPart bone27;
	private final ModelPart bone30;
	private final ModelPart bone31;
	private final ModelPart Body2;
	private final ModelPart bone7;
	private final ModelPart bone5;
	private final ModelPart bone26;
	private final ModelPart bone33;
	private final ModelPart bone34;
	private final ModelPart bone35;
	private final ModelPart bone36;
	private final ModelPart bone37;
	private final ModelPart bone3;
	private final ModelPart bone4;
	private final ModelPart bone13;
	private final ModelPart bone42;
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
		this.Body1 = root.getChild("Body1");
		this.FrontWheel = root.getChild("FrontWheel");
		this.limitter = this.FrontWheel.getChild("limitter");
		this.bone = this.FrontWheel.getChild("bone");
		this.bone38 = this.FrontWheel.getChild("bone38");
		this.bone39 = this.FrontWheel.getChild("bone39");
		this.bone40 = this.FrontWheel.getChild("bone40");
		this.bone41 = this.FrontWheel.getChild("bone41");
		this.InnerFrontWheel = this.FrontWheel.getChild("InnerFrontWheel");
		this.bone17 = this.InnerFrontWheel.getChild("bone17");
		this.bone14 = this.InnerFrontWheel.getChild("bone14");
		this.bone15 = this.InnerFrontWheel.getChild("bone15");
		this.BackWheel = root.getChild("BackWheel");
		this.bone28 = this.BackWheel.getChild("bone28");
		this.InnerBackWheel = this.BackWheel.getChild("InnerBackWheel");
		this.bone11 = this.InnerBackWheel.getChild("bone11");
		this.bone6 = this.InnerBackWheel.getChild("bone6");
		this.bone16 = this.InnerBackWheel.getChild("bone16");
		this.bone43 = this.BackWheel.getChild("bone43");
		this.bone32 = root.getChild("bone32");
		this.extendedbody = root.getChild("extendedbody");
		this.Handle = root.getChild("Handle");
		this.bone21 = this.Handle.getChild("bone21");
		this.bone19 = this.bone21.getChild("bone19");
		this.bone18 = this.bone21.getChild("bone18");
		this.bone20 = this.Handle.getChild("bone20");
		this.bone2 = root.getChild("bone2");
		this.bone29 = this.bone2.getChild("bone29");
		this.Handle2 = root.getChild("Handle2");
		this.bone22 = this.Handle2.getChild("bone22");
		this.bone23 = this.bone22.getChild("bone23");
		this.bone24 = this.bone22.getChild("bone24");
		this.bone25 = this.Handle2.getChild("bone25");
		this.bone27 = root.getChild("bone27");
		this.bone30 = this.bone27.getChild("bone30");
		this.bone31 = this.bone27.getChild("bone31");
		this.Body2 = root.getChild("Body2");
		this.bone7 = this.Body2.getChild("bone7");
		this.bone5 = this.Body2.getChild("bone5");
		this.bone26 = root.getChild("bone26");
		this.bone33 = root.getChild("bone33");
		this.bone34 = this.bone33.getChild("bone34");
		this.bone35 = this.bone33.getChild("bone35");
		this.bone36 = root.getChild("bone36");
		this.bone37 = root.getChild("bone37");
		this.bone3 = root.getChild("bone3");
		this.bone4 = root.getChild("bone4");
		this.bone13 = root.getChild("bone13");
		this.bone42 = root.getChild("bone42");
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

		ModelPartData Body1 = modelPartData.addChild("Body1", ModelPartBuilder.create().uv(0, 36).cuboid(-4.0F, 3.4523F, -13.5411F, 8.0F, 5.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.2467F, 5.1378F));

		ModelPartData cube_r1 = Body1.addChild("cube_r1", ModelPartBuilder.create().uv(56, 121).cuboid(-0.5F, -3.4F, -6.5F, 1.0F, 3.0F, 11.0F, new Dilation(0.0F))
				.uv(56, 121).cuboid(-7.5F, -3.4F, -6.5F, 1.0F, 4.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(3.5F, -2.3623F, -25.9903F, 0.1309F, 0.0F, 0.0F));

		ModelPartData cube_r2 = Body1.addChild("cube_r2", ModelPartBuilder.create().uv(130, 61).cuboid(-3.0F, 3.375F, 3.45F, 6.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.732F, -27.3628F, 0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r3 = Body1.addChild("cube_r3", ModelPartBuilder.create().uv(32, 118).cuboid(-4.0F, -6.5F, -4.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.2654F, 0.0F, 0.0F));

		ModelPartData cube_r4 = Body1.addChild("cube_r4", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -14.0F, 8.0F, 8.0F, 28.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0497F, -8.8798F, -0.1745F, 0.0F, 0.0F));

		ModelPartData cube_r5 = Body1.addChild("cube_r5", ModelPartBuilder.create().uv(0, 111).cuboid(-4.0F, -0.5F, -5.5F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 7.9523F, 6.2089F, -0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r6 = Body1.addChild("cube_r6", ModelPartBuilder.create().uv(138, 104).cuboid(-4.0F, 8.525F, -4.0F, 8.0F, 0.0F, 1.0F, new Dilation(0.01F))
				.uv(48, 36).cuboid(-4.0F, -2.5F, -6.0F, 8.0F, 11.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.3848F, -17.2251F, 0.3054F, 0.0F, 0.0F));

		ModelPartData FrontWheel = modelPartData.addChild("FrontWheel", ModelPartBuilder.create().uv(76, 102).cuboid(-14.0F, 8.1746F, 6.3035F, 14.0F, 5.0F, 5.0F, new Dilation(0.001F))
				.uv(72, 24).mirrored().cuboid(-14.0F, 10.4246F, -0.6465F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)).mirrored(false)
				.uv(76, 59).mirrored().cuboid(-14.0F, -3.4749F, -0.6465F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(7.0F, 10.9896F, -26.6035F));

		ModelPartData cube_r7 = FrontWheel.addChild("cube_r7", ModelPartBuilder.create().uv(102, 131).mirrored().cuboid(-0.5F, -2.5F, -2.5F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-12.5056F, -1.3652F, 9.1936F, -0.5468F, 0.5956F, 0.7446F));

		ModelPartData cube_r8 = FrontWheel.addChild("cube_r8", ModelPartBuilder.create().uv(32, 111).mirrored().cuboid(-0.5F, -2.0F, -1.5F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-11.7433F, 0.5892F, 12.0926F, -1.1338F, 0.504F, 0.7687F));

		ModelPartData cube_r9 = FrontWheel.addChild("cube_r9", ModelPartBuilder.create().uv(130, 37).mirrored().cuboid(-0.5F, -3.0F, -4.0F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-12.9946F, 5.4749F, 11.7923F, -1.5708F, 0.6545F, 0.0F));

		ModelPartData cube_r10 = FrontWheel.addChild("cube_r10", ModelPartBuilder.create().uv(100, 69).mirrored().cuboid(3.25F, 3.75F, -2.5F, 2.0F, 0.0F, 6.0F, new Dilation(0.01F)).mirrored(false)
				.uv(100, 69).cuboid(8.75F, 3.75F, -2.5F, 2.0F, 0.0F, 6.0F, new Dilation(0.01F)), ModelTransform.of(-14.0F, 4.4749F, -1.8962F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r11 = FrontWheel.addChild("cube_r11", ModelPartBuilder.create().uv(88, 54).mirrored().cuboid(4.0F, 3.75F, -2.0F, 1.0F, 0.0F, 5.0F, new Dilation(0.01F)).mirrored(false)
				.uv(88, 54).cuboid(9.0F, 3.75F, -2.0F, 1.0F, 0.0F, 5.0F, new Dilation(0.01F)), ModelTransform.of(-14.0F, 4.4749F, -1.9962F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r12 = FrontWheel.addChild("cube_r12", ModelPartBuilder.create().uv(32, 75).cuboid(-14.0F, 0.5F, -2.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.4142F, 0.4142F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r13 = FrontWheel.addChild("cube_r13", ModelPartBuilder.create().uv(102, 131).cuboid(-1.5F, -2.5F, -2.5F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-1.4944F, -1.3652F, 9.1936F, -0.5468F, -0.5956F, -0.7446F));

		ModelPartData cube_r14 = FrontWheel.addChild("cube_r14", ModelPartBuilder.create().uv(88, 44).cuboid(-14.0F, -1.5F, -3.0F, 14.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.2929F, 7.4142F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r15 = FrontWheel.addChild("cube_r15", ModelPartBuilder.create().uv(130, 37).cuboid(-0.5F, -3.0F, -4.0F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-1.0054F, 5.4749F, 11.7923F, -1.5708F, -0.6545F, 0.0F));

		ModelPartData cube_r16 = FrontWheel.addChild("cube_r16", ModelPartBuilder.create().uv(32, 111).cuboid(-0.5F, -2.0F, -1.5F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-2.2567F, 0.5892F, 12.0926F, -1.1338F, -0.504F, -0.7687F));

		ModelPartData cube_r17 = FrontWheel.addChild("cube_r17", ModelPartBuilder.create().uv(72, 0).cuboid(-14.0F, -5.5F, -2.0F, 14.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.4749F, 5.8033F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r18 = FrontWheel.addChild("cube_r18", ModelPartBuilder.create().uv(74, 75).cuboid(-14.0F, 0.5F, -3.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.001F)), ModelTransform.of(0.0F, 4.4749F, -2.0962F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r19 = FrontWheel.addChild("cube_r19", ModelPartBuilder.create().uv(32, 75).cuboid(-14.0F, 0.5F, -3.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 8.1213F, -1.0F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r20 = FrontWheel.addChild("cube_r20", ModelPartBuilder.create().uv(137, 70).cuboid(-3.0F, -1.5F, -1.5F, 6.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, -3.1397F, 7.2173F, 0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r21 = FrontWheel.addChild("cube_r21", ModelPartBuilder.create().uv(0, 104).cuboid(-14.0F, 0.5F, -3.0F, 14.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 8.8284F, 7.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData limitter = FrontWheel.addChild("limitter", ModelPartBuilder.create().uv(146, 82).cuboid(-1.125F, -0.625F, 0.0625F, 1.0F, 2.0F, 0.0F, new Dilation(0.001F))
				.uv(146, 82).cuboid(0.125F, -0.625F, 0.0625F, 1.0F, 2.0F, 0.0F, new Dilation(0.001F))
				.uv(147, 85).cuboid(-1.625F, -1.125F, 0.0125F, 4.0F, 2.0F, 0.0F, new Dilation(0.01F))
				.uv(146, 82).cuboid(-2.15F, -1.375F, -0.0625F, 5.0F, 2.0F, 0.0F, new Dilation(0.01F)), ModelTransform.of(-7.35F, -4.1897F, 9.6048F, 0.5236F, 0.0F, 0.0F));

		ModelPartData bone = FrontWheel.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(-7.5F, 4.8499F, -5.0962F));

		ModelPartData cube_r22 = bone.addChild("cube_r22", ModelPartBuilder.create().uv(32, 58).cuboid(-8.0F, 7.0F, 0.75F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 58).cuboid(-8.0F, 7.0F, -0.5F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 58).cuboid(-8.0F, 7.0F, -1.75F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 58).cuboid(-8.0F, 7.0F, -3.0F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 0.625F, 7.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData bone38 = FrontWheel.addChild("bone38", ModelPartBuilder.create(), ModelTransform.pivot(-6.4F, -1.0353F, -2.8031F));

		ModelPartData cube_r23 = bone38.addChild("cube_r23", ModelPartBuilder.create().uv(32, 57).cuboid(-9.4F, 3.3F, -1.75F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 57).cuboid(-9.4F, 3.3F, -0.5F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 57).cuboid(-9.4F, 3.3F, 0.75F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 57).cuboid(-9.4F, 3.3F, 2.0F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 57).cuboid(-9.4F, 3.3F, 3.25F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(6.4F, 1.4496F, 3.2173F, -2.3562F, 0.0F, 0.0F));

		ModelPartData bone39 = FrontWheel.addChild("bone39", ModelPartBuilder.create(), ModelTransform.of(-6.4F, 10.6646F, -2.8031F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r24 = bone39.addChild("cube_r24", ModelPartBuilder.create().uv(32, 57).cuboid(-9.4F, 3.3F, -1.75F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 57).cuboid(-9.4F, 3.3F, -0.5F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 57).cuboid(-9.4F, 3.3F, 0.75F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 57).cuboid(-9.4F, 3.3F, 2.0F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 57).cuboid(-9.4F, 3.3F, 3.25F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(6.4F, 1.4496F, 3.2173F, -2.3562F, 0.0F, 0.0F));

		ModelPartData bone40 = FrontWheel.addChild("bone40", ModelPartBuilder.create().uv(32, 60).cuboid(-3.0F, 0.0F, 2.0F, 6.0F, 0.0F, 1.0F, new Dilation(0.01F))
				.uv(32, 60).cuboid(-3.0F, 0.0F, 0.75F, 6.0F, 0.0F, 1.0F, new Dilation(0.01F))
				.uv(32, 60).cuboid(-3.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new Dilation(0.01F))
				.uv(32, 60).cuboid(-3.0F, 0.0F, -3.0F, 6.0F, 0.0F, 1.0F, new Dilation(0.01F))
				.uv(32, 60).cuboid(-3.0F, 0.0F, -1.75F, 6.0F, 0.0F, 1.0F, new Dilation(0.01F)), ModelTransform.pivot(-6.4F, -3.3108F, 2.4892F));

		ModelPartData bone41 = FrontWheel.addChild("bone41", ModelPartBuilder.create().uv(32, 60).cuboid(-3.0F, 0.0F, 2.0F, 6.0F, 0.0F, 1.0F, new Dilation(0.01F))
				.uv(32, 60).cuboid(-3.0F, 0.0F, 0.75F, 6.0F, 0.0F, 1.0F, new Dilation(0.01F))
				.uv(32, 60).cuboid(-3.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new Dilation(0.01F))
				.uv(32, 60).cuboid(-3.0F, 0.0F, -3.0F, 6.0F, 0.0F, 1.0F, new Dilation(0.01F))
				.uv(32, 60).cuboid(-3.0F, 0.0F, -1.75F, 6.0F, 0.0F, 1.0F, new Dilation(0.01F)), ModelTransform.pivot(-7.15F, 13.6892F, 3.9892F));

		ModelPartData InnerFrontWheel = FrontWheel.addChild("InnerFrontWheel", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.25F, -0.25F));

		ModelPartData bone17 = InnerFrontWheel.addChild("bone17", ModelPartBuilder.create().uv(80, 121).cuboid(-11.0F, 9.0104F, 0.6035F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r25 = bone17.addChild("cube_r25", ModelPartBuilder.create().uv(108, 122).cuboid(-11.0F, -1.5F, -2.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.01F)), ModelTransform.of(0.0F, 0.0F, 0.25F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r26 = bone17.addChild("cube_r26", ModelPartBuilder.create().uv(108, 122).cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.01F)), ModelTransform.of(-7.0F, 0.3536F, 8.0178F, 2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r27 = bone17.addChild("cube_r27", ModelPartBuilder.create().uv(80, 121).cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 4.7678F, 9.8462F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r28 = bone17.addChild("cube_r28", ModelPartBuilder.create().uv(80, 121).cuboid(-11.0F, -1.5F, -2.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.7678F, -2.1391F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r29 = bone17.addChild("cube_r29", ModelPartBuilder.create().uv(108, 122).cuboid(-11.0F, -1.5F, -2.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.01F)), ModelTransform.of(0.0F, 8.1213F, -1.1642F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r30 = bone17.addChild("cube_r30", ModelPartBuilder.create().uv(80, 121).cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, -1.4749F, 3.6035F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r31 = bone17.addChild("cube_r31", ModelPartBuilder.create().uv(108, 122).cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.01F)), ModelTransform.of(-7.0F, 9.182F, 8.0178F, 0.7854F, 0.0F, 0.0F));

		ModelPartData bone14 = InnerFrontWheel.addChild("bone14", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(10.0F, 7.4506F, 1.1035F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.pivot(-15.25F, 1.25F, 0.9F));

		ModelPartData cube_r32 = bone14.addChild("cube_r32", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(10.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, -0.7284F, 0.5086F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r33 = bone14.addChild("cube_r33", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(-0.5F, 0.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(10.5F, 0.3322F, 5.0521F, 2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r34 = bone14.addChild("cube_r34", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(3.0F, -1.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(7.0F, 2.9879F, 7.5663F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r35 = bone14.addChild("cube_r35", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(10.0F, -0.5F, 1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, 0.9879F, -1.8592F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r36 = bone14.addChild("cube_r36", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(10.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, 5.5829F, -1.6127F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r37 = bone14.addChild("cube_r37", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(3.0F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(7.0F, -1.4749F, 3.1035F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r38 = bone14.addChild("cube_r38", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(3.0F, -1.0F, -3.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(7.0F, 5.9364F, 7.1734F, 0.7854F, 0.0F, 0.0F));

		ModelPartData bone15 = InnerFrontWheel.addChild("bone15", ModelPartBuilder.create().uv(108, 54).cuboid(-11.0F, 7.4506F, 1.1035F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.pivot(1.25F, 1.25F, 0.9F));

		ModelPartData cube_r39 = bone15.addChild("cube_r39", ModelPartBuilder.create().uv(130, 49).cuboid(-11.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, -0.7284F, 0.5086F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r40 = bone15.addChild("cube_r40", ModelPartBuilder.create().uv(108, 54).cuboid(-11.0F, -0.5F, 1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.9879F, -1.8592F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r41 = bone15.addChild("cube_r41", ModelPartBuilder.create().uv(130, 49).cuboid(-11.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 5.5829F, -1.6127F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r42 = bone15.addChild("cube_r42", ModelPartBuilder.create().uv(108, 54).cuboid(-4.0F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-7.0F, -1.4749F, 3.1035F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r43 = bone15.addChild("cube_r43", ModelPartBuilder.create().uv(130, 49).cuboid(-4.0F, -1.0F, -3.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-7.0F, 5.9364F, 7.1734F, 0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r44 = bone15.addChild("cube_r44", ModelPartBuilder.create().uv(108, 54).cuboid(-4.0F, -1.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-7.0F, 2.9879F, 7.5663F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r45 = bone15.addChild("cube_r45", ModelPartBuilder.create().uv(130, 49).cuboid(-0.5F, 0.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-10.5F, 0.3322F, 5.0521F, 2.3562F, 0.0F, 0.0F));

		ModelPartData BackWheel = modelPartData.addChild("BackWheel", ModelPartBuilder.create().uv(72, 24).mirrored().cuboid(-7.8784F, 7.6168F, -3.5932F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)).mirrored(false)
				.uv(76, 59).mirrored().cuboid(-7.8784F, -6.2827F, -3.5932F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)).mirrored(false)
				.uv(116, 69).cuboid(-1.8784F, -8.2827F, -3.5932F, 2.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(-0.8716F, 13.7974F, 16.3432F, -3.1416F, 0.0F, 3.1416F));

		ModelPartData cube_r46 = BackWheel.addChild("cube_r46", ModelPartBuilder.create().uv(32, 75).mirrored().cuboid(-14.0F, 0.5F, -2.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(6.1216F, -2.3936F, -2.5325F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r47 = BackWheel.addChild("cube_r47", ModelPartBuilder.create().uv(88, 44).cuboid(-14.0F, -1.5F, -3.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(6.1216F, -3.1007F, 4.4675F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r48 = BackWheel.addChild("cube_r48", ModelPartBuilder.create().uv(72, 0).mirrored().cuboid(-14.0F, -5.5F, -2.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(6.1216F, 0.6671F, 2.8566F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r49 = BackWheel.addChild("cube_r49", ModelPartBuilder.create().uv(21, 139).cuboid(-1.5F, 0.0F, -3.5F, 3.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-0.8784F, -3.8078F, -5.8181F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r50 = BackWheel.addChild("cube_r50", ModelPartBuilder.create().uv(25, 147).cuboid(-1.5F, 0.0F, -3.5F, 3.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-0.8784F, 8.1419F, -5.8181F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r51 = BackWheel.addChild("cube_r51", ModelPartBuilder.create().uv(21, 139).cuboid(-8.0F, 3.5F, -3.0F, 3.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(5.6216F, 1.6671F, -4.7929F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r52 = BackWheel.addChild("cube_r52", ModelPartBuilder.create().uv(74, 85).cuboid(-14.0F, 0.5F, -3.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.001F)), ModelTransform.of(6.1216F, 1.6671F, -5.0429F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r53 = BackWheel.addChild("cube_r53", ModelPartBuilder.create().uv(32, 75).cuboid(-14.0F, 0.5F, -3.0F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(6.1216F, 5.3135F, -3.9467F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r54 = BackWheel.addChild("cube_r54", ModelPartBuilder.create().uv(72, 24).mirrored().cuboid(-7.0F, -1.5F, -3.5F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.8784F, 7.0813F, 4.821F, 0.7854F, 0.0F, 0.0F));

		ModelPartData bone28 = BackWheel.addChild("bone28", ModelPartBuilder.create(), ModelTransform.pivot(5.1161F, 2.6671F, 8.8455F));

		ModelPartData cube_r55 = bone28.addChild("cube_r55", ModelPartBuilder.create().uv(130, 37).mirrored().cuboid(-0.5F, -3.0F, -4.0F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.5708F, -0.6545F, 0.0F));

		ModelPartData cube_r56 = bone28.addChild("cube_r56", ModelPartBuilder.create().uv(102, 131).mirrored().cuboid(-1.5F, -2.5F, -2.5F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.489F, -6.8401F, -2.5986F, -0.5468F, -0.5956F, -0.7446F));

		ModelPartData cube_r57 = bone28.addChild("cube_r57", ModelPartBuilder.create().uv(32, 111).mirrored().cuboid(-0.5F, -2.0F, -1.5F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.2512F, -4.8857F, 0.3004F, -1.1338F, -0.504F, -0.7687F));

		ModelPartData InnerBackWheel = BackWheel.addChild("InnerBackWheel", ModelPartBuilder.create(), ModelTransform.pivot(6.8716F, -1.3078F, -2.2967F));

		ModelPartData bone11 = InnerBackWheel.addChild("bone11", ModelPartBuilder.create().uv(80, 121).mirrored().cuboid(3.0F, 9.0104F, 0.6036F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-15.25F, -1.25F, -0.9F));

		ModelPartData cube_r58 = bone11.addChild("cube_r58", ModelPartBuilder.create().uv(108, 122).mirrored().cuboid(3.0F, -1.5F, -2.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.25F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r59 = bone11.addChild("cube_r59", ModelPartBuilder.create().uv(108, 122).mirrored().cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(7.0F, 0.3536F, 8.0178F, 2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r60 = bone11.addChild("cube_r60", ModelPartBuilder.create().uv(80, 121).mirrored().cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(7.0F, 4.7678F, 9.8462F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r61 = bone11.addChild("cube_r61", ModelPartBuilder.create().uv(80, 121).mirrored().cuboid(3.0F, -1.5F, -2.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 3.7678F, -2.1391F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r62 = bone11.addChild("cube_r62", ModelPartBuilder.create().uv(108, 122).mirrored().cuboid(3.0F, -1.5F, -2.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(0.0F, 8.1213F, -1.1642F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r63 = bone11.addChild("cube_r63", ModelPartBuilder.create().uv(80, 121).mirrored().cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(7.0F, -1.4749F, 3.6036F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r64 = bone11.addChild("cube_r64", ModelPartBuilder.create().uv(108, 122).mirrored().cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 3.0F, 6.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(7.0F, 9.182F, 8.0178F, 0.7854F, 0.0F, 0.0F));

		ModelPartData bone6 = InnerBackWheel.addChild("bone6", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(10.0F, 7.4506F, 1.1036F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.pivot(-16.5F, 0.0F, 0.0F));

		ModelPartData cube_r65 = bone6.addChild("cube_r65", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(10.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, -0.7284F, 0.5086F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r66 = bone6.addChild("cube_r66", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(-0.5F, 0.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(10.5F, 0.3322F, 5.0521F, 2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r67 = bone6.addChild("cube_r67", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(3.0F, -1.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(7.0F, 2.9879F, 7.5663F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r68 = bone6.addChild("cube_r68", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(10.0F, -0.5F, 1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, 0.9879F, -1.8592F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r69 = bone6.addChild("cube_r69", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(10.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, 5.5829F, -1.6128F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r70 = bone6.addChild("cube_r70", ModelPartBuilder.create().uv(108, 54).mirrored().cuboid(3.0F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(7.0F, -1.4749F, 3.1035F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r71 = bone6.addChild("cube_r71", ModelPartBuilder.create().uv(130, 49).mirrored().cuboid(3.0F, -1.0F, -3.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(7.0F, 5.9364F, 7.1734F, 0.7854F, 0.0F, 0.0F));

		ModelPartData bone16 = InnerBackWheel.addChild("bone16", ModelPartBuilder.create().uv(108, 54).cuboid(-11.0F, 7.4506F, 1.1036F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r72 = bone16.addChild("cube_r72", ModelPartBuilder.create().uv(130, 49).cuboid(-11.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, -0.7284F, 0.5086F, -2.3562F, 0.0F, 0.0F));

		ModelPartData cube_r73 = bone16.addChild("cube_r73", ModelPartBuilder.create().uv(108, 54).cuboid(-11.0F, -0.5F, 1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.9879F, -1.8592F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r74 = bone16.addChild("cube_r74", ModelPartBuilder.create().uv(130, 49).cuboid(-11.0F, -0.5F, 0.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 5.5829F, -1.6128F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r75 = bone16.addChild("cube_r75", ModelPartBuilder.create().uv(108, 54).cuboid(-4.0F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-7.0F, -1.4749F, 3.1035F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r76 = bone16.addChild("cube_r76", ModelPartBuilder.create().uv(130, 49).cuboid(-4.0F, -1.0F, -3.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-7.0F, 5.9364F, 7.1734F, 0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r77 = bone16.addChild("cube_r77", ModelPartBuilder.create().uv(108, 54).cuboid(-4.0F, -1.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-7.0F, 2.9879F, 7.5663F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r78 = bone16.addChild("cube_r78", ModelPartBuilder.create().uv(130, 49).cuboid(-0.5F, 0.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(-10.5F, 0.3322F, 5.0521F, 2.3562F, 0.0F, 0.0F));

		ModelPartData bone43 = BackWheel.addChild("bone43", ModelPartBuilder.create().uv(32, 60).cuboid(-3.0F, 0.0F, 2.0F, 6.0F, 0.0F, 1.0F, new Dilation(0.01F))
				.uv(32, 60).cuboid(-3.0F, 0.0F, 0.75F, 6.0F, 0.0F, 1.0F, new Dilation(0.01F))
				.uv(32, 60).cuboid(-3.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new Dilation(0.01F))
				.uv(32, 60).cuboid(-3.0F, 0.0F, -3.0F, 6.0F, 0.0F, 1.0F, new Dilation(0.01F))
				.uv(32, 60).cuboid(-3.0F, 0.0F, -1.75F, 6.0F, 0.0F, 1.0F, new Dilation(0.01F)), ModelTransform.pivot(-0.7784F, 10.8814F, 0.0425F));

		ModelPartData bone32 = modelPartData.addChild("bone32", ModelPartBuilder.create().uv(11, 150).cuboid(2.5222F, -0.9611F, -3.7583F, 0.0F, 2.0F, 5.0F, new Dilation(0.01F))
				.uv(11, 150).cuboid(-2.4778F, -0.9611F, -3.7583F, 0.0F, 2.0F, 5.0F, new Dilation(0.01F))
				.uv(7, 161).cuboid(-2.4778F, -2.5111F, 2.5667F, 5.0F, 5.0F, 1.0F, new Dilation(0.1F))
				.uv(3, 139).cuboid(-2.4778F, -2.5111F, -4.0083F, 5.0F, 5.0F, 8.0F, new Dilation(0.0F))
				.uv(22, 161).cuboid(-2.4778F, -2.5111F, -4.0083F, 5.0F, 5.0F, 8.0F, new Dilation(0.2F)), ModelTransform.of(3.2278F, 16.2611F, -4.9917F, 0.0F, 0.0F, 0.7854F));

		ModelPartData cube_r79 = bone32.addChild("cube_r79", ModelPartBuilder.create().uv(11, 150).cuboid(0.0F, -1.0F, -3.0F, 0.0F, 2.0F, 5.0F, new Dilation(0.01F)), ModelTransform.of(-1.2278F, 2.4889F, -0.7583F, 0.0F, 0.0F, -1.5708F));

		ModelPartData cube_r80 = bone32.addChild("cube_r80", ModelPartBuilder.create().uv(11, 150).cuboid(0.0F, -1.0F, -3.0F, 0.0F, 2.0F, 5.0F, new Dilation(0.01F)), ModelTransform.of(1.1722F, 2.4889F, -0.7583F, 0.0F, 0.0F, -1.5708F));

		ModelPartData cube_r81 = bone32.addChild("cube_r81", ModelPartBuilder.create().uv(11, 150).cuboid(0.0F, -1.0F, -3.0F, 0.0F, 2.0F, 5.0F, new Dilation(0.01F)), ModelTransform.of(-1.2278F, -2.5111F, -0.7583F, 0.0F, 0.0F, -1.5708F));

		ModelPartData cube_r82 = bone32.addChild("cube_r82", ModelPartBuilder.create().uv(11, 150).cuboid(0.0F, -1.0F, -3.0F, 0.0F, 2.0F, 5.0F, new Dilation(0.01F)), ModelTransform.of(1.1722F, -2.5111F, -0.7583F, 0.0F, 0.0F, -1.5708F));

		ModelPartData extendedbody = modelPartData.addChild("extendedbody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 2.5437F, -4.3252F));

		ModelPartData cube_r83 = extendedbody.addChild("cube_r83", ModelPartBuilder.create().uv(130, 91).mirrored().cuboid(-4.075F, -0.125F, -3.0F, 0.0F, 1.0F, 9.0F, new Dilation(0.01F)).mirrored(false)
				.uv(130, 91).cuboid(4.075F, -0.125F, -3.0F, 0.0F, 1.0F, 9.0F, new Dilation(0.01F))
				.uv(122, 6).cuboid(-4.0F, -0.5F, -4.0F, 8.0F, 0.0F, 2.0F, new Dilation(0.0F))
				.uv(76, 69).cuboid(-4.0F, -0.5F, -2.0F, 8.0F, 2.0F, 4.0F, new Dilation(0.06F))
				.uv(76, 69).cuboid(-4.0F, -0.5F, 1.25F, 8.0F, 2.0F, 4.0F, new Dilation(0.06F))
				.uv(110, 112).cuboid(-4.0F, -0.5F, -2.0F, 8.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData cube_r84 = extendedbody.addChild("cube_r84", ModelPartBuilder.create().uv(76, 69).cuboid(-4.0F, -1.075F, 4.475F, 8.0F, 2.0F, 4.0F, new Dilation(0.06F))
				.uv(76, 69).cuboid(-4.0F, -1.075F, 1.225F, 8.0F, 2.0F, 4.0F, new Dilation(0.06F)), ModelTransform.of(0.0F, 1.895F, 6.2377F, -0.3491F, 0.0F, 0.0F));

		ModelPartData cube_r85 = extendedbody.addChild("cube_r85", ModelPartBuilder.create().uv(76, 69).cuboid(-4.0F, -0.9F, -2.0F, 8.0F, 2.0F, 4.0F, new Dilation(0.06F)), ModelTransform.of(0.0F, 1.895F, 6.2377F, -0.3054F, 0.0F, 0.0F));

		ModelPartData cube_r86 = extendedbody.addChild("cube_r86", ModelPartBuilder.create().uv(72, 10).cuboid(-4.0F, -1.0F, -5.0F, 8.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 5.3195F, 16.3978F, -0.1309F, 0.0F, 0.0F));

		ModelPartData Handle = modelPartData.addChild("Handle", ModelPartBuilder.create(), ModelTransform.of(8.871F, 13.9004F, -19.1385F, 0.2618F, 0.0F, 0.0F));

		ModelPartData bone21 = Handle.addChild("bone21", ModelPartBuilder.create().uv(14, 14).cuboid(-0.7419F, -2.6033F, -0.3236F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F))
				.uv(18, 18).cuboid(-0.7419F, -1.6033F, -0.3236F, 1.0F, 3.0F, 3.0F, new Dilation(0.1F)), ModelTransform.pivot(0.1209F, 0.3017F, 0.1118F));

		ModelPartData bone19 = bone21.addChild("bone19", ModelPartBuilder.create().uv(8, 22).cuboid(0.0F, -0.5F, 0.45F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		ModelPartData bone18 = bone21.addChild("bone18", ModelPartBuilder.create().uv(8, 22).cuboid(0.0105F, -0.5F, 0.9772F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.2581F, 0.3967F, 0.6764F, 0.0F, -0.1745F, 0.3054F));

		ModelPartData bone20 = Handle.addChild("bone20", ModelPartBuilder.create().uv(3, 6).cuboid(-5.0F, -1.5F, -0.25F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F))
				.uv(10, 14).cuboid(-1.0F, -1.5F, -0.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(10, 14).cuboid(-1.0F, 1.5F, -0.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(19, 5).cuboid(-5.0F, -1.5F, -0.25F, 1.0F, 4.0F, 1.0F, new Dilation(0.1F)), ModelTransform.pivot(-0.121F, -0.3017F, -0.1118F));

		ModelPartData cube_r87 = bone20.addChild("cube_r87", ModelPartBuilder.create().uv(5, 14).cuboid(0.375F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(5, 14).cuboid(-1.375F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.875F, 0.5F, -0.5F, 0.0F, 0.0F, 1.5708F));

		ModelPartData bone2 = modelPartData.addChild("bone2", ModelPartBuilder.create(), ModelTransform.pivot(4.971F, 12.6955F, 7.9032F));

		ModelPartData bone29 = bone2.addChild("bone29", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, -3.1416F));

		ModelPartData cube_r88 = bone29.addChild("cube_r88", ModelPartBuilder.create().uv(130, 37).cuboid(-0.5F, -3.0F, -4.0F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-1.0167F, 3.769F, 0.4055F, -1.5708F, 0.6545F, 0.0F));

		ModelPartData cube_r89 = bone29.addChild("cube_r89", ModelPartBuilder.create().uv(32, 111).cuboid(-0.5F, -2.0F, -1.5F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.2346F, -1.1167F, 0.7059F, -1.1338F, 0.504F, 0.7687F));

		ModelPartData cube_r90 = bone29.addChild("cube_r90", ModelPartBuilder.create().uv(102, 131).cuboid(-0.5F, -2.5F, -2.5F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-0.5277F, -3.0711F, -2.1931F, -0.5468F, 0.5956F, 0.7446F));

		ModelPartData Handle2 = modelPartData.addChild("Handle2", ModelPartBuilder.create(), ModelTransform.of(-8.871F, 13.9004F, -19.1385F, 0.2618F, 0.0F, 0.0F));

		ModelPartData bone22 = Handle2.addChild("bone22", ModelPartBuilder.create().uv(18, 18).mirrored().cuboid(-0.2581F, -1.6033F, -0.3236F, 1.0F, 3.0F, 3.0F, new Dilation(0.1F)).mirrored(false)
				.uv(14, 14).mirrored().cuboid(-0.2581F, -2.6033F, -0.3236F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-0.1209F, 0.3017F, 0.1118F));

		ModelPartData bone23 = bone22.addChild("bone23", ModelPartBuilder.create().uv(8, 22).mirrored().cuboid(-2.0F, -0.5F, 0.45F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

		ModelPartData bone24 = bone22.addChild("bone24", ModelPartBuilder.create().uv(8, 22).mirrored().cuboid(-3.0105F, -0.5F, 0.9772F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.2581F, 0.3967F, 0.6764F, 0.0F, 0.1745F, -0.3054F));

		ModelPartData bone25 = Handle2.addChild("bone25", ModelPartBuilder.create().uv(3, 6).mirrored().cuboid(1.0F, -1.5F, -0.25F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
				.uv(10, 14).mirrored().cuboid(0.0F, -1.5F, -0.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
				.uv(10, 14).mirrored().cuboid(0.0F, 1.5F, -0.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
				.uv(19, 5).mirrored().cuboid(4.0F, -1.5F, -0.25F, 1.0F, 4.0F, 1.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.pivot(0.121F, -0.3017F, -0.1118F));

		ModelPartData cube_r91 = bone25.addChild("cube_r91", ModelPartBuilder.create().uv(5, 14).mirrored().cuboid(-1.375F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
				.uv(5, 14).mirrored().cuboid(0.375F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.875F, 0.5F, -0.5F, 0.0F, 0.0F, -1.5708F));

		ModelPartData bone27 = modelPartData.addChild("bone27", ModelPartBuilder.create().uv(49, 5).cuboid(-3.2F, -3.75F, -1.75F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(51, 21).cuboid(-5.2F, -3.75F, -1.5F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F))
				.uv(52, 15).cuboid(-0.7F, -2.25F, -0.275F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(50, 14).cuboid(-1.2F, -3.75F, -0.25F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(8.7F, 17.75F, 12.75F));

		ModelPartData bone30 = bone27.addChild("bone30", ModelPartBuilder.create().uv(64, 15).cuboid(0.05F, -0.25F, -0.5F, 0.0F, 3.0F, 1.0F, new Dilation(0.001F))
				.uv(8, 22).cuboid(-0.7F, 1.75F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

		ModelPartData bone31 = bone27.addChild("bone31", ModelPartBuilder.create().uv(8, 22).cuboid(3.0F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(64, 15).cuboid(5.0F, -3.0F, -1.0F, 0.0F, 3.0F, 1.0F, new Dilation(0.001F)), ModelTransform.pivot(-2.95F, 2.25F, 0.75F));

		ModelPartData Body2 = modelPartData.addChild("Body2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 7.2655F, 5.6171F));

		ModelPartData cube_r92 = Body2.addChild("cube_r92", ModelPartBuilder.create().uv(32, 59).cuboid(-4.0F, -0.5F, -3.5F, 8.0F, 2.0F, 14.0F, new Dilation(0.001F)), ModelTransform.of(0.0F, -2.2444F, -0.8584F, -0.3491F, 0.0F, 0.0F));

		ModelPartData cube_r93 = Body2.addChild("cube_r93", ModelPartBuilder.create().uv(80, 130).cuboid(-4.0F, -4.5F, -5.0F, 8.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 4.2312F, -1.4793F, -1.2217F, 0.0F, 0.0F));

		ModelPartData cube_r94 = Body2.addChild("cube_r94", ModelPartBuilder.create().uv(42, 102).mirrored().cuboid(-5.0F, -1.0F, -6.0F, 3.0F, 2.0F, 14.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -0.5775F, 2.2596F, -0.3568F, -0.2048F, 0.0757F));

		ModelPartData cube_r95 = Body2.addChild("cube_r95", ModelPartBuilder.create().uv(42, 102).cuboid(2.0F, -1.0F, -6.0F, 3.0F, 2.0F, 14.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.5775F, 2.2596F, -0.3568F, 0.2048F, -0.0757F));

		ModelPartData cube_r96 = Body2.addChild("cube_r96", ModelPartBuilder.create().uv(118, 131).cuboid(0.375F, -0.625F, -3.0F, 0.0F, 1.0F, 7.0F, new Dilation(0.01F)), ModelTransform.of(4.25F, -1.9455F, -1.4992F, -0.354F, 0.1639F, -0.0602F));

		ModelPartData cube_r97 = Body2.addChild("cube_r97", ModelPartBuilder.create().uv(118, 131).mirrored().cuboid(-0.375F, -0.625F, -3.0F, 0.0F, 1.0F, 7.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(-4.25F, -1.9455F, -1.4992F, -0.354F, -0.1639F, 0.0602F));

		ModelPartData bone7 = Body2.addChild("bone7", ModelPartBuilder.create(), ModelTransform.of(6.9359F, 2.7501F, -2.6113F, 0.0F, 0.0F, -0.0873F));

		ModelPartData cube_r98 = bone7.addChild("cube_r98", ModelPartBuilder.create().uv(72, 139).cuboid(-0.5F, -0.5F, -4.5F, 1.0F, 1.0F, 9.0F, new Dilation(0.01F)), ModelTransform.of(0.0815F, 0.5276F, 3.3959F, -2.6312F, -0.0663F, -0.1102F));

		ModelPartData cube_r99 = bone7.addChild("cube_r99", ModelPartBuilder.create().uv(72, 139).cuboid(-0.975F, -4.0F, -6.5F, 1.0F, 6.0F, 9.0F, new Dilation(0.01F)), ModelTransform.of(0.4313F, 2.2091F, 2.792F, -2.2385F, -0.0663F, -0.1102F));

		ModelPartData cube_r100 = bone7.addChild("cube_r100", ModelPartBuilder.create().uv(49, 137).cuboid(-0.7F, -3.0F, -2.5F, 1.0F, 4.0F, 5.0F, new Dilation(0.01F)), ModelTransform.of(-0.4488F, -0.718F, 0.6127F, -2.6808F, 0.1393F, -0.2727F));

		ModelPartData cube_r101 = bone7.addChild("cube_r101", ModelPartBuilder.create().uv(100, 54).cuboid(0.35F, -0.55F, -2.375F, 0.0F, 1.0F, 4.0F, new Dilation(0.01F)), ModelTransform.of(-0.1F, -0.075F, 0.0F, -2.7633F, 0.2276F, -0.461F));

		ModelPartData cube_r102 = bone7.addChild("cube_r102", ModelPartBuilder.create().uv(151, 121).cuboid(0.35F, -3.0F, -7.0F, 0.0F, 5.0F, 5.0F, new Dilation(0.01F)), ModelTransform.of(-1.1781F, -2.6395F, 3.9942F, 0.0F, 0.0F, -0.48F));

		ModelPartData cube_r103 = bone7.addChild("cube_r103", ModelPartBuilder.create().uv(39, 137).cuboid(-0.675F, -2.5F, -2.0F, 1.0F, 5.0F, 4.0F, new Dilation(0.01F))
				.uv(21, 122).cuboid(0.425F, -2.5F, -2.0F, 0.0F, 5.0F, 5.0F, new Dilation(0.01F)), ModelTransform.of(-1.1781F, -2.6395F, 3.9942F, 0.0F, 0.0F, -0.5672F));

		ModelPartData bone5 = Body2.addChild("bone5", ModelPartBuilder.create(), ModelTransform.of(-6.9359F, 2.7501F, -2.6113F, 0.0F, 0.0F, 0.0873F));

		ModelPartData cube_r104 = bone5.addChild("cube_r104", ModelPartBuilder.create().uv(72, 139).mirrored().cuboid(-0.5F, -0.5F, -4.5F, 1.0F, 1.0F, 9.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(-0.0815F, 0.5276F, 3.3959F, -2.6312F, 0.0663F, 0.1102F));

		ModelPartData cube_r105 = bone5.addChild("cube_r105", ModelPartBuilder.create().uv(49, 137).mirrored().cuboid(-0.3F, -3.0F, -2.5F, 1.0F, 4.0F, 5.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(0.4488F, -0.718F, 0.6127F, -2.6808F, -0.1393F, 0.2727F));

		ModelPartData cube_r106 = bone5.addChild("cube_r106", ModelPartBuilder.create().uv(100, 54).mirrored().cuboid(-0.35F, -0.55F, -2.375F, 0.0F, 1.0F, 4.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(0.1F, -0.075F, 0.0F, -2.7633F, -0.2276F, 0.461F));

		ModelPartData cube_r107 = bone5.addChild("cube_r107", ModelPartBuilder.create().uv(151, 121).mirrored().cuboid(-0.35F, -3.0F, -7.0F, 0.0F, 5.0F, 5.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(1.1781F, -2.6395F, 3.9942F, 0.0F, 0.0F, 0.48F));

		ModelPartData cube_r108 = bone5.addChild("cube_r108", ModelPartBuilder.create().uv(39, 137).mirrored().cuboid(-0.325F, -2.5F, -2.0F, 1.0F, 5.0F, 4.0F, new Dilation(0.01F)).mirrored(false)
				.uv(21, 122).mirrored().cuboid(-0.425F, -2.5F, -2.0F, 0.0F, 5.0F, 5.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(1.1781F, -2.6395F, 3.9942F, 0.0F, 0.0F, 0.5672F));

		ModelPartData cube_r109 = bone5.addChild("cube_r109", ModelPartBuilder.create().uv(72, 139).mirrored().cuboid(-0.025F, -4.0F, -6.5F, 1.0F, 6.0F, 9.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(-0.4313F, 2.2091F, 2.792F, -2.2385F, 0.0663F, 0.1102F));

		ModelPartData bone26 = modelPartData.addChild("bone26", ModelPartBuilder.create().uv(22, 161).mirrored().cuboid(-2.5222F, -2.5111F, -4.0083F, 5.0F, 5.0F, 8.0F, new Dilation(0.2F)).mirrored(false)
				.uv(11, 150).mirrored().cuboid(-2.5222F, -0.9611F, -3.7583F, 0.0F, 2.0F, 5.0F, new Dilation(0.01F)).mirrored(false)
				.uv(11, 150).mirrored().cuboid(2.4778F, -0.9611F, -3.7583F, 0.0F, 2.0F, 5.0F, new Dilation(0.01F)).mirrored(false)
				.uv(7, 161).mirrored().cuboid(-2.5222F, -2.5111F, 2.5667F, 5.0F, 5.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
				.uv(3, 139).mirrored().cuboid(-2.5222F, -2.5111F, -4.0083F, 5.0F, 5.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2278F, 16.2611F, -4.9917F, 0.0F, 0.0F, -0.7854F));

		ModelPartData cube_r110 = bone26.addChild("cube_r110", ModelPartBuilder.create().uv(11, 150).mirrored().cuboid(0.0F, -1.0F, -3.0F, 0.0F, 2.0F, 5.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(1.2278F, 2.4889F, -0.7583F, 0.0F, 0.0F, 1.5708F));

		ModelPartData cube_r111 = bone26.addChild("cube_r111", ModelPartBuilder.create().uv(11, 150).mirrored().cuboid(0.0F, -1.0F, -3.0F, 0.0F, 2.0F, 5.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(-1.1722F, 2.4889F, -0.7583F, 0.0F, 0.0F, 1.5708F));

		ModelPartData cube_r112 = bone26.addChild("cube_r112", ModelPartBuilder.create().uv(11, 150).mirrored().cuboid(0.0F, -1.0F, -3.0F, 0.0F, 2.0F, 5.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(1.2278F, -2.5111F, -0.7583F, 0.0F, 0.0F, 1.5708F));

		ModelPartData cube_r113 = bone26.addChild("cube_r113", ModelPartBuilder.create().uv(11, 150).mirrored().cuboid(0.0F, -1.0F, -3.0F, 0.0F, 2.0F, 5.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(-1.1722F, -2.5111F, -0.7583F, 0.0F, 0.0F, 1.5708F));

		ModelPartData bone33 = modelPartData.addChild("bone33", ModelPartBuilder.create().uv(51, 21).mirrored().cuboid(3.2F, -3.75F, -1.5F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
				.uv(49, 5).mirrored().cuboid(1.2F, -3.75F, -1.75F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
				.uv(52, 15).mirrored().cuboid(-1.3F, -2.25F, -0.275F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
				.uv(50, 14).mirrored().cuboid(-2.8F, -3.75F, -0.25F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-8.7F, 17.75F, 12.75F));

		ModelPartData bone34 = bone33.addChild("bone34", ModelPartBuilder.create().uv(64, 15).mirrored().cuboid(-0.05F, -0.25F, -0.5F, 0.0F, 3.0F, 1.0F, new Dilation(0.001F)).mirrored(false)
				.uv(8, 22).mirrored().cuboid(-1.3F, 1.75F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

		ModelPartData bone35 = bone33.addChild("bone35", ModelPartBuilder.create().uv(8, 22).mirrored().cuboid(-6.0F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
				.uv(64, 15).mirrored().cuboid(-5.0F, -3.0F, -1.0F, 0.0F, 3.0F, 1.0F, new Dilation(0.001F)).mirrored(false), ModelTransform.pivot(2.95F, 2.25F, 0.75F));

		ModelPartData bone36 = modelPartData.addChild("bone36", ModelPartBuilder.create().uv(48, 163).cuboid(-6.4F, -0.25F, -7.5F, 10.0F, 0.0F, 10.0F, new Dilation(0.001F))
				.uv(75, 164).cuboid(-6.0F, 0.0F, -7.0F, 9.0F, 0.0F, 9.0F, new Dilation(0.001F)), ModelTransform.pivot(1.4F, 24.75F, -20.25F));

		ModelPartData bone37 = modelPartData.addChild("bone37", ModelPartBuilder.create().uv(48, 163).cuboid(-6.4F, -0.25F, -7.5F, 10.0F, 0.0F, 10.0F, new Dilation(0.001F))
				.uv(75, 164).cuboid(-6.0F, 0.0F, -7.0F, 9.0F, 0.0F, 9.0F, new Dilation(0.001F)), ModelTransform.pivot(1.4F, 24.75F, 18.75F));

		ModelPartData bone3 = modelPartData.addChild("bone3", ModelPartBuilder.create().uv(110, 142).cuboid(-0.975F, -13.5F, -8.5F, 0.0F, 14.0F, 14.0F, new Dilation(0.001F))
				.uv(144, 142).cuboid(-1.0F, -13.0F, -8.0F, 0.0F, 13.0F, 13.0F, new Dilation(0.001F)), ModelTransform.pivot(8.375F, 22.5F, -22.0F));

		ModelPartData bone4 = modelPartData.addChild("bone4", ModelPartBuilder.create().uv(144, 142).cuboid(-1.025F, -13.5F, -7.5F, 0.0F, 13.0F, 13.0F, new Dilation(0.001F))
				.uv(110, 142).cuboid(-1.0F, -14.0F, -8.0F, 0.0F, 14.0F, 14.0F, new Dilation(0.001F)), ModelTransform.pivot(8.4F, 23.0F, 17.25F));

		ModelPartData bone13 = modelPartData.addChild("bone13", ModelPartBuilder.create().uv(110, 142).mirrored().cuboid(0.975F, -13.5F, -8.5F, 0.0F, 14.0F, 14.0F, new Dilation(0.001F)).mirrored(false)
				.uv(144, 142).mirrored().cuboid(1.0F, -13.0F, -8.0F, 0.0F, 13.0F, 13.0F, new Dilation(0.001F)).mirrored(false), ModelTransform.pivot(-8.375F, 22.5F, -22.0F));

		ModelPartData bone42 = modelPartData.addChild("bone42", ModelPartBuilder.create().uv(144, 142).mirrored().cuboid(1.025F, -13.5F, -7.5F, 0.0F, 13.0F, 13.0F, new Dilation(0.001F)).mirrored(false)
				.uv(110, 142).mirrored().cuboid(1.0F, -14.0F, -8.0F, 0.0F, 14.0F, 14.0F, new Dilation(0.001F)).mirrored(false), ModelTransform.pivot(-8.4F, 23.0F, 17.25F));

		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 57).cuboid(4.25F, -15.25F, -12.75F, 0.0F, 13.0F, 16.0F, new Dilation(0.01F))
				.uv(0, 57).mirrored().cuboid(-4.25F, -15.25F, -12.75F, 0.0F, 13.0F, 16.0F, new Dilation(0.01F)).mirrored(false)
				.uv(45, 140).mirrored().cuboid(-4.5F, -10.8F, -9.5F, 0.0F, 6.0F, 10.0F, new Dilation(0.01F)).mirrored(false)
				.uv(45, 140).cuboid(4.5F, -10.8F, -9.5F, 0.0F, 6.0F, 10.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 256, 256);
	}

	@Override
	public void setAngles(LightCycleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		// Player.render(matrices, vertexConsumer, light, overlay, red, green, blue, 0.5f);
		Body1.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		FrontWheel.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		BackWheel.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone32.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		extendedbody.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Handle.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Handle2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone27.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Body2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone26.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone33.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone36.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone37.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone3.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone4.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone13.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone42.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	public static void transformMixinModel(float i, float j, PlayerEntityModel playerEntityModel) {
		playerEntityModel.head.pitch = j * 0.017453292F + 0.35F;
		playerEntityModel.head.yaw = i * 0.017453292F;
		playerEntityModel.head.roll = 0.0F;

		playerEntityModel.body.pitch = 1.2F;
		playerEntityModel.body.yaw = 0.0F;
		playerEntityModel.body.roll = 0.0F;

		playerEntityModel.rightArm.pitch = -0.65F;
		playerEntityModel.rightArm.yaw = 0.25F;
		playerEntityModel.rightArm.roll = 0.5F;

		playerEntityModel.leftArm.pitch = -0.65F;
		playerEntityModel.leftArm.yaw = -0.25F;
		playerEntityModel.leftArm.roll = -0.5F;

		// Legs straddling the bike
		playerEntityModel.rightLeg.pitch = 0.8F;
		playerEntityModel.rightLeg.yaw = -0.5F;
		playerEntityModel.rightLeg.roll = 0.35F;

		playerEntityModel.leftLeg.pitch = 0.8F;
		playerEntityModel.leftLeg.yaw = 0.5F;
		playerEntityModel.leftLeg.roll = -0.35F;

		playerEntityModel.hat.copyTransform(playerEntityModel.head);
		playerEntityModel.jacket.copyTransform(playerEntityModel.body);
		playerEntityModel.rightSleeve.copyTransform(playerEntityModel.rightArm);
		playerEntityModel.leftSleeve.copyTransform(playerEntityModel.leftArm);
		playerEntityModel.rightPants.copyTransform(playerEntityModel.rightLeg);
		playerEntityModel.leftPants.copyTransform(playerEntityModel.leftLeg);

		float seatZ = -10f;
		float seatY = -1f;

		playerEntityModel.body.pivotZ = seatZ;
		playerEntityModel.jacket.pivotZ = seatZ;
		playerEntityModel.head.pivotZ = seatZ;
		playerEntityModel.hat.pivotZ = seatZ;

		playerEntityModel.leftArm.pivotZ = seatZ;
		playerEntityModel.leftSleeve.pivotZ = seatZ;
		playerEntityModel.rightArm.pivotZ = seatZ;
		playerEntityModel.rightSleeve.pivotZ = seatZ;

		playerEntityModel.body.pivotY = seatY;
		playerEntityModel.jacket.pivotY = seatY;
		playerEntityModel.head.pivotY = seatY;
		playerEntityModel.hat.pivotY = seatY;

		playerEntityModel.leftArm.pivotY = seatY;
		playerEntityModel.leftSleeve.pivotY = seatY;
		playerEntityModel.rightArm.pivotY = seatY;
		playerEntityModel.rightSleeve.pivotY = seatY;

		playerEntityModel.leftLeg.pivotZ = 2f;
		playerEntityModel.leftPants.pivotZ = 2f;
		playerEntityModel.rightLeg.pivotZ = 2f;
		playerEntityModel.rightPants.pivotZ = 2f;

		playerEntityModel.leftLeg.pivotY = 0f;
		playerEntityModel.leftPants.pivotY = 0f;
		playerEntityModel.rightLeg.pivotY = 0f;
		playerEntityModel.rightPants.pivotY = 0f;
	}
}

