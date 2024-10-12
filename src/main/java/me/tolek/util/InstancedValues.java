package me.tolek.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class InstancedValues {

    private static InstancedValues instance;

    public static InstancedValues getInstance() {
        if (instance == null) instance = new InstancedValues();
        return instance;
    }

    private InstancedValues() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();
    }

    private final Gson gson;

    public boolean shownWelcomeScreen = false;
    public boolean hasLoaded = false;
    public boolean updateAvailable = false;

    public String getMflpVersion() {
        String settings = getMflpSettings().strip();
        if (settings == null) return null;

        String[] lines = settings.split("\n");
        return lines[1].split(":")[1].replaceAll("\"", "").replaceAll(",", "").strip();
    }

    public String getFileVersion() {
        String settings = getMflpSettings().strip();
        if (settings == null) return null;

        String[] lines = settings.split("\n");
        return lines[2].split(":")[1].replaceAll("\"", "").replaceAll(",", "").strip();
    }

    public String getMflpSettings() {
        Optional<Path> o = FabricLoader.getInstance().getModContainer("modforlazypeople").get()
                .findPath("assets/modforlazypeople/mflp/settings.mflp");
        if (o.isPresent()) {
            try {
                String settings = Files.readString(o.get());
                return settings;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public long timeSinceLastInputMillis = 0;
    public long timeSinceLastWbMillis = 0;
    public String githubUrl = "github.com/tolek-tpr/ModForLazyPeople/releases";
    public String modrinthUrl = "https://modrinth.com/mod/modforlazypeople/versions";
    public boolean shownUpdateScreen = false;
    public boolean pauseWelcomeBack = false;
    public boolean isAfk = false;
    public boolean ignoreWbMessages = true;

}
