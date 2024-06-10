package me.tolek.settings;

import me.tolek.settings.base.BooleanSetting;
import me.tolek.util.MflpUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class AutoWelcomeBack extends BooleanSetting {

    public AutoWelcomeBack() {
        super("Auto welcome back", false);
    }

    @Override
    public void run() {
        this.setState(!this.getState());
        // if (this.getState == false) then do something
    }

    @Override
    public void refresh() {
        if (this.getState()) {
            MinecraftClient.getInstance().player.sendMessage(Text.literal("wb"));
        }
    }

}
