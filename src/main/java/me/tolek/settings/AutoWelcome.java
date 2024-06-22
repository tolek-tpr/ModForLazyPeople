package me.tolek.settings;

import me.tolek.settings.base.BooleanSetting;
import net.minecraft.client.MinecraftClient;

public class AutoWelcome extends BooleanSetting {

    public AutoWelcome() {
        super("Auto welcome", false);
    }

    @Override
    public void run() {
        this.setState(!this.getState());
        // if (this.getState == false) then do something
    }

    @Override
    public void refresh() {
        if (this.getState() && MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.networkHandler.sendChatMessage("welcome!");
        }
    }

}
