package me.tolek.commands.client;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import me.tolek.gui.screens.PartyGui;
import me.tolek.gui.screens.PartyGuiScreen;
import me.tolek.modules.party.Party;
import me.tolek.network.PartyNetworkHandler;
import me.tolek.util.ScreenUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;

@Environment(EnvType.CLIENT)
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
                                if (Party.isInParty()) {
                                    context.getSource().sendFeedback(Text.translatable("mflp.party.leavingParty"));
                                    PartyNetworkHandler.leaveParty();
                                } else {
                                    context.getSource().sendFeedback(Text.literal("You are not in a party!").formatted(Formatting.RED));
                                }
                                return 1;
                            }))

                    .then(literal("info")
                            .executes(context -> {
                                if (!Party.isInParty()) {
                                    context.getSource().sendError(Text.translatable("mflp.party.error.notInParty"));
                                    return 2;
                                }

                                MutableText newLine = Text.literal("\n");
                                MutableText title = Text.translatable("mflp.party.infoTitle").styled(style -> style.withBold(true));
                                MutableText ownerText = Text.literal("  ").append(Text.translatable("mflp.party.infoOwner", Party.getOwner())).styled(style -> style.withBold(false).withItalic(true));
                                MutableText moderatorsTitle = Text.literal("  ").append(Text.translatable("mflp.party.moderators")).styled(style -> style.withBold(true));
                                MutableText moderatorsText = Text.literal("").styled(style -> style.withBold(false).withItalic(true));
                                for (String moderator : Party.getModerators()) {
                                    moderatorsText.append("\n    -%s".formatted(moderator));
                                }
                                MutableText membersTitle = Text.literal("  ").append(Text.translatable("mflp.party.members")).styled(style -> style.withBold(true).withItalic(false));
                                MutableText membersText = Text.literal("").styled(style -> style.withBold(false).withItalic(true));
                                for (String member : Party.getMembers()) {
                                    membersText.append("\n    -%s".formatted(member));
                                }

                                MutableText info = title.append(newLine).append(ownerText).append(newLine).append(moderatorsTitle).append(moderatorsText).append(newLine).append(membersTitle).append(membersText);
                                context.getSource().sendFeedback(info);
                                return 1;
                            }))

                    .then(literal("remove").then(argument("player", StringArgumentType.word())
                            .executes(context -> {
                                String player = StringArgumentType.getString(context, "player");
                                context.getSource().sendFeedback(Text.translatable("mflp.party.removingPlayer", player));
                                PartyNetworkHandler.removeMember(player);
                                return 1;
                            })))

                    .then(literal("promote").then(argument("player", StringArgumentType.word())
                            .executes(context -> {
                                String player = StringArgumentType.getString(context, "player");
                                context.getSource().sendFeedback(Text.translatable("mflp.party.promotingPlayer", player));
                                PartyNetworkHandler.promotePlayer(player);
                                return 1;
                            })))

                    .then(literal("demote").then(argument("player", StringArgumentType.word())
                            .executes(context -> {
                                String player = StringArgumentType.getString(context, "player");
                                context.getSource().sendFeedback(Text.translatable("mflp.party.demotingPlayer", player));
                                PartyNetworkHandler.demotePlayer(player);
                                return 1;
                            })))

                    .then(literal("manage")
                            .executes(context -> {
                                ScreenUtil.openScreen(new PartyGuiScreen(new PartyGui()));
                                return 1;
                            }))
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
