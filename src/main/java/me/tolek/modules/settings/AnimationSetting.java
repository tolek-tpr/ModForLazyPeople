package me.tolek.modules.settings;

import me.tolek.gui.screens.AnimationsScreen;
import me.tolek.modules.settings.base.ButtonSetting;
import net.minecraft.client.MinecraftClient;

public class AnimationSetting extends ButtonSetting {

    public AnimationSetting() {
        super();
        this.buttonName = "mflp.openScreen";
        this.setName("mflp.setting.customAnimations.name");
        this.setTooltip("mflp.setting.customAnimations.tooltip");
    }

    @Override
    public void run() {
        MinecraftClient.getInstance().setScreen(new AnimationsScreen());
    }

}
