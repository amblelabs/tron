package amble.tron.client;

import amble.tron.Tron;
import amble.tron.client.render.IdentityDiscThrownItemRenderer;
import amble.tron.client.render.LightCycleEntityRenderer;
import amble.tron.core.TronAttachmentTypes;
import amble.tron.core.TronEntities;
import amble.tron.core.entities.LightCycleEntity;
import amble.tron.core.items.IdentityDiscItem;
import amble.tron.core.items.LightSuitItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

import static amble.tron.core.TronAttachmentUtil.ATTACHMENT_UPDATE;
import static amble.tron.core.items.IdentityDiscItem.CHANGE_COLOR;
import static amble.tron.core.items.LightSuitItem.CHANGE_COLOR_LIGHTSUIT;

public class TronClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TronAttachmentTypes.init();
        EntityRendererRegistry.register(TronEntities.IDENTITY_DISC, IdentityDiscThrownItemRenderer::new);
        EntityRendererRegistry.register(TronEntities.LIGHT_CYCLE, LightCycleEntityRenderer::new);
        registerClientReceivers();
    }

    private void registerClientReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(ATTACHMENT_UPDATE, (client, handler, buf, responseSender) -> {
            Identifier attachmentId = buf.readIdentifier(); // must match the server order
            float x = buf.readFloat();
            float y = buf.readFloat();
            float z = buf.readFloat();
            Vector3f color = new Vector3f(x, y, z);

            client.execute(() -> {
                if (client.player == null) return;
                if (attachmentId.equals(Tron.of("faction_color"))) {
                    if (!client.player.hasAttached(TronAttachmentTypes.FACTION_COLOR)) {
                        client.player.setAttached(TronAttachmentTypes.FACTION_COLOR, color);
                    } else {
                        client.player.modifyAttached(TronAttachmentTypes.FACTION_COLOR, vector3f -> color);
                    }
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(CHANGE_COLOR, (client, handler, buf, responseSender) -> {
            Vector3f color = buf.readVector3f();

            client.execute(() -> {
                if (client.player == null) return;
                ItemStack stack = client.player.getActiveHand() == Hand.MAIN_HAND ? client.player.getMainHandStack() : client.player.getOffHandStack();
                if (stack.getItem() instanceof IdentityDiscItem discItem) {
                    discItem.__setRGB(color, stack);
                    client.player.getInventory().markDirty();
                }
            });
        });

        /*ClientPlayNetworking.registerGlobalReceiver(RETRACT_BLADE, (client, handler, buf, responseSender) -> {
            boolean retracted = buf.readBoolean();
            ItemStack stack = client.player.getActiveHand() == Hand.MAIN_HAND ? client.player.getMainHandStack() : client.player.getOffHandStack();

            client.execute(() -> {
                if (client.player == null) return;
                if (stack.getItem() instanceof IdentityDiscItem discItem) {
                    discItem.setBladeRetracted(stack, retracted);
                    client.player.getInventory().markDirty();
                }
            });
        });*/

        ClientPlayNetworking.registerGlobalReceiver(CHANGE_COLOR_LIGHTSUIT, (client, handler, buf, responseSender) -> {
            Vector3f color = buf.readVector3f();
            ItemStack stack = buf.readItemStack();

            if (stack.getItem() instanceof LightSuitItem lightSuitItem) {
                lightSuitItem.__setRGB(color, stack);
            }
        });
    }
}
