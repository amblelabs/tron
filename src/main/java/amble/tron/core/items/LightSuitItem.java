package amble.tron.core.items;

import amble.tron.Tron;
import amble.tron.core.TronAttachmentTypes;
import amble.tron.core.TronAttachmentUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class LightSuitItem extends ArmorItem {
    public static final Identifier CHANGE_COLOR_LIGHTSUIT = Tron.of("change_color_lightsuit");
    private static final String red = "X";
    private static final String green = "Y";
    private static final String blue = "Z";

    static  {
        ServerPlayNetworking.registerGlobalReceiver(CHANGE_COLOR_LIGHTSUIT, (server, player, handler, buf, responseSender) -> {
            Vector3f color = buf.readVector3f();
            ItemStack stack = buf.readItemStack();

            if (stack.getItem() instanceof LightSuitItem lightSuitItem) {
                lightSuitItem.__setRGB(color, stack);
            }
        });
    }

    public LightSuitItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!(world instanceof ServerWorld)) return;
        if (entity instanceof ServerPlayerEntity player && stack.getItem() instanceof IdentityDiscItem) {
            if (world.getBlockState(player.getBlockPos().down()).getBlock() == Blocks.NETHERITE_BLOCK) {
                Vector3f rectified = new Vector3f(1f, 0.5f, 0.1f);

                TronAttachmentUtil.setFactionColor(player, rectified);
            }
        }
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = new ItemStack(this);
        NbtCompound compound = stack.getOrCreateNbt();
        compound.putFloat(red, 1);
        compound.putFloat(green, 1);
        compound.putFloat(blue, 1);
        return stack;
    }

    public Vector3f getRGB(ItemStack stack) {
        if (!(stack.getItem() instanceof LightSuitItem)) return new Vector3f(1, 1, 1);
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains(red) && nbt.contains(green) && nbt.contains(blue)) {
            return new Vector3f(nbt.getFloat(red), nbt.getFloat(green), nbt.getFloat(blue));
        }
        return new Vector3f(1, 1, 1);
    }

    public void setRGB(Vector3f vector3f, ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putFloat(red, vector3f.x);
        nbt.putFloat(green, vector3f.y);
        nbt.putFloat(blue, vector3f.z);

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeVector3f(vector3f);
        buf.writeItemStack(stack);
        ClientPlayNetworking.send(LightSuitItem.CHANGE_COLOR_LIGHTSUIT, buf);
    }

    protected void __setRGB(Vector3f vector3f, ItemStack stack) {
        if ((stack.getItem() instanceof LightSuitItem)) return;
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putFloat(red, vector3f.x);
        nbt.putFloat(green, vector3f.y);
        nbt.putFloat(blue, vector3f.z);
    }
}
