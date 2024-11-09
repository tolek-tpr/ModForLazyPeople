package me.tolek.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.event.PartyListener;
import me.tolek.event.PartyListener.InviteClientEvent;
import me.tolek.event.PartyListener.PartyChangedEvent;
import me.tolek.gui.screens.FailedToConnectToMflpNetworkScreen;
import me.tolek.gui.screens.NonMflpUserScreen;
import me.tolek.modules.party.Party;
import me.tolek.modules.settings.MflpSettingsList;
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
public class PartyNetworkHandler extends EventImpl {

    private static final WebSocketServerHandler serverHandler = WebSocketServerHandler.getInstance();
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void invitePlayer(String player) {
        if (checkDisconnected()) return;

        JsonObject message = new JsonObject();

        JsonObject body = new JsonObject();
        body.addProperty("body_cmd", "INVITE");
        body.addProperty("player", player);

        message.addProperty("key", serverHandler.clientKey);
        message.addProperty("id", client.getSession().getUsername());
        message.addProperty("cmd", "PARTY");
        message.add("body", body);

        serverHandler.sendMessage(message.toString());
    }

    public static void acceptInvite() {
        if (checkDisconnected()) return;

        JsonObject message = new JsonObject();

        JsonObject body = new JsonObject();
        body.addProperty("body_cmd", "ACCEPT");
        body.addProperty("player", client.getSession().getUsername());

        message.addProperty("key", serverHandler.clientKey);
        message.addProperty("id", client.getSession().getUsername());
        message.addProperty("cmd", "PARTY");
        message.add("body", body);

        serverHandler.sendMessage(message.toString());
    }

    public static void send(String message) {
        if (checkDisconnected()) return;

        JsonObject messageS = new JsonObject();

        JsonObject body = new JsonObject();
        body.addProperty("body_cmd", "CHAT");
        body.addProperty("player", client.getSession().getUsername());
        body.addProperty("message", message);

        messageS.addProperty("key", serverHandler.clientKey);
        messageS.addProperty("id", client.getSession().getUsername());
        messageS.addProperty("cmd", "PARTY");
        messageS.add("body", body);

        serverHandler.sendMessage(messageS.toString());
    }

    public static void removeMember(String member) {
        if (checkDisconnected()) return;

        JsonObject message = new JsonObject();

        JsonObject body = new JsonObject();
        body.addProperty("body_cmd", "KICK");
        body.addProperty("player", member);

        message.addProperty("key", serverHandler.clientKey);
        message.addProperty("id", client.getSession().getUsername());
        message.addProperty("cmd", "PARTY");
        message.add("body", body);

        serverHandler.sendMessage(message.toString());
    }

    public static void leaveParty() {
        if (checkDisconnected()) return;
        
        JsonObject message = new JsonObject();

        JsonObject body = new JsonObject();
        body.addProperty("body_cmd", "LEAVE");
        body.addProperty("player", client.getSession().getUsername());

        message.addProperty("key", serverHandler.clientKey);
        message.addProperty("id", client.getSession().getUsername());
        message.addProperty("cmd", "PARTY");
        message.add("body", body);

        serverHandler.sendMessage(message.toString());
        Party.setInParty(false);
        PartyListener.ClientLeftEvent event = new PartyListener.ClientLeftEvent();
        EventManager.getInstance().fire(event);
    }

    public static void declineInvite() {
        if (checkDisconnected()) return;
        
        JsonObject message = new JsonObject();

        JsonObject body = new JsonObject();
        body.addProperty("body_cmd", "DECLINE");
        body.addProperty("player", client.getSession().getUsername());

        message.addProperty("key", serverHandler.clientKey);
        message.addProperty("id", client.getSession().getUsername());
        message.addProperty("cmd", "PARTY");
        message.add("body", body);

        serverHandler.sendMessage(message.toString());
    }

    public static void promotePlayer(String player) {
        if (checkDisconnected()) return;
        
        JsonObject message = new JsonObject();

        JsonObject body = new JsonObject();
        body.addProperty("body_cmd", "PROMOTE");
        body.addProperty("player", player);

        message.addProperty("key", serverHandler.clientKey);
        message.addProperty("id", client.getSession().getUsername());
        message.addProperty("cmd", "PARTY");
        message.add("body", body);

        serverHandler.sendMessage(message.toString());
    }

    public static void demotePlayer(String player) {
        if (checkDisconnected()) return;
        
        JsonObject message = new JsonObject();

        JsonObject body = new JsonObject();
        body.addProperty("body_cmd", "DEMOTE");
        body.addProperty("player", player);

        message.addProperty("key", serverHandler.clientKey);
        message.addProperty("id", client.getSession().getUsername());
        message.addProperty("cmd", "PARTY");
        message.add("body", body);

        serverHandler.sendMessage(message.toString());
    }

    @Override
    public void onEnable() {
        serverHandler.addMessageHandler(message -> {
            try {
                if (serverHandler.endpoint == null) return;

                JsonObject json = JsonParser.parseString(message).getAsJsonObject();

                String id = json.get("id").getAsString();
                String cmd = json.get("cmd").getAsString();

                // Errors
                if (cmd.equals("ERROR")) {
                    String titleKey = "mflp.error.unexpected.title";
                    String descriptionKey = "mflp.error.unexpected.description";
                    switch (json.get("body").getAsString()) {
                        case "NO_PENDING_INVITE":
                            titleKey = "mflp.error.noPendingInvite.title";
                            descriptionKey = "mflp.error.noPendingInvite.description";
                            break;
                        case "NO_PERMISSION":
                            titleKey = "mflp.error.noPermission.title";
                            descriptionKey = "mflp.error.noPermission.description";
                            break;
                        case "INVALID_PLAYER":
                            titleKey = "mflp.error.invalidPlayer.title";
                            descriptionKey = "mflp.error.invalidPlayer.description";

                            handleInvalidPlayer(json.get("player").getAsString());

                            break;
                        case "PLAYER_OFFLINE":
                            titleKey = "mflp.error.playerOffline.title";
                            descriptionKey = "mflp.error.playerOffline.description";
                            break;
                        case "PLAYER_IN_PARTY":
                            titleKey = "mflp.error.playerInParty.title";
                            descriptionKey = "mflp.error.playerInParty.description";
                            break;
                        case "SELF_INVITE":
                            titleKey = "mflp.error.selfInvite.title";
                            descriptionKey = "mflp.error.selfInvite.description";
                            break;
                        case "NOT_IN_PARTY":
                            titleKey = "mflp.error.notInParty.title";
                            descriptionKey = "mflp.error.notInParty.description";
                            break;
                        case "INVALID_PARTY":
                            titleKey = "mflp.error.invalidParty.title";
                            descriptionKey = "mflp.error.invalidParty.description";
                            break;
                        case "UNSUPPORTED_OPERATION":
                            titleKey = "mflp.error.unsupportedOperation.title";
                            descriptionKey = "mflp.error.unsupportedOperation.description";
                            break;
                    }
                    PartyListener.ErrorEvent event = new PartyListener.ErrorEvent(titleKey, descriptionKey);
                    EventManager.getInstance().fire(event);
                }

                if (cmd.equals("CLIENT_INVITED")) {
                    InviteClientEvent event = new InviteClientEvent(id);
                    EventManager.getInstance().fire(event);
                }
                if (cmd.equals("PARTY_CHANGE")) {
                    partyChanged(json);
                }
                if (cmd.equals("INVITE_DECLINED")) {
                    String body = json.get("body").getAsString();

                    PartyListener.PlayerDeclinedEvent event = new PartyListener.PlayerDeclinedEvent(body);
                    EventManager.getInstance().fire(event);
                }
                if (cmd.equals("PLAYER_KICKED")) {
                    partyChanged(json);

                    PartyListener.PlayerRemovedEvent event = new PartyListener.PlayerRemovedEvent(json.getAsJsonObject("body")
                            .get("kicked").getAsString());
                    EventManager.getInstance().fire(event);
                }
                if (cmd.equals("CLIENT_KICKED")) {
                    Party.setInParty(false);

                    PartyListener.ClientRemovedEvent event = new PartyListener.ClientRemovedEvent();
                    EventManager.getInstance().fire(event);
                }
                if (cmd.equals("PLAYER_PROMOTED")) {
                    partyChanged(json);

                    PartyListener.PlayerPromotedEvent event = new PartyListener.PlayerPromotedEvent(json.getAsJsonObject("body")
                            .get("promoted").getAsString());
                    EventManager.getInstance().fire(event);
                }
                if (cmd.equals("PLAYER_DEMOTED")) {
                    partyChanged(json);

                    PartyListener.PlayerDemotedEvent event = new PartyListener.PlayerDemotedEvent(json.getAsJsonObject("body")
                            .get("demoted").getAsString());
                    EventManager.getInstance().fire(event);
                }
                if (cmd.equals("CHAT")) {
                    PartyListener.MessageReceivedEvent event = new PartyListener.MessageReceivedEvent(json.getAsJsonObject("body")
                            .get("message").getAsString(), json.getAsJsonObject("body").get("author").getAsString());
                    EventManager.getInstance().fire(event);
                }
                if (cmd.equals("INVITE_SUCCESS")) {
                    PartyListener.PlayerInviteEvent event = new PartyListener.PlayerInviteEvent(json.get("body").getAsString());
                    EventManager.getInstance().fire(event);
                }
                if (cmd.equals("PLAYER_LEAVE")) {
                    partyChanged(json);

                    PartyListener.PlayerLeaveEvent event = new PartyListener.PlayerLeaveEvent(json.getAsJsonObject("body")
                            .get("left").getAsString());
                    EventManager.getInstance().fire(event);
                }
                if (cmd.equals("PLAYER_JOIN")) {
                    partyChanged(json);

                    PartyListener.PlayerJoinedEvent event = new PartyListener.PlayerJoinedEvent(json.getAsJsonObject("body")
                            .get("joined").getAsString());
                    EventManager.getInstance().fire(event);
                }
                if (cmd.equals("INVITE_TIMEOUT")) {
                    PartyListener.InviteTimedOutEvent event = new PartyListener.InviteTimedOutEvent(json.get("body").getAsString());
                    EventManager.getInstance().fire(event);
                }
            } catch (Exception ignored) { }
        });
    }

    private void handleInvalidPlayer(String playerName) {
        if (client.getNetworkHandler() == null)
            return;

        List<String> connectedPlayers = client.getNetworkHandler().getPlayerList().stream().map(x -> x.getProfile().getName()).toList();

        if (connectedPlayers.contains(playerName)) {
            // Tell the user that they can tell them that they tried to invite a non MFLP user
            ScreenUtil.openScreen(new NonMflpUserScreen(playerName));
        }
    }

    private void partyChanged(JsonObject json) {
        JsonObject bodyObject = json.getAsJsonObject("body");

        ArrayList<String> mods = new ArrayList<>();
        ArrayList<String> players = new ArrayList<>();

        bodyObject.get("moderators").getAsJsonArray().forEach(element -> mods.add(element.getAsString()));
        bodyObject.get("players").getAsJsonArray().forEach(element -> players.add(element.getAsString()));

        if (players.contains(client.getSession().getUsername()) || mods.contains(client.getSession().getUsername()) ||
                bodyObject.get("owner").getAsString().equals(client.getSession().getUsername())) {
            Party.setInParty(true);
        }

        PartyChangedEvent event = new PartyChangedEvent(bodyObject.get("owner").getAsString(), mods, players);
        EventManager.getInstance().fire(event);
    }

    public static boolean checkDisconnected() {
        if (serverHandler.isDisconnected()) {
            switch (MflpSettingsList.getInstance().SERVER_DISCONNECTION_ACTION.stateIndex) {
                case 0: // Chat
                    if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().world != null) {
                        MinecraftClient.getInstance().player.sendMessage(Text.translatable("mflp.error.notConnected.title")
                                .formatted(Formatting.ITALIC, Formatting.BOLD, Formatting.RED));
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
