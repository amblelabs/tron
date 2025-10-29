package amble.tron.core;

import amble.tron.Tron;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
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
            broadcastFactionColor(player, null, color, player.getServer());
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
        int targetId = target.getId();

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeIdentifier(TronAttachmentTypes.FACTION_COLOR.identifier()); // which attachment
        buf.writeInt(targetId);
        buf.writeFloat(color.x());
        buf.writeFloat(color.y());
        buf.writeFloat(color.z());
        ServerPlayNetworking.send(target, ATTACHMENT_UPDATE, buf);

        for (ServerPlayerEntity player : PlayerLookup.tracking(target)) {
            PacketByteBuf buf2 = PacketByteBufs.create();
            buf2.writeIdentifier(TronAttachmentTypes.FACTION_COLOR.identifier());
            buf2.writeInt(targetId);
            buf2.writeFloat(color.x());
            buf2.writeFloat(color.y());
            buf2.writeFloat(color.z());
            ServerPlayNetworking.send(player, ATTACHMENT_UPDATE, buf2);
        }
    }

    // Broadcast example (only when needed — prefer tracking players instead of everyone)
    public static void broadcastFactionColor(ServerPlayerEntity subject, ServerPlayerEntity except, Vector3f color, MinecraftServer server) {
        int subjectId = subject.getId();
        for (ServerPlayerEntity p : server.getPlayerManager().getPlayerList()) {
            if (p != except) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeIdentifier(TronAttachmentTypes.FACTION_COLOR.identifier());
                buf.writeInt(subjectId);
                buf.writeFloat(color.x());
                buf.writeFloat(color.y());
                buf.writeFloat(color.z());
                ServerPlayNetworking.send(p, ATTACHMENT_UPDATE, buf);
            }
        }
    }

}
