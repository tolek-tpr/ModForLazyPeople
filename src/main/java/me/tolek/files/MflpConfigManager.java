package me.tolek.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import me.tolek.modules.Macro.Macro;
import me.tolek.modules.autoReply.AutoRepliesList;
import me.tolek.modules.autoReply.AutoReply;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.util.InstancedValues;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
//import java.util.function.Supplier;

public class MflpConfigManager {

    private static final String CONFIG_FILE = "MflpConfig.json";
    private Gson gson;

    public MflpConfigManager() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.setPrettyPrinting().create();
    }

    public void save(ArrayList<Macro> macros, boolean shownWelcomeScreen, MflpSettingsList settings, AutoRepliesList arl) {
        ModData modData = new ModData(macros, shownWelcomeScreen, settings, arl, InstancedValues.getInstance().fileVersion);
        try (FileWriter writer = new FileWriter(CONFIG_FILE, StandardCharsets.UTF_8)) {
            gson.toJson(modData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ModData load() {
        try (FileReader reader = new FileReader(CONFIG_FILE, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, ModData.class);
        } catch (IOException e) {
            System.out.println("File not found!");
            return null;
        } catch (JsonIOException e) {
            System.out.println("Json Error");
            return null;
        }
    }

    public class ModData {
        private ArrayList<Macro> macros = new ArrayList<>();
        private MflpSettingsList settings;
        private boolean shownWelcomeScreen;
        private ArrayList<AutoReply> autoReplies;
        private String fileVersion;

        public ModData(ArrayList<Macro> macros, boolean shownWelcomeScreen, MflpSettingsList settings, AutoRepliesList arl, String fileVersion) {
            this.macros.addAll(macros);
            this.settings = settings;
            this.shownWelcomeScreen = shownWelcomeScreen;
            this.autoReplies = arl.getAutoReplies();
            this.fileVersion = fileVersion;
        }

        public ArrayList<Macro> getMacros() {
            return this.macros;
        }
        public MflpSettingsList getSettings() { return this.settings; }
        public ArrayList<AutoReply> getAutoReplies() { return this.autoReplies; }
        public boolean isShownWelcomeScreen() {
            return shownWelcomeScreen;
        }
        public String getFileVersion() { return this.fileVersion; }
    }


}
