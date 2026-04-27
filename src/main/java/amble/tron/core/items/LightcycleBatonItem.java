package amble.tron.core.items;

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
import net.minecraft.world.World;

public class LightcycleBatonItem extends Item {
    public LightcycleBatonItem(AItemSettings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            LightcycleBatonThrownEntity entity = new LightcycleBatonThrownEntity(world, user, stack.copy());
            entity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.8f, 1.0f);
            world.spawnEntity(entity);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.NEUTRAL, 0.5F, 1.0F);

            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(stack, world.isClient());
    }
}


