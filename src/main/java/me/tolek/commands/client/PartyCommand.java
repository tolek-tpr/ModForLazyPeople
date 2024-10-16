package me.tolek.commands.client;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import me.tolek.network.PartyNetworkHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;

public class PartyCommand implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(literal("party")
                    .then(literal("invite").then(argument("player", StringArgumentType.word())
                            .executes(context -> {
                                String player = StringArgumentType.getString(context, "player");
                                context.getSource().sendFeedback(Text.translatable("mflp.party.invitingPlayer", player));
                                PartyNetworkHandler.invitePlayer(player);
                                return 1;
                            })))

                    .then(literal("accept")
                            .executes(context -> {
                                context.getSource().sendFeedback(Text.translatable("mflp.party.acceptingInvite"));
                                PartyNetworkHandler.acceptInvite();
                                return 1;
                            }))

                    .then(literal("decline")
                            .executes(context -> {
                                context.getSource().sendFeedback(Text.translatable("mflp.party.decliningInvite"));
                                PartyNetworkHandler.declineInvite();
                                return 1;
                            }))

                    .then(literal("chat").then(argument("message", StringArgumentType.greedyString())
                            .executes(PartyCommand::chat)))

                    .then(literal("leave")
                            .executes(context -> {
                                context.getSource().sendFeedback(Text.translatable("mflp.party.leavingParty"));
                                PartyNetworkHandler.leaveParty();
                                return 1;
                            }))

                    .then(literal("info")
                            .executes(context -> {
                                PartyNetworkHandler.getPartyInfo();
                                return 1;
                            }))

                    .then(literal("remove").then(argument("player", StringArgumentType.word())
                            .executes(context -> {
                                String player = StringArgumentType.getString(context, "player");
                                context.getSource().sendFeedback(Text.translatable("mflp.party.removingPlayer", player));
                                PartyNetworkHandler.removeMember(player);
                                return 1;
                            })))
            );

            dispatcher.register(literal("pc").then(argument("message", StringArgumentType.greedyString()).executes(PartyCommand::chat)));
        });
    }

    private static int chat(CommandContext<FabricClientCommandSource> context) {
        String message = StringArgumentType.getString(context, "message");
        PartyNetworkHandler.send(message);
        return 1;
    }

}
