package me.tolek.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import me.tolek.modules.Macro.Macro;
import me.tolek.modules.autoReply.AutoRepliesList;
import me.tolek.modules.autoReply.AutoReply;
import me.tolek.modules.settings.MflpSettingsList;

import java.io.*;
import java.nio.charset.Charset;
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
        File configFileObject = new File(CONFIG_FILE);
        if (configFileObject.exists()) {
            configFileObject.delete();
        }

        ModData modData = new ModData(macros, shownWelcomeScreen, settings, arl);
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

    public class ShortMacro {

        public String name;
        public ArrayList<String> commands;
        public int key;
        public int repeatAmt = 1;
        public boolean isUneditable = false;
        public boolean isOn = true;

        public ShortMacro(String name, ArrayList<String> commands, int key, int repeatAmt, boolean isUneditable, boolean isOn) {
            this.name = name;
            this.commands = commands;
            this.key = key;
            this.repeatAmt = repeatAmt;
            this.isUneditable = isUneditable;
            this.isOn = isOn;
        }

    }

    public class ModData {
        private ArrayList<ShortMacro> macros = new ArrayList<>();
        private MflpSettingsList settings;
        private boolean shownWelcomeScreen;
        private ArrayList<AutoReply> autoReplies = new ArrayList<>();

        public ModData(ArrayList<Macro> macros, boolean shownWelcomeScreen, MflpSettingsList settings, AutoRepliesList arl) {
            for (Macro m : macros) {
                this.macros.add(new ShortMacro(m.getName(), m.getCommands(), m.getKey(), m.getRepeatAmount(), m.getUneditable(), m.getTurnedOn()));
            }
            this.settings = settings;
            this.shownWelcomeScreen = shownWelcomeScreen;
            this.autoReplies = arl.getAutoReplies();
        }

        public ArrayList<ShortMacro> getShortMacros() {
            return this.macros;
        }
        public MflpSettingsList getSettings() { return this.settings; }
        public ArrayList<AutoReply> getAutoReplies() { return this.autoReplies; }
        public boolean isShownWelcomeScreen() {
            return shownWelcomeScreen;
        }
    }


}
