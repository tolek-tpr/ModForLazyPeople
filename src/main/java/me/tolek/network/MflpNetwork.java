package me.tolek.network;

import me.tolek.event.Event;
import me.tolek.event.EventManager;
import me.tolek.event.PartyListener;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.lang3.NotImplementedException;

public class MflpNetwork {
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

}
