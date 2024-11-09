package me.tolek.modules.settings;

import me.tolek.util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomPlayerMessageList {

    private static CustomPlayerMessageList instance;
    private ArrayList<Tuple<String, String>> messagesPerPlayer = new ArrayList<>();

    private CustomPlayerMessageList() {}

    public static CustomPlayerMessageList getInstance() {
        if (instance == null) instance = new CustomPlayerMessageList();
        return instance;
    }

    public ArrayList<Tuple<String, String>> getMessages() {
        return messagesPerPlayer;
    }

    public void addMessage(Tuple<String, String> tuple) {
        messagesPerPlayer.add(tuple);
    }

    public String getMessageForName(String name) {
        for (Tuple t : messagesPerPlayer) {
            if (t.value1.equals(name)) return (String) t.value2;
        }
        return null;
    }

    public void setMessages(ArrayList<Tuple<String, String>> list) { messagesPerPlayer = list; }

}
