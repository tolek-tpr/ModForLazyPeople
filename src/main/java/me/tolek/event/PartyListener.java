package me.tolek.event;

import java.util.ArrayList;

public interface PartyListener extends Listener {

    void onMessage(String message, String author);
    void onPlayerInvited(String playerUsername);
    void onClientInvited(String partyOwnerUsername);
    void onClientRemoved();
    void onClientLeft();
    void onPlayerLeft(String playerUsername);
    void onPlayerRemoved(String playerUsername);
    void onPlayerJoined(String playerUsername);
    void onPartyInviteFailed(String playerUsername);
    void onPartyChanged(String owner, ArrayList<String> moderators, ArrayList<String> members);
    void onError(String errorTitleTranslationKey, String errorDescriptionTranslationKey);
    void onPlayerDemoted(String player);
    void onPlayerPromoted(String player);
    void onPlayerDeclinedInvite(String player);

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

    public static class ClientLeftEvent extends Event<PartyListener> {

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for(PartyListener listener : listeners)
                listener.onClientLeft();
        }

        @Override
        public Class<PartyListener> getListenerType() {
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

    public static class ErrorEvent extends Event<PartyListener> {
        private final String errorTitleTranslationKey;
        private final String errorDescriptionTranslationKey;

        public ErrorEvent(String errorTitleTranslationKey, String errorDescriptionTranslationKey) {
            this.errorTitleTranslationKey = errorTitleTranslationKey;
            this.errorDescriptionTranslationKey = errorDescriptionTranslationKey;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for (PartyListener listener : listeners)
                listener.onError(this.errorTitleTranslationKey, this.errorDescriptionTranslationKey);
        }

        @Override
        public Class<PartyListener> getListenerType() {
            return PartyListener.class;
        }
    }

    public static class PlayerDemotedEvent extends Event<PartyListener> {
        private final String player;

        public PlayerDemotedEvent(String player) {
            this.player = player;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for (PartyListener listener : listeners)
                listener.onPlayerDemoted(this.player);
        }

        @Override
        public Class<PartyListener> getListenerType() {
            return PartyListener.class;
        }
    }

    public static class PlayerPromotedEvent extends Event<PartyListener> {
        private final String player;

        public PlayerPromotedEvent(String player) {
            this.player = player;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for (PartyListener listener : listeners)
                listener.onPlayerPromoted(this.player);
        }

        @Override
        public Class<PartyListener> getListenerType() {
            return PartyListener.class;
        }
    }

    public static class PlayerDeclinedEvent extends Event<PartyListener> {
        private final String player;

        public PlayerDeclinedEvent(String player) {
            this.player = player;
        }

        @Override
        public void fire(ArrayList<PartyListener> listeners) {
            for (PartyListener listener : listeners)
                listener.onPlayerDeclinedInvite(this.player);
        }

        @Override
        public Class<PartyListener> getListenerType() {
            return PartyListener.class;
        }
    }

}
