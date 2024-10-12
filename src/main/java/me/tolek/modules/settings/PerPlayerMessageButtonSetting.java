package me.tolek.modules.settings;

import me.tolek.gui.screens.CustomPlayerMessageScreen;
import me.tolek.modules.settings.base.ButtonSetting;
import net.minecraft.client.MinecraftClient;

public class PerPlayerMessageButtonSetting extends ButtonSetting {

    public PerPlayerMessageButtonSetting() {
        super();
        this.buttonName = "mflp.openScreen";
        this.setName("mflp.setting.perPlayerMessages.name");
        this.setTooltip("mflp.setting.perPlayerMessages.tooltip");
    }

    @Override
    public void run() {
        MinecraftClient.getInstance().setScreen(new CustomPlayerMessageScreen());
    }

}
