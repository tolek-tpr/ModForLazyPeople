package me.tolek.network;

import me.tolek.event.Event;
import me.tolek.event.EventManager;
import me.tolek.event.PartyListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.lang3.NotImplementedException;

@Environment(EnvType.CLIENT)
public class PartyNetworkHandler {

    public static Event<PartyListener> PartyEvents;
    
    public static void invitePlayer(String player) {
        throw new NotImplementedException();
    }

    public static void acceptInvite() {
        throw new NotImplementedException();
    }

    public static void send(String message) {
        EventManager.getInstance().fire(new PartyListener.MessageReceivedEvent(message, MinecraftClient.getInstance().getSession().getUsername()));
        //throw new NotImplementedException();
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

}
