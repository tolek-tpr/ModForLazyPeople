package me.tolek.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.event.MinecraftStartListener;
import me.tolek.event.PartyListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.lang3.NotImplementedException;

import static me.tolek.event.PartyListener.InviteClientEvent;

@Environment(EnvType.CLIENT)
public class PartyNetworkHandler extends EventImpl {

    public static void invitePlayer(String player) {
        throw new NotImplementedException();
    }

    public static void acceptInvite() {
        throw new NotImplementedException();
    }

    public static void send(String message) {
        throw new NotImplementedException();
    }

    public static void removeMember(String member) {
        throw new NotImplementedException();
    }

    public static void leaveParty() {
        throw new NotImplementedException();
    }

    public static void getPartyInfo() {
        throw new NotImplementedException();
    }

    public static void declineInvite() {
        throw new NotImplementedException();
    }

    @Override
    public void onEnable() {
        WebSocketServerHandler serverHandler = WebSocketServerHandler.getInstance();

        serverHandler.addMessageHandler(message -> {
            try {
                JsonObject json = JsonParser.parseString(message).getAsJsonObject();

                String id = json.get("id").getAsString();
                String cmd = json.get("cmd").getAsString();
                String body = json.get("body").getAsString();

                if (cmd.equals("CLIENT_INVITED") && body.equals("CLIENT_INVITED")) {
                    InviteClientEvent event = new InviteClientEvent(id);
                }
            } catch (NullPointerException ignored) { }
        });
    }
}
