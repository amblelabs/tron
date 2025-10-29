package amble.tron.core;

import amble.tron.Tron;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

import java.util.stream.Stream;

public class TronAttachmentTypes {

    public static void init() {}

    public static final AttachmentType<Vector3f> FACTION_COLOR = AttachmentRegistry.<Vector3f>builder()
            .persistent(Codec.of(TronAttachmentTypes.vector3fEncoder(), TronAttachmentTypes.vector3fDecoder()))
            .copyOnDeath()
            .initializer(() -> new Vector3f(1, 1, 1))
            .buildAndRegister(Tron.of("faction_color"));


    public static Encoder<Vector3f> vector3fEncoder() {
        return new Encoder<>() {
            @Override
            public <T> DataResult<T> encode(Vector3f input, DynamicOps<T> ops, T prefix) {
                T x = ops.createFloat(input.x());
                T y = ops.createFloat(input.y());
                T z = ops.createFloat(input.z());

                Stream<Pair<T, T>> entries = Stream.of(
                        Pair.of(ops.createString("x"), x),
                        Pair.of(ops.createString("y"), y),
                        Pair.of(ops.createString("z"), z)
                );

                T map = ops.createMap(entries);
                return DataResult.success(map);
            }
        };
    }

    public static Decoder<Vector3f> vector3fDecoder() {
        return RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.FLOAT.fieldOf("x").forGetter(Vector3f::x),
                        Codec.FLOAT.fieldOf("y").forGetter(Vector3f::y),
                        Codec.FLOAT.fieldOf("z").forGetter(Vector3f::z)
                ).apply(instance, Vector3f::new)
        );
    }

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

    public static Vector3f setFactionColor(ServerPlayerEntity player, Vector3f color) {
        setInitialPlayerFaction(player);
        Vector3f factionColor;
        player.modifyAttached(TronAttachmentTypes.FACTION_COLOR, (vector3f -> color));
        factionColor = player.getAttached(TronAttachmentTypes.FACTION_COLOR);
        sendFactionColorUpdate(player, color);
        return factionColor;
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
