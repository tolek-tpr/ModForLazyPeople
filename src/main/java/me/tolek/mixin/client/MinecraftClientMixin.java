package me.tolek.mixin.client;

import me.tolek.modules.Macro.Macro;
import me.tolek.modules.Macro.MacroList;
import me.tolek.files.MflpConfigManager;
import me.tolek.modules.autoReply.AutoRepliesList;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.updateChecker.UpdateChecker;
import me.tolek.util.InstancedValues;
import me.tolek.util.MflpUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    private MflpUtil util = new MflpUtil();

    @Inject(at = @At("HEAD"), method = "scheduleStop")
    private void scheduleStop(CallbackInfo ci) {
        MflpConfigManager configManager = new MflpConfigManager();
        InstancedValues iv = InstancedValues.getInstance();
        MacroList macroList = MacroList.getInstance();
        MflpSettingsList settings = MflpSettingsList.getInstance();
        AutoRepliesList arl = AutoRepliesList.getInstance();

        if (!util.didSave) {
            configManager.save(macroList.getMacros(), iv.shownWelcomeScreen, settings, arl);
            util.didSave = true;
        }
    }

    @Inject(at = @At("HEAD"), method = "run")
    private void run(CallbackInfo ci) {
        MflpConfigManager configManager = new MflpConfigManager();
        InstancedValues iv = InstancedValues.getInstance();
        MacroList macroList = MacroList.getInstance();
        MflpSettingsList settings = MflpSettingsList.getInstance();
        AutoRepliesList arl = AutoRepliesList.getInstance();

        if (!iv.hasLoaded) {
            MflpConfigManager.ModData loadedData = configManager.load();

            if (loadedData != null) {
                ArrayList<MflpConfigManager.ShortMacro> shortMacros = loadedData.getShortMacros();
                boolean loadedShownWelcomeScreen = loadedData.isShownWelcomeScreen();

                iv.shownWelcomeScreen = loadedShownWelcomeScreen;
                if (!loadedData.getShortMacros().isEmpty() && loadedData.getShortMacros() != null) {
                    macroList.getMacros().clear();
                }

                if (loadedData.getSettings() != null) {
                    settings.getSettings().clear();
                }

                if (loadedData.getAutoReplies() != null) {
                    arl.getAutoReplies().clear();
                }

                for (MflpConfigManager.ShortMacro sm : shortMacros) {
                    KeyBinding kb = new KeyBinding("mflp.keybinding.undefined",
                            sm.key,
                            "mflp.keybindCategory.MFLP");

                    Macro m = new Macro(kb, sm.commands, sm.name, sm.repeatAmt, sm.isUneditable, sm.isOn);
                    m.setKey(sm.key);
                    macroList.addMacro(m);
                }
                if (loadedData.getSettings() != null) {
                    settings.AUTO_WELCOME_BACK = loadedData.getSettings().AUTO_WELCOME_BACK;
                    settings.AUTO_WELCOME = loadedData.getSettings().AUTO_WELCOME;
                    settings.WB_MESSAGE = loadedData.getSettings().WB_MESSAGE;
                    settings.WELCOME_MESSAGE = loadedData.getSettings().WELCOME_MESSAGE;
                    settings.WB_RANK_WHITELIST = loadedData.getSettings().WB_RANK_WHITELIST;
                    settings.WB_COOLDOWN = loadedData.getSettings().WB_COOLDOWN;
                    //settings.AUTO_PLOT_HOME = loadedData.getSettings().AUTO_PLOT_HOME;
                    settings.AUTO_WB_BLACKLIST = loadedData.getSettings().AUTO_WB_BLACKLIST;
                    settings.AUTO_WB_WHITELIST = loadedData.getSettings().AUTO_WB_WHITELIST;
                    settings.WB_BLACKLIST = loadedData.getSettings().WB_BLACKLIST;
                    settings.WB_WHITELIST = loadedData.getSettings().WB_WHITELIST;
                }
                if (loadedData.getAutoReplies() != null) {
                    arl.setAutoReplies(loadedData.getAutoReplies());
                }
                //settings.settings = loadedData.getSettings().settings;

            }
            iv.hasLoaded = true;
        }

        UpdateChecker uc = new UpdateChecker("tolek-tpr", "ModForLazyPeople", iv.version);
        uc.check();
        if (uc.isUpdateAvailable()) {
            iv.updateAvailable = true;
        }
        //iv.updateAvailable = false;
    }

}
