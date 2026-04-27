package amble.tron.core.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.joml.Vector3f;

public interface TronColor {
    String red = "X";
    String green = "Y";
    String blue = "Z";

     default Vector3f getRGB(ItemStack stack) {
        if (!(stack.getItem() instanceof LightSuitItem)) return new Vector3f(1, 1, 1);
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains(red) && nbt.contains(green) && nbt.contains(blue)) {
            return new Vector3f(nbt.getFloat(red), nbt.getFloat(green), nbt.getFloat(blue));
        }
        return new Vector3f(1, 1, 1);
    }

}
