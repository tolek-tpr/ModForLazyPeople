package me.tolek.event;

import java.util.ArrayList;

public interface PartyListener extends Listener {

    void onMessage(String message, String author);
    void onPlayerInvited(String playerUsername);
    void onClientInvited(byte partyId, String partyOwnerUsername);
    void onPlayerLeft(String playerUsername);
    void onPlayerRemoved(String playerUsername);
    void onPlayerJoined(String playerUsername);
    void onPartyInfoReturned(); // TODO: Proper arguments + events

    public static class MessageReceivedEvent extends Event<PartyListener> {

        private final String message;
        private final String author;

        public MessageReceivedEvent(String message, String author) {
            this.message = message;
            this.author = author;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onMessage(this.message, this.author);
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
        private final String partyOwnerUsername;

        public InviteClientEvent(byte partyID, String partyOwnerUsername) {
            this.partyID = partyID;
            this.partyOwnerUsername = partyOwnerUsername;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onClientInvited(this.partyID, partyOwnerUsername);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }

    public static class PlayerLeaveEvent extends Event<PartyListener> {

        private final String username;

        public PlayerLeaveEvent(String username) {
            this.username = username;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onPlayerLeft(this.username);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }

    public static class PlayerRemovedEvent extends Event<PartyListener> {

        private final String username;

        public PlayerRemovedEvent(String username) {
            this.username = username;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onPlayerRemoved(this.username);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }

    public static class PlayerJoinedEvent extends Event<PartyListener> {

        private final String username;

        public PlayerJoinedEvent(String username) {
            this.username = username;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onPlayerJoined(this.username);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }

}
