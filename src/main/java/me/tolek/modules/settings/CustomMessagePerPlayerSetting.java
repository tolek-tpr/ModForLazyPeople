package me.tolek.modules.settings;

import me.tolek.gui.screens.CustomMessagePerServerScreen;
import me.tolek.modules.settings.base.ButtonSetting;
import net.minecraft.client.MinecraftClient;

public class CustomMessagePerPlayerSetting extends ButtonSetting {

    public CustomMessagePerPlayerSetting() {
        super();
        this.buttonName = "Open screen";
        this.setName("Custom server messages");
        this.setTooltip("Opens the screen where you can modify the server join/afk messages");
    }

    @Override
    public void run() {
        MinecraftClient.getInstance().setScreen(new CustomMessagePerServerScreen());
    }

}
