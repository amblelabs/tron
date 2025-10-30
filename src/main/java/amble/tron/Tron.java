package amble.tron;

import amble.tron.core.*;
import amble.tron.core.commands.FactionColorCommand;
import amble.tron.core.entities.LightCycleEntity;
import dev.amble.lib.container.RegistryContainer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

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
        TronAttachmentTypes.init();
        CommandRegistrationCallback.EVENT.register(((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
            FactionColorCommand.register(commandDispatcher);
        }));

        // Set initial faction color on join
        registerPlayerFactionColor();

        // register entity attributes
        registerEntityAttributes();
	}

    private void registerPlayerFactionColor() {
        ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, server) -> {
            ServerPlayerEntity player = serverPlayNetworkHandler.getPlayer();
            TronAttachmentUtil.setInitialPlayerFaction(player);
        });
    }

    private void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(TronEntities.LIGHT_CYCLE, LightCycleEntity.createLivingAttributes());
    }
}