package me.tolek.network;

import me.tolek.event.EventManager;
import me.tolek.event.PartyListener;
import me.tolek.gui.screens.FailedToConnectToMflpNetworkScreen;
import me.tolek.gui.screens.NonMflpUserScreen;
import me.tolek.modules.party.Party;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.network.client.WebsocketHandler;
import me.tolek.network.packet.C2S.party.*;
import me.tolek.network.packet.S2C.party.PartyChangeS2CPacket;
import me.tolek.network.packet.S2C.party.PartyErrorS2CPacket;
import me.tolek.util.ScreenUtil;
import me.tolek.util.ToastUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

import static me.tolek.event.PartyListener.InviteClientEvent;
import static me.tolek.event.PartyListener.PartyChangedEvent;

@Environment(EnvType.CLIENT)
public class PartyHandler {

    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void invitePlayer(String player) {
        if (checkDisconnected()) return;

        WebsocketHandler.getInstance().sendBuffer(new PartyPlayerInviteC2SPacket(player, client.getSession().getUsername()).serialize());
    }

    public static void acceptInvite() {
        if (checkDisconnected()) return;

        WebsocketHandler.getInstance().sendBuffer(new PartyClientAcceptC2SPacket(client.getSession().getUsername()).serialize());
    }

    public static void send(String message) {
        if (checkDisconnected()) return;

        WebsocketHandler.getInstance().sendBuffer(new PartyClientChatC2SPacket(message, client.getSession().getUsername()).serialize());
    }

    public static void removeMember(String member) {
        if (checkDisconnected()) return;

        WebsocketHandler.getInstance().sendBuffer(new PartyPlayerKickC2SPacket(member, client.getSession().getUsername()).serialize());
    }

    public static void leaveParty() {
        if (checkDisconnected()) return;

        WebsocketHandler.getInstance().sendBuffer(new PartyClientLeaveC2SPacket(client.getSession().getUsername()).serialize());

        Party.setInParty(false);
        PartyListener.ClientLeftEvent event = new PartyListener.ClientLeftEvent();
        EventManager.getInstance().fire(event);
    }

    public static void declineInvite() {
        if (checkDisconnected()) return;

        WebsocketHandler.getInstance().sendBuffer(new PartyClientDeclineC2SPacket(client.getSession().getUsername()).serialize());
    }

    public static void promotePlayer(String player) {
        if (checkDisconnected()) return;

        WebsocketHandler.getInstance().sendBuffer(new PartyPlayerPromoteC2SPacket(player, client.getSession().getUsername()).serialize());
    }

    public static void demotePlayer(String player) {
        if (checkDisconnected()) return;

        WebsocketHandler.getInstance().sendBuffer(new PartyPlayerDemoteC2SPacket(player, client.getSession().getUsername()).serialize());
    }

    public static void handleError(PartyErrorS2CPacket packet) {
        String titleKey = "mflp.error.unexpected.title";
        String descriptionKey = "mflp.error.unexpected.description";
        var code = packet.code;

        switch (code) {
            case NO_PENDING_INVITE:
                titleKey = "mflp.error.noPendingInvite.title";
                descriptionKey = "mflp.error.noPendingInvite.description";
                break;
            case NO_PERMISSION:
                titleKey = "mflp.error.noPermission.title";
                descriptionKey = "mflp.error.noPermission.description";
                break;
            case INVALID_PLAYER:
                titleKey = "mflp.error.invalidPlayer.title";
                descriptionKey = "mflp.error.invalidPlayer.description";

                handleInvalidPlayer(packet.player);

                break;
            case PLAYER_NOT_IN_PARTY:
                titleKey = "mflp.error.playerNotInParty.title";
                descriptionKey = "mflp.error.playerNotInParty.description";
                break;
            case ALREADY_IN_PARTY:
                titleKey = "mflp.error.playerInParty.title";
                descriptionKey = "mflp.error.playerInParty.description";
                break;
            case SELF_INVITE:
                titleKey = "mflp.error.selfInvite.title";
                descriptionKey = "mflp.error.selfInvite.description";
                break;
            case NOT_IN_PARTY:
                titleKey = "mflp.error.notInParty.title";
                descriptionKey = "mflp.error.notInParty.description";
                break;
            case UNSUPPORTED_OPERATION:
                titleKey = "mflp.error.unsupportedOperation.title";
                descriptionKey = "mflp.error.unsupportedOperation.description";
                break;
            case ALREADY_INVITED:
                titleKey = "mflp.error.alreadyInvited.title";
                descriptionKey = "mflp.error.alreadyInvited.description";
                break;
        }
        PartyListener.ErrorEvent event = new PartyListener.ErrorEvent(titleKey, descriptionKey);
        EventManager.getInstance().fire(event);
    }

    public static void responsePlayerAccepted(String username) {
        PartyListener.PlayerJoinedEvent event = new PartyListener.PlayerJoinedEvent(username);
        EventManager.getInstance().fire(event);
    }

    public static void responsePlayerChat(String username, String message) {
        PartyListener.MessageReceivedEvent event = new PartyListener.MessageReceivedEvent(message, username);
        EventManager.getInstance().fire(event);
    }

    public static void responsePlayerDeclined(String username) {
        PartyListener.PlayerDeclinedEvent event = new PartyListener.PlayerDeclinedEvent(username);
        EventManager.getInstance().fire(event);
    }

    public static void responsePlayerLeft(String username) {
        PartyListener.PlayerLeaveEvent event = new PartyListener.PlayerLeaveEvent(username);
        EventManager.getInstance().fire(event);
    }

    public static void responsePlayerDemoted(String demoted) {
        PartyListener.PlayerDemotedEvent event = new PartyListener.PlayerDemotedEvent(demoted);
        EventManager.getInstance().fire(event);
    }

    public static void responsePlayerInvited(String invited) {
        PartyListener.PlayerInviteEvent event = new PartyListener.PlayerInviteEvent(invited);
        EventManager.getInstance().fire(event);
    }

    public static void responseClientInvited(String executor) {
        InviteClientEvent event = new InviteClientEvent(executor);
        EventManager.getInstance().fire(event);
    }

    public static void responseClientKicked() {
        Party.setInParty(false);

        PartyListener.ClientRemovedEvent event = new PartyListener.ClientRemovedEvent();
        EventManager.getInstance().fire(event);
    }

    public static void responsePlayerKicked(String kicked) {
        PartyListener.PlayerRemovedEvent event = new PartyListener.PlayerRemovedEvent(kicked);
        EventManager.getInstance().fire(event);
    }

    public static void responsePlayerPromoted(String promoted, String executor) {
        PartyListener.PlayerPromotedEvent event = new PartyListener.PlayerPromotedEvent(promoted);
        EventManager.getInstance().fire(event);
    }

    private static void handleInvalidPlayer(String playerName) {
        if (client.getNetworkHandler() == null)
            return;

        List<String> connectedPlayers = client.getNetworkHandler().getPlayerList().stream().map(x -> x.getProfile().getName()).toList();

        if (connectedPlayers.contains(playerName)) {
            // Tell the user that they can tell them that they tried to invite a non MFLP user
            ScreenUtil.openScreen(new NonMflpUserScreen(playerName));
        }
    }

    public static void partyChanged(PartyChangeS2CPacket packet) {
        String owner = packet.owner;
        ArrayList<String> mods = packet.mods;
        ArrayList<String> players = packet.players;

        if (players.contains(client.getSession().getUsername()) || mods.contains(client.getSession().getUsername()) ||
                owner.equalsIgnoreCase(client.getSession().getUsername())) {
            Party.setInParty(true);
        }

        PartyChangedEvent event = new PartyChangedEvent(owner, mods, players);
        EventManager.getInstance().fire(event);
    }

    public static boolean checkDisconnected() {
        if (WebsocketHandler.getInstance().isDisconnected()) {
            switch (MflpSettingsList.getInstance().SERVER_DISCONNECTION_ACTION.stateIndex) {
                case 0: // Chat
                    if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().world != null) {
                        MinecraftClient.getInstance().player.sendMessage(Text.translatable("mflp.error.notConnected.title")
                                .formatted(Formatting.ITALIC, Formatting.BOLD, Formatting.RED), false);
                    } else {
                        ToastUtil.showToast(Text.translatable("mflp.error.notConnected.title"), Text.translatable("mflp.error.notConnected.description"));
                    }

                    break;
                case 1: // Toast
                    ToastUtil.showToast(Text.translatable("mflp.error.notConnected.title"), Text.translatable("mflp.error.notConnected.description"));
                    break;
                case 2: // Screen
                    ScreenUtil.openScreen(new FailedToConnectToMflpNetworkScreen());
                    break;
                default:
                    throw new IndexOutOfBoundsException(MflpSettingsList.getInstance().SERVER_DISCONNECTION_ACTION.stateIndex);
            }

            Party.setInParty(false);
            return true;
        }

        return false;
    }
}

