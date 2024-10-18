package me.tolek.event;

import java.util.ArrayList;

public interface PartyListener extends Listener {

    void onMessage(String message, String author, String err);
    void onPlayerInvited(String playerUsername, String err);
    void onClientInvited(String partyOwnerUsername);
    void onClientRemoved();
    void onPlayerLeft(String playerUsername, String err);
    void onPlayerRemoved(String playerUsername, String err);
    void onPlayerJoined(String playerUsername, String err);
    void onPartyInviteFailed(String playerUsername);
    void onPartyChanged(String owner, ArrayList<String> moderators, ArrayList<String> members);

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

        private final String partyOwnerUsername;

        public InviteClientEvent(String partyOwnerUsername) {
            this.partyOwnerUsername = partyOwnerUsername;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onClientInvited(this.partyOwnerUsername);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }

    public static class ClientRemovedEvent extends Event<PartyListener> {

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onClientRemoved();
        }

        @Override
        public Class<PartyListener> getListenerType() {
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

    public static class PlayerInviteFailedEvent extends Event<PartyListener> {

        private final String username;

        public PlayerInviteFailedEvent(String username) {
            this.username = username;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onPartyInviteFailed(this.username);
        }

        @Override
        public Class<PartyListener> getListenerType()
        {
            return PartyListener.class;
        }

    }

    public static class PartyChangedEvent extends Event<PartyListener> {

        private final String owner;
        private final ArrayList<String> moderators;
        private final ArrayList<String> members;

        public PartyChangedEvent(String owner, ArrayList<String> moderators, ArrayList<String> members) {
            this.owner = owner;
            this.moderators = moderators;
            this.members = members;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onPartyChanged(this.owner, this.moderators, this.members);
        }

        @Override
        public Class<PartyListener> getListenerType() {
            return PartyListener.class;
        }
    }

}
