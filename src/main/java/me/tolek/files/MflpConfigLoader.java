package me.tolek.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonReader;
import me.tolek.MflpInfo;
import me.tolek.modules.autoReply.AutoRepliesList;
import me.tolek.modules.autoReply.AutoReply;
import me.tolek.modules.macro.MacroList;
import me.tolek.modules.settings.*;
import me.tolek.modules.settings.base.MflpSetting;
import me.tolek.util.MflpUtil;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class MflpConfigLoader {

    private final Gson gson;

    public MflpConfigLoader() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        //builder.registerTypeAdapter(MflpSetting.class, new MflpSettingTypeAdapter());
        gson = builder.create();
    }

    public void saveAll() {
        ModData.SettingsData settingsData = new ModData.SettingsData(MflpSettingsList.getInstance());
        ModData.MainConfigData configData = new ModData.MainConfigData(MflpInfo.getInstance(), CustomPlayerMessageList.getInstance(), CustomMessagePerServerList.getInstance());
        ArrayList<ModData.ShortMacro> shortMacros = new ArrayList<>();
        MacroList.getInstance().getMacros().forEach(macro -> shortMacros.add(ModData.ShortMacro.fromMacro(macro)));
        ModData.MacroData macroData = new ModData.MacroData(shortMacros);
        ModData.AutoReplyData autoReplyData = new ModData.AutoReplyData(AutoRepliesList.getInstance().getAutoReplies());
        Logger logger = MflpUtil.getConfigLogger();

        try {
            logger.info("Saving!");
            FileWriter configWriter = new FileWriter(MflpConfigFiles.getMainConfigFormatted(), StandardCharsets.UTF_8);
            gson.toJson(configData, configWriter);
            configWriter.close();

            FileWriter settingsWriter = new FileWriter(MflpConfigFiles.getSettingsConfigFormatted(), StandardCharsets.UTF_8);
            gson.toJson(settingsData, settingsWriter);
            settingsWriter.close();

            FileWriter macroWriter = new FileWriter(MflpConfigFiles.getMacroConfigFormatted(), StandardCharsets.UTF_8);
            gson.toJson(macroData, macroWriter);
            macroWriter.close();

            FileWriter autoReplyWriter = new FileWriter(MflpConfigFiles.getAutoRepliesConfigFormatted(), StandardCharsets.UTF_8);
            gson.toJson(autoReplyData, autoReplyWriter);
            autoReplyWriter.close();

            logger.info("Saved successfully!");
        } catch (IOException e) {
            logger.error("Failed to write MFLP config file! ", e);
        }
    }

    @Deprecated
    public void loadAll(@Nullable ArrayList<MflpConfigFieldModifier<? extends ISerializable>> macroConfigModifiers,
                        @Nullable ArrayList<MflpConfigFieldModifier<? extends ISerializable>> settingsConfigModifiers,
                        @Nullable ArrayList<MflpConfigFieldModifier<? extends ISerializable>> autoRepliesConfigModifiers) {
        Logger logger = MflpUtil.getConfigLogger();

        logger.info("Starting MFLP Config import.");
        this.load(MflpConfigFiles.getMainConfigFormatted(), logger, null);
        this.load(MflpConfigFiles.getMacroConfigFormatted(), logger, macroConfigModifiers);
        this.load(MflpConfigFiles.getSettingsConfigFormatted(), logger, settingsConfigModifiers);
        this.load(MflpConfigFiles.getAutoRepliesConfigFormatted(), logger, autoRepliesConfigModifiers);
    }

    public void loadAll(HashMap<FieldModifierType, ArrayList<MflpConfigFieldModifier<? extends ISerializable>>> allModifiers) {
        Logger logger = MflpUtil.getConfigLogger();

        logger.info("Starting MFLP Config import.");
        this.load(MflpConfigFiles.getMainConfigFormatted(), logger, null);
        this.load(MflpConfigFiles.getMacroConfigFormatted(), logger, allModifiers.get(FieldModifierType.MACROS));
        this.load(MflpConfigFiles.getSettingsConfigFormatted(), logger, allModifiers.get(FieldModifierType.SETTINGS));
        this.load(MflpConfigFiles.getAutoRepliesConfigFormatted(), logger, allModifiers.get(FieldModifierType.AUTO_REPLIES));
    }

    public void load(String file, Logger logger, ArrayList<MflpConfigFieldModifier<? extends ISerializable>> modifiers) {
        try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            if (file.equals(MflpConfigFiles.getSettingsConfigFormatted())) {
                loadSettings(gson.fromJson(reader, ModData.SettingsData.class), logger, modifiers);
            } else if (file.equals(MflpConfigFiles.getMainConfigFormatted())) {
                loadMainConfig(gson.fromJson(reader, ModData.MainConfigData.class), logger);
            } else if (file.equals(MflpConfigFiles.getMacroConfigFormatted())) {
                loadMacroConfig(gson.fromJson(reader, ModData.MacroData.class), logger, modifiers);
            } else if (file.equals(MflpConfigFiles.getAutoRepliesConfigFormatted())) {
                loadAutoReplies(gson.fromJson(reader, ModData.AutoReplyData.class), logger, modifiers);
            }
        } catch (IOException e) {
            logger.warn("Could not find file: " + file);
        } catch (JsonIOException e) {
            logger.warn("Json Exception! Thrown by file: " + file);
            logger.error(e.getLocalizedMessage());
        } catch (Exception e) {
            logger.error("A error occured while loading MFLP save files!", e);
        }
    }

    private void loadMainConfig(ModData.MainConfigData configData, Logger logger) {
        MflpInfo info = MflpInfo.getInstance();

        if (configData == null || configData.config() == null || configData.playerMessageList() == null || configData.playerMessageList().getMessages() == null ||
                configData.serverMessageList() == null || configData.serverMessageList().getMessages() == null) {
            logger.warn("Failed to fetch Main Config!");
        } else {
            info.loadedFileVersion = configData.config().loadedFileVersion;
            info.shownWelcomeScreen = configData.config().shownWelcomeScreen;
            CustomPlayerMessageList.getInstance().setMessages(configData.playerMessageList().getMessages());
            CustomMessagePerServerList.getInstance().setMessagesPerServer(configData.serverMessageList().getMessages());
        }

    }

    private void loadMacroConfig(ModData.MacroData macroData, Logger logger, ArrayList<MflpConfigFieldModifier<? extends ISerializable>> modifiers) {
        MacroList macroList = MacroList.getInstance();

        if (macroData == null || macroData.macros() == null) {
            logger.warn("Failed to fetch Macro List!");
            return;
        }
        macroList.getMacros().clear();
        for (ModData.ShortMacro shortMacro : macroData.macros()) {
            ArrayList<MflpConfigFieldModifier<? extends ISerializable>> specificMods = this.getModifiersForSerializable(modifiers, shortMacro);
            ModData.ShortMacro parsed = (ModData.ShortMacro) this.modifyObject(shortMacro, specificMods);
            macroList.addMacro(parsed == null ? ModData.ShortMacro.toMacro(shortMacro) : ModData.ShortMacro.toMacro(parsed));
        }
    }

    public void loadAutoReplies(ModData.AutoReplyData autoReplyData, Logger logger,
                                 ArrayList<MflpConfigFieldModifier<? extends ISerializable>> modifiers) {
        AutoRepliesList repliesList = AutoRepliesList.getInstance();

        if (autoReplyData == null || autoReplyData.autoReplies() == null) {
            logger.warn("Failed to fetch Auto Reply Data!");
            return;
        }
        for (AutoReply ar : autoReplyData.autoReplies()) {
            ArrayList<MflpConfigFieldModifier<? extends ISerializable>> specificMods = this.getModifiersForSerializable(modifiers, ar);
            AutoReply parsed = (AutoReply) this.modifyObject(ar, specificMods);
            repliesList.addAutoReply(parsed == null ? ar : parsed);
        }
    }

    public void loadSettings(ModData.SettingsData settingsData, Logger logger, ArrayList<MflpConfigFieldModifier<? extends ISerializable>> modifiers) {
        MflpSettingsList settingsList = MflpSettingsList.getInstance();

        if (settingsData.settingsList() == null || settingsData.settingsList().getSettings() == null) {
            logger.warn("Failed to fetch Settings List!");
            return;
        }

        var mfs = this.getModifiersForSettings(modifiers);
        var loadedSettings = settingsData.settingsList();

        // region Setting Loading
        settingsList.AUTO_WELCOME_BACK = (AutoWelcomeBack) this.modifyObject(loadedSettings.AUTO_WELCOME_BACK, mfs.get(AutoWelcomeBack.class));
        settingsList.AUTO_WELCOME = (AutoWelcome) this.modifyObject(loadedSettings.AUTO_WELCOME, mfs.get(AutoWelcome.class));
        // endregion
    }

    private ArrayList<MflpConfigFieldModifier<? extends ISerializable>> getModifiersForSerializable(
            ArrayList<MflpConfigFieldModifier<? extends ISerializable>> possibleModifiers, ISerializable object) {

        ArrayList<MflpConfigFieldModifier<? extends ISerializable>> ret = new ArrayList<>();
        if (possibleModifiers == null) return ret;
        possibleModifiers.forEach(modifier -> {
            if (modifier.getFieldType() == object.getClass()) ret.add(modifier);
        });
        return ret;
    }

    private HashMap<Class<MflpSetting>, ArrayList<MflpConfigFieldModifier<? extends ISerializable>>> getModifiersForSettings(
            ArrayList<MflpConfigFieldModifier<? extends ISerializable>> modifiers) {
        HashMap<Class<MflpSetting>, ArrayList<MflpConfigFieldModifier<? extends ISerializable>>> map = new HashMap<>();

        for (var modifier : modifiers) {
            map.getOrDefault(modifier.getFieldType(), new ArrayList<>()).add(modifier);
        }

        return map;
    }

    private ISerializable modifyObject(ISerializable serializable, ArrayList<MflpConfigFieldModifier<? extends ISerializable>> modifiers) {
        if (serializable == null) return null;
        AtomicReference<ISerializable> ret = new AtomicReference<>();
        ret.set(serializable);
        if (modifiers == null) return serializable;
        modifiers.forEach(mod -> ret.set(mod.accept(ret.get())));
        return ret.get();
    }

    public static enum FieldModifierType {
        MACROS,
        SETTINGS,
        AUTO_REPLIES,
    }

}
