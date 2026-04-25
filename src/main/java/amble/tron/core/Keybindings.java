package amble.tron.core;

import java.util.ArrayList;
import java.util.List;

import amble.tron.core.bind.KeyBind;
import amble.tron.core.items.IdentityDiscItem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import amble.tron.Tron;
import amble.tron.core.entities.LightCycleEntity;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;

public class Keybindings {

    private static final List<KeyBind> BINDS = new ArrayList<>();

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (KeyBind bind : BINDS)
                bind.tick(client);
        });
        register(new KeyBind.Held("ignite_blade", "main", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, client -> {
            ClientPlayerEntity player = client.player;

            if (player == null)
                return;

            ItemStack stack = player.getActiveHand() == Hand.MAIN_HAND ? player.getMainHandStack() : player.getOffHandStack();

            if (stack.getItem() instanceof IdentityDiscItem discItem) {
                discItem.setBladeRetracted(stack, !discItem.isBladeRetracted(stack));
                player.getInventory().markDirty();
                player.playSound(SoundEvents.ITEM_AXE_SCRAPE, 0.5f, 0.8f);
            }
        }));

        register(new KeyBind.Held("toggle_beam", "main", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, client -> {
            ClientPlayerEntity player = client.player;

            if (player == null)
                return;

            if (player.getVehicle() instanceof LightCycleEntity) {
                ClientPlayNetworking.send(Tron.of("toggle_beam"), PacketByteBufs.empty());
                player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.5f, 1.0f);
            }
        }));
    }

    private static void register(KeyBind bind) {
        bind.register();
        BINDS.add(bind);
    }
}
