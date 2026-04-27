package amble.tron.core.items;

import amble.tron.core.TronAttachmentUtil;
import amble.tron.core.TronEntities;
import amble.tron.core.entities.LightCycleEntity;
import amble.tron.core.entities.LightcycleBatonThrownEntity;
import dev.amble.lib.item.AItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class LightcycleBatonItem extends Item {
    public LightcycleBatonItem(AItemSettings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            if (user.isSneaking()) {
                double yawRad = Math.toRadians(user.getYaw());
                Vec3d spawnPos = user.getPos().add(-Math.sin(yawRad) * 0.8, 0.0, Math.cos(yawRad) * 0.8);

                LightCycleEntity cycle = new LightCycleEntity(TronEntities.LIGHT_CYCLE, world);
                cycle.refreshPositionAndAngles(spawnPos.x, user.getY(), spawnPos.z, user.getYaw(), 0.0f);
                cycle.beginSpawnAnimation();
                cycle.setBeamActive(false);

                Vector3f factionColor = TronAttachmentUtil.getFactionColor(user);
                if (factionColor != null) {
                    cycle.setColor(factionColor);
                }

                world.spawnEntity(cycle);
                user.setSneaking(false);
                user.startRiding(cycle, true);
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.NEUTRAL, 0.8f, 1.2f);
            } else {
                LightcycleBatonThrownEntity entity = new LightcycleBatonThrownEntity(world, user, stack.copy());
                entity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.8f, 1.0f);
                world.spawnEntity(entity);
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.NEUTRAL, 0.5F, 1.0F);
            }

            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(stack, world.isClient());
    }
}



