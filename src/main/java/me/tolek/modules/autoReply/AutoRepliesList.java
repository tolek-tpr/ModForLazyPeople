package me.tolek.modules.autoReply;

import me.tolek.modules.Macro.MacroList;

import java.util.ArrayList;

public class AutoRepliesList {

    private AutoRepliesList() {
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("Keyword");
        ArrayList<String> replies = new ArrayList<>();
        replies.add("Reply");
        addAutoReply(new AutoReply("Test", keywords, replies));
    }

    public static final String repliesTooltip = "Here, you add replies. To execute a command add a / before the command, to only " +
            "say the message type it without the slash.";
    public static final String toReplyTooltip = "Here, you add messages, that the auto reply bot should reply to.";

    private static AutoRepliesList instance;
    private ArrayList<AutoReply> autoReplies = new ArrayList<>();

    public static AutoRepliesList getInstance() {
        if (AutoRepliesList.instance == null) AutoRepliesList.instance = new AutoRepliesList();
        return AutoRepliesList.instance;
    }

    public ArrayList<AutoReply> getAutoReplies() { return this.autoReplies; }
    public void addAutoReply(AutoReply autoReply) { this.autoReplies.add(autoReply); }
    public void setAutoReplies(ArrayList<AutoReply> autoReplyList) { this.autoReplies = autoReplyList; }
    public void remove(AutoReply remove) { this.autoReplies.remove(remove); }

}
