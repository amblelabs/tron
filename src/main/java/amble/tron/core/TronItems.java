package amble.tron.core;

import amble.tron.core.items.LightSuitItem;
import amble.tron.core.items.IdentityDiscItem;
import dev.amble.lib.container.impl.ItemContainer;
import dev.amble.lib.item.AItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;

public class TronItems extends ItemContainer {
    public static Item LIGHTSUIT = new LightSuitItem(ArmorMaterials.IRON, ArmorItem.Type.CHESTPLATE, new AItemSettings().maxDamage(100000));

    public static Item IDENTITY_DISC = new IdentityDiscItem(new AItemSettings().maxCount(1));
}
