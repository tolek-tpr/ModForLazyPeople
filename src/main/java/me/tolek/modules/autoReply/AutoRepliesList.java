package me.tolek.modules.autoReply;

import me.tolek.modules.Macro.MacroList;

import java.util.ArrayList;

public class AutoRepliesList {

    private AutoRepliesList() {

    }

    public static final String repliesTooltip = "mflp.autoReplyConfigScreen.replies.tooltip";
    public static final String toReplyTooltip = "mflp.autoReplyConfigScreen.keywords.tooltip";

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
