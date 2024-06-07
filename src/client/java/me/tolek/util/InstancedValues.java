package me.tolek.util;

public class InstancedValues {

    private static InstancedValues instance;

    public static InstancedValues getInstance() {
        if (instance == null) instance = new InstancedValues();
        return instance;
    }

    private InstancedValues() {}

    public boolean shownWelcomeScreen = false;
    public boolean hasLoaded = false;

}
