package me.tolek.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.tolek.event.Event;
import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.event.PartyListener;
import me.tolek.modules.party.Party;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;

import static me.tolek.event.PartyListener.InviteClientEvent;
import static me.tolek.event.PartyListener.PartyChangedEvent;

@Environment(EnvType.CLIENT)
public class PartyNetworkHandler extends EventImpl {

    private static final WebSocketServerHandler serverHandler = WebSocketServerHandler.getInstance();
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void invitePlayer(String player) {
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
        JsonObject message = new JsonObject();

        JsonObject body = new JsonObject();
        body.addProperty("body_cmd", "LEAVE");
        body.addProperty("player", client.getSession().getUsername());

        message.addProperty("key", serverHandler.clientKey);
        message.addProperty("id", client.getSession().getUsername());
        message.addProperty("cmd", "PARTY");
        message.add("body", body);

        serverHandler.sendMessage(message.toString());
    }

    public static void declineInvite() {
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
                    // NO_PENDING_INVITE
                    // NO_PERMISSION
                    // INVALID_PLAYER
                    // PLAYER_OFFLINE
                    // PLAYER_IN_PARTY
                    // SELF_INVITE
                    // NOT_IN_PARTY
                    // INVALID_PARTY
                    // UNSUPPORTED_OPERATION
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
                    String body = json.get("body").getAsString();

                    PartyListener.PlayerRemovedEvent event = new PartyListener.PlayerRemovedEvent(body);
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

                    PartyListener.PlayerPromotedEvent event = new PartyListener.PlayerPromotedEvent(json.getAsJsonObject("body")
                            .get("demoted").getAsString());
                    EventManager.getInstance().fire(event);
                }
                if (cmd.equals("CHAT")) {
                    PartyListener.MessageReceivedEvent event = new PartyListener.MessageReceivedEvent(json.getAsJsonObject("body")
                            .get("message").getAsString(), json.getAsJsonObject("body").get("author").getAsString());
                    EventManager.getInstance().fire(event);
                }
            } catch (Exception ignored) { }
        });
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
            System.out.println("true");
        }

        PartyChangedEvent event = new PartyChangedEvent(bodyObject.get("owner").getAsString(), mods, players);
        EventManager.getInstance().fire(event);
    }

}
