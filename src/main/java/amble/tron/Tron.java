package amble.tron;

import amble.tron.core.Keybindings;
import amble.tron.core.TronEntities;
import amble.tron.core.TronItems;
import dev.amble.lib.container.RegistryContainer;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tron implements ModInitializer {
	public static final String MOD_ID = "tron";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier of(String string) {
        return new Identifier(MOD_ID, string);
    }

	@Override
	public void onInitialize() {
        RegistryContainer.register(TronItems.class, MOD_ID);
        RegistryContainer.register(TronEntities.class, MOD_ID);
        Keybindings.init();
	}
}