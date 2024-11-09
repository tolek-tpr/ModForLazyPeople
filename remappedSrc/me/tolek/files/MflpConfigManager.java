package me.tolek.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import me.tolek.ModForLazyPeople;
import me.tolek.modules.macro.Macro;
import me.tolek.modules.autoReply.AutoRepliesList;
import me.tolek.modules.autoReply.AutoReply;
import me.tolek.modules.settings.CustomMessagePerServerList;
import me.tolek.modules.settings.CustomPlayerMessageList;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.util.InstancedValues;
import me.tolek.util.Tuple;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MflpConfigManager {

    private static final String CONFIG_FILE = "MflpConfig.json";
    private static final String FILE_VERSION = InstancedValues.getInstance().getFileVersion();
    private final Gson gson;

    public MflpConfigManager() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.setPrettyPrinting().create();
    }

    public void save(ArrayList<Macro> macros, boolean shownWelcomeScreen, MflpSettingsList settings, AutoRepliesList arl) {
        ModData modData = new ModData(macros, shownWelcomeScreen, settings, arl, FILE_VERSION,
                CustomPlayerMessageList.getInstance().getMessages(), CustomMessagePerServerList.getInstance().getMessages());
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
            ModForLazyPeople.LOGGER.warn("MFLP save file not found!", e);
            return null;
        } catch (JsonIOException e) {
            ModForLazyPeople.LOGGER.warn("Json Error while loading MFLP save file", e);
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
        private String fileVersion;
        private ArrayList<ShortMacro> macros = new ArrayList<>();
        private MflpSettingsList settings;
        private boolean shownWelcomeScreen;
        private ArrayList<AutoReply> autoReplies = new ArrayList<>();
        private ArrayList<Tuple<String, String>> customPlayerMessages = new ArrayList<>();
        private ArrayList<Tuple<String, Tuple<String, String>>> customServerMessages = new ArrayList<>();

        public ModData(ArrayList<Macro> macros, boolean shownWelcomeScreen, MflpSettingsList settings,
                       AutoRepliesList arl, String fileVersion, ArrayList<Tuple<String, String>> customPlayerMessages,
                       ArrayList<Tuple<String, Tuple<String, String>>> customServerMessages) {
            for (Macro m : macros) {
                this.macros.add(new ShortMacro(m.getName(), m.getCommands(), m.getKey(), m.getRepeatAmount(), m.getUneditable(), m.getTurnedOn()));
            }
            this.settings = settings;
            this.shownWelcomeScreen = shownWelcomeScreen;
            this.autoReplies = arl.getAutoReplies();
            this.fileVersion = fileVersion;
            this.customPlayerMessages = customPlayerMessages;
            this.customServerMessages = customServerMessages;
        }

        public ArrayList<ShortMacro> getShortMacros() {
            return this.macros;
        }
        public String getFileVersion() { return this.fileVersion; }
        public MflpSettingsList getSettings() { return this.settings; }
        public ArrayList<AutoReply> getAutoReplies() { return this.autoReplies; }
        public boolean isShownWelcomeScreen() {
            return shownWelcomeScreen;
        }
        public ArrayList<Tuple<String, String>> getCustomPlayerMessages() { return this.customPlayerMessages; }
        public ArrayList<Tuple<String, Tuple<String, String>>> getCustomServerMessages() { return this.customServerMessages; }
    }


}
