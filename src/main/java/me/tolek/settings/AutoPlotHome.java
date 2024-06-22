package me.tolek.settings;

import me.tolek.settings.base.BooleanSetting;
import net.minecraft.client.MinecraftClient;

public class AutoPlotHome extends BooleanSetting {

    public AutoPlotHome() {
        super("Auto Plot Home", false);
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

    @Override
    public void refresh() {
        if (this.getState() && MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.networkHandler.sendCommand("/p h");
        }
    }

}
