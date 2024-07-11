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
    public String version = "v2.6.0";
    public long timeSinceLastInputInMils = 0;
    public long timeSinceLastWbInMils = 0;
    public String githubUrl = "github.com/tolek-tpr/ModForLazyPeople/releases";
    public String modrinthUrl = "https://modrinth.com/mod/modforlazypeople/versions";
    public boolean shownUpdateScreen = false;
    public boolean pauseWelcomeBack = false;
    public boolean isAfk = false;

}
