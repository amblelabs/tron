package amble.tron.core.commands;

import amble.tron.core.TronAttachmentUtil;
import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;

import java.util.Collection;
import java.util.function.Function;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.joml.Vector3f;

public class FactionColorCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("tron factioncolor")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("red", FloatArgumentType.floatArg())
                        .then(CommandManager.argument("green", FloatArgumentType.floatArg())
                                .then(CommandManager.argument("blue", FloatArgumentType.floatArg())
                                        .executes(context -> {
                                            float r = FloatArgumentType.getFloat(context, "red");
                                            float g = FloatArgumentType.getFloat(context, "green");
                                            float b = FloatArgumentType.getFloat(context, "blue");
                                            ServerPlayerEntity player = context.getSource().getPlayer();
                                            return execute(
                                                    context.getSource(),
                                                    p -> r,
                                                    p -> g,
                                                    p -> b,
                                                    ImmutableList.of(player)
                                            );
                                        })
                                        .then(CommandManager.argument("targets", EntityArgumentType.players())
                                                .executes(context -> {
                                                    float r = FloatArgumentType.getFloat(context, "red");
                                                    float g = FloatArgumentType.getFloat(context, "green");
                                                    float b = FloatArgumentType.getFloat(context, "blue");
                                                    Collection<ServerPlayerEntity> targets = EntityArgumentType.getPlayers(context, "targets");
                                                    return execute(
                                                            context.getSource(),
                                                            p -> r,
                                                            p -> g,
                                                            p -> b,
                                                            targets
                                                    );
                                                })
                                        )
                                )
                        )
                )
        );
    }

    private static int execute(ServerCommandSource source,
                               Function<? super ServerPlayerEntity, Float> redFunc,
                               Function<? super ServerPlayerEntity, Float> greenFunc,
                               Function<? super ServerPlayerEntity, Float> blueFunc,
                               Collection<? extends ServerPlayerEntity> targets) {
        for (ServerPlayerEntity player : targets) {
            float r = redFunc.apply(player);
            float g = greenFunc.apply(player);
            float b = blueFunc.apply(player);
            TronAttachmentUtil.setFactionColor(player, new Vector3f(r, g, b));
        }

        if (targets.size() == 1) {
            ServerPlayerEntity player = targets.iterator().next();
            float r = redFunc.apply(player);
            float g = greenFunc.apply(player);
            float b = blueFunc.apply(player);
            String color = r + "," + g + "," + b;
            TronAttachmentUtil.setFactionColor(player, new Vector3f(r, g, b));
            source.sendFeedback(() -> Text.literal("Set faction color of " + player.getDisplayName().getString() + " to " + color), true);
        } else {
            source.sendFeedback(() -> Text.literal("Set faction color for " + targets.size() + " players"), true);
        }

        return targets.size();
    }
}