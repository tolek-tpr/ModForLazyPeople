package me.tolek.files;

import me.tolek.ModForLazyPeople;
import me.tolek.event.*;
import me.tolek.files.deprecated.MflpConfigManager;
import me.tolek.modules.autoReply.AutoReply;
import me.tolek.modules.macro.MacroList;
import me.tolek.modules.autoReply.AutoRepliesList;
import me.tolek.modules.settings.AutoWelcomeBack;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.updateChecker.UpdateChecker;
import me.tolek.util.InstancedValues;
import me.tolek.util.MflpUtil;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class MflpConfigImpl extends EventImpl implements MinecraftQuitListener, MinecraftStartListener {

    private InstancedValues iv;
    private MflpUtil util;

    @Override
    public void onEnable() {
        EventManager.getInstance().add(MinecraftQuitListener.class, this);
        EventManager.getInstance().add(MinecraftStartListener.class, this);
        this.iv = InstancedValues.getInstance();
        this.util = new MflpUtil();
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(MinecraftQuitListener.class, this);
        EventManager.getInstance().remove(MinecraftStartListener.class, this);
    }

    @Override
    public void onQuit() {
        MflpConfigManager configManager = new MflpConfigManager();

        MacroList macroList = MacroList.getInstance();
        MflpSettingsList settings = MflpSettingsList.getInstance();
        AutoRepliesList arl = AutoRepliesList.getInstance();

        if (!util.didSave) {
           //configManager.save(macroList.getMacros(), MflpInfo.getInstance().shownWelcomeScreen, settings, arl);
            new MflpConfigLoader().saveAll();
            util.didSave = true;
        }
    }

    @Override
    public void onStartFinished() {}

    @Override
    public void onStart() {
        MflpSettingsList settings = MflpSettingsList.getInstance();

        if (!iv.hasLoaded) {
            HashMap<MflpConfigLoader.FieldModifierType, ArrayList<MflpConfigFieldModifier<? extends ISerializable>>> modifiers = new HashMap<>();

            // region FieldModifierExamples
            modifiers.put(MflpConfigLoader.FieldModifierType.SETTINGS, MflpUtil.asArray(new MflpConfigFieldModifier<AutoWelcomeBack>() {
                @Override
                public AutoWelcomeBack accept(ISerializable modifiable) {
                    return (AutoWelcomeBack) modifiable;
                }

                @Override
                public Class<AutoWelcomeBack> getFieldType() {
                    return AutoWelcomeBack.class;
                }
            }));
            modifiers.put(MflpConfigLoader.FieldModifierType.AUTO_REPLIES, MflpUtil.asArray(new MflpConfigFieldModifier<AutoReply>() {
                @Override
                public AutoReply accept(ISerializable modifiable) {
                    return (AutoReply) modifiable;
                }

                @Override
                public Class<AutoReply> getFieldType() {
                    return AutoReply.class;
                }
            }));
            modifiers.put(MflpConfigLoader.FieldModifierType.MACROS, MflpUtil.asArray(new MflpConfigFieldModifier<ModData.ShortMacro>(){
                @Override
                public ModData.ShortMacro accept(ISerializable modifiable) {
                    return (ModData.ShortMacro) modifiable;
                }

                @Override
                public Class<ModData.ShortMacro> getFieldType() {
                    return ModData.ShortMacro.class;
                }
            }));
            // endregion

            new MflpConfigLoader().loadAll(modifiers);
            settings.FREE_CAM_ENABLED.setState(false);
            settings.POST_PROCESSOR.setState(0);

            iv.hasLoaded = true;
        }

        UpdateChecker uc = new UpdateChecker("tolek-tpr", "ModForLazyPeople", iv.getMflpVersion());
        uc.check();
        Logger logger = ModForLazyPeople.LOGGER;
        if (uc.isUpdateAvailable()) {
            logger.warn("New version available: v{} (current: v{})", uc.getLatestVersion(), uc.currentVersion);
            logger.warn("Download it at {}", iv.modrinthUrl);
        }
        if (uc.isUpdateAvailable()) {
            iv.updateAvailable = true;
        }
    }

}
