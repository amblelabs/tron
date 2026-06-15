package amble.tron;

import amble.tron.core.*;
import amble.tron.core.commands.FactionColorCommand;
import amble.tron.core.entities.LightCycleEntity;
import dev.amble.lib.container.RegistryContainer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.server.network.ServerPlayerEntity;
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
        RegistryContainer.register(TronItemGroups.class, MOD_ID);
        TronAttachmentTypes.init();
        TronSounds.init();
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

        ServerPlayNetworking.registerGlobalReceiver(Tron.of("toggle_beam"), (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.getVehicle() instanceof LightCycleEntity cycle) {
                    cycle.setBeamActive(!cycle.isBeamActive());
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(Tron.of("recall_baton"), (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (!(player.getVehicle() instanceof LightCycleEntity cycle) || cycle.isDying()) {
                    return;
                }

                cycle.beginRecallAnimation(player);
            });
        });
    }

    private void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(TronEntities.LIGHT_CYCLE, LightCycleEntity.createLivingAttributes());
    }
}