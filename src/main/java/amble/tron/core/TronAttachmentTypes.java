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
}
