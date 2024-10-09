package me.tolek.modules.settings;

import me.tolek.util.Tuple;

import java.util.ArrayList;

public class CustomMessagePerServerList {

    private static CustomMessagePerServerList instance;
    private ArrayList<Tuple<String, Tuple<String, String>>> messagesPerServer = new ArrayList<>();

    private CustomMessagePerServerList() {}

    public static CustomMessagePerServerList getInstance() {
        if (instance == null) instance = new CustomMessagePerServerList();
        return instance;
    }

    public ArrayList<Tuple<String, Tuple<String, String>>> getMessages() { return messagesPerServer; }
    public void addMessagesForServer(String server, Tuple<String, String> messages) { this.messagesPerServer.add(new Tuple<>(server, messages)); }
    public void setMessagesPerServer(ArrayList<Tuple<String, Tuple<String, String>>> messagesPerServer) { this.messagesPerServer = messagesPerServer; }

    @SuppressWarnings("unchecked")
    public Tuple<String, String> getMessagesForServer(String server) {
        for (Tuple t : messagesPerServer) {
            if (t.value1.equals(server) && t.value2 instanceof Tuple<?,?> v2) return (Tuple<String, String>) v2;
        }
        return null;
    }

}
