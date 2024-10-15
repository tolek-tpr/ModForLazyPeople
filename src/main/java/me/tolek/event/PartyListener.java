package me.tolek.event;

import java.util.ArrayList;

public interface PartyListener extends Listener {

    void onMessage(String message);
    void playerAdded(String playerUsername);
    void playerInvited(String playerUsername);
    void onInvite(byte partyId);

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
                listener.playerAdded(this.username);
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
                listener.playerInvited(this.username);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }

    public static class InviteClientEvent extends Event<PartyListener> {

        private final byte partyID;

        public InviteClientEvent(byte partyID) {
            this.partyID = partyID;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onInvite(this.partyID);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }


}
