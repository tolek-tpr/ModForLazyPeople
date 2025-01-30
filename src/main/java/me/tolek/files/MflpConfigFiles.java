package me.tolek.files;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.nio.file.Path;

public class MflpConfigFiles {

    public static final String MAIN_CONFIG = "MflpConfig.json";
    public static final String MACRO_CONFIG = "MflpMacros.json";
    public static final String SETTINGS_CONFIG = "MflpSettings.json";
    public static final String AUTO_REPLIES_CONFIG = "MflpAutoReplies.json";

    public static String getMflpFolder() {
        return FabricLoader.getInstance().getConfigDir().resolve(Path.of("mflp/")).toString() + "/";
    }

    public static String getMainConfigFormatted() { return MflpConfigFiles.getMflpFolder() + MflpConfigFiles.MAIN_CONFIG; }
    public static String getMacroConfigFormatted() { return MflpConfigFiles.getMflpFolder() + MflpConfigFiles.MACRO_CONFIG; }
    public static String getSettingsConfigFormatted() { return MflpConfigFiles.getMflpFolder() + MflpConfigFiles.SETTINGS_CONFIG; }
    public static String getAutoRepliesConfigFormatted() { return MflpConfigFiles.getMflpFolder() + MflpConfigFiles.AUTO_REPLIES_CONFIG; }

}
