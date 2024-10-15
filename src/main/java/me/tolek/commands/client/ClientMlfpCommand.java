package me.tolek.commands.client;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.tolek.ModForLazyPeople;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;

public class ClientMlfpCommand implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(literal("party")
                    .then(literal("invite").then(argument("user", StringArgumentType.word()))
                    .executes(context -> {
                        String user = StringArgumentType.getString(context, "user");
                        ModForLazyPeople.LOGGER.info("INVITE {}", user);
                        context.getSource().sendFeedback(Text.literal("Player has been invited. (or not because it isn't implemented yet lmao)")); // TODO: Text.translatable
                        return 1;
                    }))

                    .then(literal("accept")
                    .executes(context -> {
                        ModForLazyPeople.LOGGER.info("ACCEPT");
                        context.getSource().sendFeedback(Text.literal("Invite Accepted! (or not because it isn't implemented yet lmao)")); // TODO: Text.translatable
                        return 1;
                    }))

                    .then(literal("send").then(argument("message", StringArgumentType.greedyString()))
                    .executes(context -> {
                        String message = StringArgumentType.getString(context, "message");
                        ModForLazyPeople.LOGGER.info("SEND {}", message);
                        context.getSource().sendFeedback(Text.literal("You: ").append(message).formatted(Formatting.ITALIC, Formatting.GRAY)); // TODO: Text.translatable
                        return 1;
                    })));
        });
    }
}
