package me.tolek.modules.autoReply;

import java.util.ArrayList;

public class AutoReply {

    private ArrayList<String> keywords = new ArrayList<>();
    private ArrayList<String> replies = new ArrayList<>();
    private String name;
    private boolean isTurnedOn = true;

    public AutoReply(String name, ArrayList<String> keywords, ArrayList<String> replies) {
        this.keywords = keywords;
        this.replies = replies;
        this.name = name;
    }

    public void editToReply(int index, String newKeyword) {
        if (index != -1)
            keywords.set(index, newKeyword);
    }

    public void editReplies(int index, String newKeyword) {
        if (index != -1)
            replies.set(index, newKeyword);
    }

    public AutoReply copy() {
        return new AutoReply(this.getName(), this.getKeywords(), this.getReplies());
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public void addKeyword(String string) { this.keywords.add(string); }

    public void removeKeyword(String string) { this.keywords.remove(string); }

    public ArrayList<String> getReplies() { return replies; }

    public void setReplies(ArrayList<String> replies) {
        this.replies = replies;
    }

    public void addReply(String string) { this.replies.add(string); }

    public void removeReply(String string) { this.replies.remove(string); }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    public void setTurnedOn(boolean turnedOn) {
        isTurnedOn = turnedOn;
    }

}
