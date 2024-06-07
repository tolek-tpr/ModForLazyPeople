package me.tolek;

import me.shedaniel.cloth.clothconfig.shadowed.org.yaml.snakeyaml.Yaml;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MflpConfigScreen {
    private static MflpConfigScreen instance;
    private MflpConfigScreen() {}

    public static MflpConfigScreen getInstance() {
        if (MflpConfigScreen.instance == null) MflpConfigScreen.instance = new MflpConfigScreen();
        return MflpConfigScreen.instance;
    }
    private ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen((Screen) null)
            .setTitle(Text.translatable("mflp.configScreen.title"));

    private ConfigCategory configCategory = builder.getOrCreateCategory(Text.of("Config"));
    private ConfigCategory hotkeysCategory = builder.getOrCreateCategory(Text.of("Hotkeys"));
    private ConfigEntryBuilder entryBuilder = builder.entryBuilder();

    private Screen configScreen = null;

    private boolean useBetterFreeze = false;
    private InputUtil.Key betterFreezeKeycode = InputUtil.UNKNOWN_KEY;
    private InputUtil.Key betterFreezeStepKey = InputUtil.UNKNOWN_KEY;
    private InputUtil.Key worldSetupKeycode = InputUtil.UNKNOWN_KEY;

    public Screen buildConfigScreen() {
        if (configScreen == null) {
            saveConfig(builder);
            loadConfig();

            configCategory.addEntry(entryBuilder.startBooleanToggle(Text.translatable("mflp.categoryEntry.tickFreezeToggle"), useBetterFreeze)
                    .setDefaultValue(false)
                    .setTooltip(Text.translatable("mflp.tooltip.tickFreezeToggle"))
                    .setSaveConsumer(frozen2 -> {
                        useBetterFreeze = frozen2;
                    })
                    .build());
            hotkeysCategory.addEntry(entryBuilder.startKeyCodeField(Text.translatable("mflp.categoryEntry.tickFreezeKeybind"), betterFreezeKeycode)
                    .setDefaultValue(InputUtil.UNKNOWN_KEY)
                    .setTooltip(Text.translatable("mflp.tooltip.tickFreezeKey"))
                    .setKeySaveConsumer(frozenKeyCode2 -> {
                        betterFreezeKeycode = frozenKeyCode2;
                        HotkeyExecutor.updateHotkeys();
                    })
                    .build());
            hotkeysCategory.addEntry(entryBuilder.startKeyCodeField(Text.translatable("mflp.categoryEntry.tickStepKeybind"), betterFreezeStepKey)
                    .setDefaultValue(InputUtil.UNKNOWN_KEY)
                    .setTooltip(Text.translatable("mflp.tooltip.tickStepKey"))
                    .setKeySaveConsumer(stepKeyCode -> {
                        betterFreezeStepKey = stepKeyCode;
                        HotkeyExecutor.updateHotkeys();
                    })
                    .build());
            hotkeysCategory.addEntry(entryBuilder.startKeyCodeField(Text.translatable("mflp.categoryEntry.setupWorldKeybind"), worldSetupKeycode)
                    .setDefaultValue(InputUtil.UNKNOWN_KEY)
                    .setTooltip(Text.translatable("mflp.tooltip.worldSetupKey"))
                    .setKeySaveConsumer(worldSetupKey -> {
                        worldSetupKeycode = worldSetupKey;
                        HotkeyExecutor.updateHotkeys();
                    })
                    .build());
            configScreen = builder.build();
        }
        return configScreen;
    }

    public boolean getToggleBetterFreeze() { return useBetterFreeze; }
    public InputUtil.Key getWorldSetupKey() { return worldSetupKeycode; }
    public InputUtil.Key getFreezeKey() { return betterFreezeKeycode; }
    public InputUtil.Key getTickStepKey() { return betterFreezeStepKey; }

    public void saveConfig(ConfigBuilder builder) {
        builder.setSavingRunnable(() -> {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("useBetterFreeze", useBetterFreeze);
            dataMap.put("betterFreezeKeycode", betterFreezeKeycode.getCode());
            dataMap.put("betterFreezeStepKey", betterFreezeStepKey.getCode());
            dataMap.put("worldSetupKey", worldSetupKeycode.getCode());
            try {
                PrintWriter writer = new PrintWriter(new File("MflpConfig.yml"));
                Yaml yaml = new Yaml();
                yaml.dump(dataMap, writer);
            } catch (FileNotFoundException e) {
                System.out.println("File not found, not writing");
            }
        });
    }

    public void loadConfig() {
        try {
            InputStream inputStream = new FileInputStream(new File("MflpConfig.yml"));
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            useBetterFreeze = Boolean.parseBoolean(String.valueOf(data.get("useBetterFreeze")));
            betterFreezeKeycode = InputUtil.fromKeyCode(Integer.parseInt(String.valueOf(data.get("betterFreezeKeycode"))), 1);
            betterFreezeStepKey = InputUtil.fromKeyCode(Integer.parseInt(String.valueOf(data.get("betterFreezeStepKey"))), 1);
            worldSetupKeycode = InputUtil.fromKeyCode(Integer.parseInt(String.valueOf(data.get("worldSetupKey"))), 1);
            HotkeyExecutor.updateHotkeys();
        } catch (FileNotFoundException e) {
            System.out.println("File not found, not reading");
        }
    }

}
