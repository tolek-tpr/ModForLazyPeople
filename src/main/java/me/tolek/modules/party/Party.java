package me.tolek.modules.party;

import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;

public class Party {

    private static String owner;
    private static ArrayList<String> moderators = new ArrayList<>();
    private static ArrayList<String> members = new ArrayList<>();
    private static boolean isInParty = false;

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

    public static boolean isOwner() {
        return MinecraftClient.getInstance().getSession().getUsername().equals(owner);
    }

    public static boolean isModeratorOrOwner() {
        return moderators.contains(MinecraftClient.getInstance().getSession().getUsername()) || isOwner();
    }

    public static boolean isInParty() {
        return isInParty;
    }

    public static void setInParty(boolean isInParty) {
        Party.isInParty = isInParty;
    }
}
