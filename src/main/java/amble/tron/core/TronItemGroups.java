package amble.tron.core;

import amble.tron.Tron;
import dev.amble.lib.container.impl.ItemGroupContainer;
import dev.amble.lib.itemgroup.AItemGroup;
import net.minecraft.item.ItemStack;

public class TronItemGroups implements ItemGroupContainer {

    public static final AItemGroup MAIN = AItemGroup.builder(Tron.of("item_group"))
            .icon(() -> new ItemStack(TronItems.IDENTITY_DISC)).build();
}
