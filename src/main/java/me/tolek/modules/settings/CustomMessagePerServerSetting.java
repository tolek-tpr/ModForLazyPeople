package me.tolek.modules.settings;

import me.tolek.gui.screens.CustomMessagePerServerScreen;
import me.tolek.modules.settings.base.ButtonSetting;
import net.minecraft.client.MinecraftClient;

public class CustomMessagePerServerSetting extends ButtonSetting {

    public CustomMessagePerServerSetting() {
        super();
        this.buttonName = "mflp.openScreen";
        this.setName("mflp.setting.customServerMessages.name");
        this.setTooltip("mflp.setting.customServerMessages.tooltip");
    }

    @Override
    public void run() {
        MinecraftClient.getInstance().setScreen(new CustomMessagePerServerScreen());
    }

}
