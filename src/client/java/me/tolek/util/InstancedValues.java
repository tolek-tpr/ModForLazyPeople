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
    public boolean updateAvailable = false;
    public String version = "v2.4.1-beta";
    public long timeSinceLastInputInMils = 0;
    public String githubUrl = "github.com/tolek-tpr/ModForLazyPeople/releases";
    public boolean shownUpdateScreen = false;

}
