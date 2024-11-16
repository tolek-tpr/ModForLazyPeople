package me.tolek.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import me.tolek.ModForLazyPeople;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.modules.settings.base.MflpSetting;
import me.tolek.util.LoggingUtils;
import me.tolek.util.MflpUtil;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MflpConfigParser {

    private final Gson gson;

    public MflpConfigParser() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();
    }

    private <T> ConfigFieldModifier<T> getModifierForObject(ArrayList<ConfigFieldModifier<Object>> modifiers, T lookupObject) {
        for (ConfigFieldModifier<Object> modifier : modifiers) {
            if (lookupObject.getClass() == modifier.getType()) return (ConfigFieldModifier<T>) modifier;
        }
        return null;
    }

    public void save() {}

    public void loadAll(@Nullable ArrayList<ConfigFieldModifier<Object>> mainConfigModifiers,
                        @Nullable ArrayList<ConfigFieldModifier<Object>> macroConfigModifiers,
                        @Nullable ArrayList<ConfigFieldModifier<Object>> settingsConfigModifiers,
                        @Nullable ArrayList<ConfigFieldModifier<Object>> autoRepliesConfigModifiers,
                        @Nullable ArrayList<ConfigFieldModifier<Object>> hotkeyConfigModifiers,
                        @Nullable ArrayList<ConfigFieldModifier<Object>> colorsConfigModifiers) {
        Logger logger = LoggingUtils.getConfigLogger();

        logger.info("Starting MFLP Config import.");
        this.load(MflpConfigFiles.getMainConfigFormatted(), logger, mainConfigModifiers);
        this.load(MflpConfigFiles.getMacroConfigFormatted(), logger, macroConfigModifiers);
        this.load(MflpConfigFiles.getSettingsConfigFormatted(), logger, settingsConfigModifiers);
        this.load(MflpConfigFiles.getAutoRepliesConfigFormatted(), logger, autoRepliesConfigModifiers);
        this.load(MflpConfigFiles.getHotkeysConfigFormatted(), logger, hotkeyConfigModifiers);
        this.load(MflpConfigFiles.getColorsConfigFormatted(), logger, colorsConfigModifiers);
    }

    @SafeVarargs
    public final void load(String file, Logger logger, @Nullable ConfigFieldModifier<Object>... modifiers) {
        this.load(file, logger, MflpUtil.asArray(modifiers));
    }

    public void load(String file, Logger logger, ArrayList<ConfigFieldModifier<Object>> modifiers) {
        if (file.equals(MflpConfigFiles.getSettingsConfigFormatted())) {
            try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
                loadSettings(gson.fromJson(reader, ModData.SettingsData.class), modifiers);
            } catch (IOException e) {
                logger.warn("MFLP Config: Settings file missing!");
                return;
            } catch (JsonIOException e) {
                logger.error(e.getLocalizedMessage());
                return;
            }
        }
    }

    public void loadSettings(ModData.SettingsData data, ArrayList<ConfigFieldModifier<Object>> modifiers) {
        MflpSettingsList settingsList = MflpSettingsList.getInstance();

        for (int i = 0; i < settingsList.getSettings().size(); i++) {
            MflpSetting setting = settingsList.getSettings().get(i);
            MflpSetting parsedSetting = this.getModifierForObject(modifiers, setting).accept(data.settings().getSettings().get(i));
            settingsList.getSettings().set(i, parsedSetting == null ? setting : parsedSetting);
        }
    }

}
