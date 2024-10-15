package me.tolek.commands.client;

import com.mojang.brigadier.arguments.StringArgumentType;
import me.tolek.ModForLazyPeople;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;

public class PartyCommand implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(literal("party")
                .then(literal("invite").then(argument("player", StringArgumentType.word())
                .executes(context -> {
                    String player = StringArgumentType.getString(context, "player");
                    ModForLazyPeople.LOGGER.info("INVITE {}", player);
                    context.getSource().sendFeedback(Text.literal("Player has been invited. (or not because it isn't implemented yet lmao)")); // TODO: Text.translatable
                    return 1;
                })))

                .then(literal("accept")
                .executes(context -> {
                    ModForLazyPeople.LOGGER.info("ACCEPT");
                    context.getSource().sendFeedback(Text.literal("Invite Accepted! (or not because it isn't implemented yet lmao)")); // TODO: Text.translatable
                    return 1;
                }))


                .then(literal("send").then(argument("message", StringArgumentType.greedyString())
                .executes(context -> {
                    String message = StringArgumentType.getString(context, "message");
                    ModForLazyPeople.LOGGER.info("SEND {}", message);
                    context.getSource().sendFeedback(Text.literal("PARTY NAME GOES HERE - ").formatted(Formatting.BOLD).append(Text.literal("You: ").append(message).formatted(Formatting.ITALIC, Formatting.GRAY))); // TODO: Remove me in favor of the event listener
                    return 1;
                })))

                .then(literal("create").then(argument("name", StringArgumentType.greedyString())
                .executes(context -> {
                    String name = StringArgumentType.getString(context, "name");
                    ModForLazyPeople.LOGGER.info("CREATE {}", name);
                    context.getSource().sendFeedback(Text.literal("Party created with name: " + name + "! (or not because it isn't implemented yet lmao)"));
                    return 1;
                })))

                .then(literal("rename").then(argument("name", StringArgumentType.greedyString())
                .executes(context -> {
                    String name = StringArgumentType.getString(context, "name");
                    ModForLazyPeople.LOGGER.info("RENAME {}", name);
                    context.getSource().sendFeedback(Text.literal("Party renamed to: " + name + "! (or not because it isn't implemented yet lmao)"));
                    return 1;
                })))

                .then(literal("leave")
                .executes(context -> {
                    ModForLazyPeople.LOGGER.info("LEAVE");
                    context.getSource().sendFeedback(Text.literal("Left party! (or not because it isn't implemented yet lmao)")); // TODO: Text.translatable
                    return 1;
                }))

                .then(literal("info")
                .executes(context -> {
                    ModForLazyPeople.LOGGER.info("INFO");
                    context.getSource().sendFeedback(Text.literal("party information goes here"));
                    return 1;
                }))

                .then(literal("remove").then(argument("player", StringArgumentType.word())
                .executes(context -> {
                    String player = StringArgumentType.getString(context, "player");
                    ModForLazyPeople.LOGGER.info("REMOVE {}", player);
                    context.getSource().sendFeedback(Text.literal("Player has been removed. (or not because it isn't implemented yet lmao)")); // TODO: Text.translatable
                    return 1;
                })))
        ));
    }
}
