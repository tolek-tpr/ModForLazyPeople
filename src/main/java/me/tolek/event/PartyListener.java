package me.tolek.event;

import java.util.ArrayList;

public interface PartyListener extends Listener {

    void onMessage(String message);
    void onPlayerAdded(String playerUsername);
    void onPlayerInvited(String playerUsername);
    void onClientInvited(byte partyId, String partyName);
    void onPlayerLeft(String playerUsername);
    void onPlayerRemoved(String playerUsername);
    void onPartyRenamed(String newName);
    void onPlayerJoined(String playerUsername);

    public static class MessageReceivedEvent extends Event<PartyListener> {

        private final String message;

        public MessageReceivedEvent(String message) {
            this.message = message;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onMessage(this.message);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }

    public static class PlayerAddedEvent extends Event<PartyListener> {

        private final String username;

        public PlayerAddedEvent(String username) {
            this.username = username;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onPlayerAdded(this.username);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }

    public static class PlayerInviteEvent extends Event<PartyListener> {

        private final String username;

        public PlayerInviteEvent(String username) {
            this.username = username;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onPlayerInvited(this.username);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }

    public static class InviteClientEvent extends Event<PartyListener> {

        private final byte partyID;
        private final String partyName;

        public InviteClientEvent(byte partyID, String partyName) {
            this.partyID = partyID;
            this.partyName = partyName;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onClientInvited(this.partyID, partyName);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }


}
