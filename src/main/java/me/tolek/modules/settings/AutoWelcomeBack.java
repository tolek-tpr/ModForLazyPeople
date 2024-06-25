package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;
import net.minecraft.client.MinecraftClient;

public class AutoWelcomeBack extends BooleanSetting {

    public AutoWelcomeBack() {
        super("Auto welcome back", false, "Automatically says wb when someone comes back from being afk or joins synergy");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
        // if (this.getState == false) then do something
    }

    @Override
    public void refresh() {
        if (this.getState() && MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.networkHandler.sendChatMessage(MflpSettingsList.getInstance().WB_MESSAGE.getState());
        }
    }

}
