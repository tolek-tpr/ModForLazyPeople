package me.tolek.event;

import java.util.ArrayList;
import java.util.Optional;

public interface PartyListener extends Listener {

    void onMessage(String message, String author, String err);
    void onPlayerInvited(String playerUsername, String err);
    void onClientInvited(byte partyId, String partyOwnerUsername, String err);
    void onPlayerLeft(String playerUsername, String err);
    void onPlayerRemoved(String playerUsername, String err);
    void onPlayerJoined(String playerUsername, String err);
    void onPartyInfoReturned(String owner, ArrayList<String> members, String err); // TODO: Proper arguments + events

    public static class MessageReceivedEvent extends Event<PartyListener> {

        private final String message;
        private final String author;
        private final String err;

        public MessageReceivedEvent(String message, String author, String err) {
            this.message = message;
            this.author = author;
            this.err = err;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onMessage(this.message, this.author, this.err);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }

    public static class PlayerInviteEvent extends Event<PartyListener> {

        private final String username;
        private final String err;

        public PlayerInviteEvent(String username, String err) {
            this.username = username;
            this.err = err;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onPlayerInvited(this.username, this.err);
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
        private final String err;

        public InviteClientEvent(byte partyID, String partyOwnerUsername, String err) {
            this.partyID = partyID;
            this.partyOwnerUsername = partyOwnerUsername;
            this.err = err;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onClientInvited(this.partyID, this.partyOwnerUsername, this.err);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }

    public static class PlayerLeaveEvent extends Event<PartyListener> {

        private final String username;
        private final String err;

        public PlayerLeaveEvent(String username, String err) {
            this.username = username;
            this.err = err;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onPlayerLeft(this.username, this.err);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }

    public static class PlayerRemovedEvent extends Event<PartyListener> {

        private final String username;
        private final String err;

        public PlayerRemovedEvent(String username, String err) {
            this.username = username;
            this.err = err;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onPlayerRemoved(this.username, this.err);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }

    public static class PlayerJoinedEvent extends Event<PartyListener> {

        private final String username;
        private final String err;

        public PlayerJoinedEvent(String username, String err) {
            this.username = username;
            this.err = err;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onPlayerJoined(this.username, this.err);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }

    public static class PartyInfoReturnedEvent extends Event<PartyListener> {

        private final String owner;
        private final ArrayList<String> members;
        private final String err;

        public PartyInfoReturnedEvent(String owner, ArrayList<String> members, String err) {
            this.owner = owner;
            this.members = members;
            this.err = err;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onPartyInfoReturned(this.owner, this.members, this.err);
        }

        @Override
        public Class<PartyListener> getListenerType() {
            return PartyListener.class;
        }
    }

}
