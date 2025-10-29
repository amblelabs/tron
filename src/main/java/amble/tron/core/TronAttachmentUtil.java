package amble.tron.core;

import amble.tron.Tron;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

public class TronAttachmentUtil {

    public static void setInitialPlayerFaction(ServerPlayerEntity player) {
        boolean bl = player.hasAttached(TronAttachmentTypes.FACTION_COLOR);
        if (bl) return;
        // Attach user color
        Vector3f color = new Vector3f(1, 1, 1);
        player.setAttached(TronAttachmentTypes.FACTION_COLOR, color);
        if (player.getServer() != null) {
            broadcastFactionColor(null, color, player.getServer());
        } else {
            sendFactionColorUpdate(player, color);
        }
    }

    public static Vector3f getFactionColor(PlayerEntity player) {
        boolean bl = player.hasAttached(TronAttachmentTypes.FACTION_COLOR);
        if (!bl) return new Vector3f(1, 1, 1);
        return player.getAttached(TronAttachmentTypes.FACTION_COLOR);
    }

    public static void setFactionColor(ServerPlayerEntity player, Vector3f color) {
        setInitialPlayerFaction(player);
        Vector3f factionColor;
        player.modifyAttached(TronAttachmentTypes.FACTION_COLOR, (vector3f -> color));
        factionColor = player.getAttached(TronAttachmentTypes.FACTION_COLOR);
        sendFactionColorUpdate(player, color);
    }

    public static final Identifier ATTACHMENT_UPDATE = Tron.of("attachment_update");

    public static void sendFactionColorUpdate(ServerPlayerEntity target, Vector3f color) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeIdentifier(Tron.of("faction_color")); // which attachment
        buf.writeFloat(color.x());
        buf.writeFloat(color.y());
        buf.writeFloat(color.z());
        ServerPlayNetworking.send(target, ATTACHMENT_UPDATE, buf);
    }

    // Broadcast example (only when needed — prefer tracking players instead of everyone)
    public static void broadcastFactionColor(ServerPlayerEntity except, Vector3f color, MinecraftServer server) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeIdentifier(Tron.of("faction_color"));
        buf.writeFloat(color.x());
        buf.writeFloat(color.y());
        buf.writeFloat(color.z());
        for (ServerPlayerEntity p : server.getPlayerManager().getPlayerList()) {
            if (p != except) ServerPlayNetworking.send(p, ATTACHMENT_UPDATE, buf);
        }
    }

}
