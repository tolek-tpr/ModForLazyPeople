package me.tolek.modules.party;

import java.util.ArrayList;

public class Party {

    private static String owner;
    private static ArrayList<String> moderators = new ArrayList<>();
    private static ArrayList<String> members = new ArrayList<>();

    public static String getOwner() {
        return owner;
    }

    public static void setOwner(String owner) {
        Party.owner = owner;
    }

    public static ArrayList<String> getModerators() {
        return moderators;
    }

    public static void setModerators(ArrayList<String> moderators) {
        Party.moderators = moderators;
    }

    public static ArrayList<String> getMembers() {
        return members;
    }

    public static void setMembers(ArrayList<String> members) {
        Party.members = members;
    }
}
