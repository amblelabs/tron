package amble.tron.core.items;

import amble.tron.Tron;
import amble.tron.core.TronAttachmentUtil;
import amble.tron.core.entities.IdentityDiscThrownEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.joml.Vector3f;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.EquipmentSlot;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap;

public class IdentityDiscItem extends Item implements TronColor {
    public static final Identifier CHANGE_COLOR = Tron.of("change_color");
    public static final Identifier RETRACT_BLADE = Tron.of("retract_blade");
    private static final String bladeRetracted = "bladeRetracted";

    static  {
        ServerPlayNetworking.registerGlobalReceiver(RETRACT_BLADE, (server, player, handler, buf, responseSender) -> {
            boolean retract = buf.readBoolean();
            ItemStack stack = player.getMainHandStack();

            if (stack.getItem() instanceof IdentityDiscItem discItem) {
                discItem.__setBladeRetracted(stack, retract);
                player.getInventory().markDirty();
            }
        });
    }

    public IdentityDiscItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = new ItemStack(this);
        NbtCompound compound = stack.getOrCreateNbt();
        compound.putFloat(red, 1);
        compound.putFloat(green, 1);
        compound.putFloat(blue, 1);
        compound.putBoolean(bladeRetracted, true);
        return stack;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!(world instanceof ServerWorld)) return;
        if (entity instanceof ServerPlayerEntity player && stack.getItem() instanceof IdentityDiscItem) {
            Vector3f factionColor = TronAttachmentUtil.getFactionColor(player);
            if (!this.getRGB(stack).equals(factionColor)) {
                this.setRGB(player, factionColor, stack);
            }
        }
    }

    public boolean isBladeRetracted(ItemStack stack) {
        if (!(stack.getItem() instanceof IdentityDiscItem)) return true;
        NbtCompound nbt = stack.getOrCreateNbt();
        if(nbt.contains(bladeRetracted)){
            return nbt.getBoolean(bladeRetracted);
        }
        return false;
    }

    public void setBladeRetracted(ItemStack stack, boolean retract) {
        __setBladeRetracted(stack, retract);
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(retract);
        ClientPlayNetworking.send(IdentityDiscItem.RETRACT_BLADE, buf);
    }

    public void __setBladeRetracted(ItemStack stack, boolean retract) {
        if (!(stack.getItem() instanceof IdentityDiscItem)) return;
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putBoolean(bladeRetracted, retract);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!(itemStack.getItem() instanceof IdentityDiscItem)) return TypedActionResult.fail(itemStack);
        if (isBladeRetracted(itemStack)) return TypedActionResult.fail(itemStack);
        if (!world.isClient()) {
            IdentityDiscThrownEntity discThrownEntity = new IdentityDiscThrownEntity(world, user, itemStack);
            float yawOffset = user.getYaw();
            discThrownEntity.setVelocity(user, user.getPitch(), yawOffset, 0.0F, 4f, 0f);
            discThrownEntity.setColor(this.getRGB(itemStack));
            world.spawnEntity(discThrownEntity);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), this.getDefaultSound(), SoundCategory.NEUTRAL, 0.5F, 1F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        itemStack.decrement(1);

        ItemCooldownManager cooldownManager = user.getItemCooldownManager();
        cooldownManager.set(this, 40);

        return TypedActionResult.success(itemStack, world.isClient());
    }

    public SoundEvent getDefaultSound() {
        return SoundEvents.ITEM_TRIDENT_THROW;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND && !isBladeRetracted(stack)) {
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(
                    ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 8.0, EntityAttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(
                    ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2.4, EntityAttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(stack, slot);
    }

    public void setRGB(ServerPlayerEntity player, Vector3f vector3f, ItemStack stack) {
        __setRGB(vector3f, stack);

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeVector3f(vector3f);
        ServerPlayNetworking.send(player, IdentityDiscItem.CHANGE_COLOR, buf);
    }

    public void __setRGB(Vector3f vector3f, ItemStack stack) {
        if (!(stack.getItem() instanceof IdentityDiscItem)) return;
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putFloat(red, vector3f.x);
        nbt.putFloat(green, vector3f.y);
        nbt.putFloat(blue, vector3f.z);
    }
}
