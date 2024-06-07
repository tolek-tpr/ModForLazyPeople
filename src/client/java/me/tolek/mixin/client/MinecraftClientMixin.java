package me.tolek.mixin.client;

import me.tolek.Macro.Macro;
import me.tolek.Macro.MacroList;
import me.tolek.files.MflpConfigManager;
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
        if (!util.didSave) {
            configManager.save(macroList.getMacros(), iv.shownWelcomeScreen);
            System.out.println("saving, " + macroList.getMacros() + " SPACE " + iv.shownWelcomeScreen);
            util.didSave = true;
        }
    }

    @Inject(at = @At("HEAD"), method = "run")
    private void run(CallbackInfo ci) {
        MflpConfigManager configManager = new MflpConfigManager();
        InstancedValues iv = InstancedValues.getInstance();
        MacroList macroList = MacroList.getInstance();

        if (!iv.hasLoaded) {
            MflpConfigManager.ModData loadedData = configManager.load();

            if (loadedData != null) {
                ArrayList<MflpConfigManager.ShortMacro> shortMacros = loadedData.getShortMacros();
                boolean loadedShownWelcomeScreen = loadedData.isShownWelcomeScreen();

                iv.shownWelcomeScreen = loadedShownWelcomeScreen;
                macroList.getMacros().clear();

                for (MflpConfigManager.ShortMacro sm : shortMacros) {
                    KeyBinding kb = new KeyBinding("mflp.keybinding.undefined",
                            sm.key,
                            "mflp.keybindCategory.MFLP");

                    Macro m = new Macro(kb, sm.commands, sm.name, sm.repeatAmt, sm.isUneditable, sm.isOn);
                    m.setKey(sm.key);
                    macroList.addMacro(m);
                }
            }
            iv.hasLoaded = true;
        }

        UpdateChecker uc = new UpdateChecker("tolek-tpr", "ModForLazyPeople", iv.version);
        uc.check();
        if (uc.isUpdateAvailable()) {
            iv.updateAvailable = true;
        }
    }

}
