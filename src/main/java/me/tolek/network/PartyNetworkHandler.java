package me.tolek.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.event.MinecraftStartListener;
import me.tolek.event.PartyListener;
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
        body.addProperty("cmd", "INVITE");
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
        body.addProperty("cmd", "ACCEPT");
        body.addProperty("player", client.getSession().getUsername());

        message.addProperty("key", serverHandler.clientKey);
        message.addProperty("id", client.getSession().getUsername());
        message.addProperty("cmd", "PARTY");
        message.add("body", body);

        serverHandler.sendMessage(message.toString());
    }

    public static void send(String message) {
        throw new NotImplementedException();
    }

    public static void removeMember(String member) {
        throw new NotImplementedException();
    }

    public static void leaveParty() {
        JsonObject message = new JsonObject();

        JsonObject body = new JsonObject();
        body.addProperty("cmd", "LEAVE");
        body.addProperty("player", client.getSession().getUsername());

        message.addProperty("key", serverHandler.clientKey);
        message.addProperty("id", client.getSession().getUsername());
        message.addProperty("cmd", "PARTY");
        message.add("body", body);

        serverHandler.sendMessage(message.toString());
    }

    public static void declineInvite() {
        throw new NotImplementedException();
    }

    public static void promotePlayer(String player) {
        throw new NotImplementedException();
    }

    public static void demotePlayer(String player) {
        throw new NotImplementedException();
    }

    @Override
    public void onEnable() {
        serverHandler.addMessageHandler(message -> {
            try {
                JsonObject json = JsonParser.parseString(message).getAsJsonObject();

                String id = json.get("id").getAsString();
                String cmd = json.get("cmd").getAsString();
                String body = json.get("body").getAsString();

                if (cmd.equals("CLIENT_INVITED") && body.equals("CLIENT_INVITED")) {
                    InviteClientEvent event = new InviteClientEvent(id);
                    EventManager.getInstance().fire(event);
                } else if (cmd.equals("PARTY_CHANGE")) {
                    JsonObject bodyObject = JsonParser.parseString(body).getAsJsonObject();

                    ArrayList<String> mods = new ArrayList<>();
                    ArrayList<String> players = new ArrayList<>();

                    bodyObject.get("moderators").getAsJsonArray().forEach(element -> mods.add(element.getAsString()));
                    bodyObject.get("players").getAsJsonArray().forEach(element -> mods.add(element.getAsString()));

                    PartyChangedEvent event = new PartyChangedEvent(bodyObject.get("owner").toString(), mods, players);
                    EventManager.getInstance().fire(event);
                }
            } catch (Exception ignored) { }
        });
    }
}
