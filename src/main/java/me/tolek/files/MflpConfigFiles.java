package me.tolek.files;

public final class MflpConfigFiles {

    public static final String MAIN_CONFIG = "MflpConfig.json";
    public static final String MACRO_CONFIG = "MflpMacros.json";
    public static final String SETTINGS_CONFIG = "MflpSettings.json";
    public static final String AUTO_REPLIES_CONFIG = "MflpAutoReplies.json";
    public static final String HOTKEYS_CONFIG = "MflpHotkeys.json";
    public static final String COLORS_CONFIG = "MflpColors.json";

    public static String getMflpFolder() {
        return "config/mflp";
    }

    public static String getMainConfigFormatted() { return MflpConfigFiles.getMflpFolder() + MflpConfigFiles.MAIN_CONFIG; }
    public static String getMacroConfigFormatted() { return MflpConfigFiles.getMflpFolder() + MflpConfigFiles.MACRO_CONFIG; }
    public static String getSettingsConfigFormatted() { return MflpConfigFiles.getMflpFolder() + MflpConfigFiles.SETTINGS_CONFIG; }
    public static String getAutoRepliesConfigFormatted() { return MflpConfigFiles.getMflpFolder() + MflpConfigFiles.AUTO_REPLIES_CONFIG; }
    public static String getHotkeysConfigFormatted() { return MflpConfigFiles.getMflpFolder() + MflpConfigFiles.HOTKEYS_CONFIG; }
    public static String getColorsConfigFormatted() { return MflpConfigFiles.getMflpFolder() + MflpConfigFiles.COLORS_CONFIG; }

}
