package me.tolek.event;

public interface PartyListener extends Listener {

    void onMessage(String message);
    void playerAdded(String playerUsername);
    void playerInvited(String playerUsername);
    void onInvite(byte partyId);

}
