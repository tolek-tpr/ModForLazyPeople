package me.tolek.modules.settings;

import me.tolek.interfaces.IScheduler;
import me.tolek.modules.settings.base.BooleanSetting;
import me.tolek.modules.settings.base.MflpSetting;
import me.tolek.util.InstancedValues;
import me.tolek.util.MflpUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;

public class AutoWelcomeBack extends BooleanSetting {

    public String lastName;

    public AutoWelcomeBack() {
        super("Auto welcome back", false, "Automatically says wb when someone comes back from being afk or joins synergy.");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
        // if (this.getState == false) then do something
    }

    @Override
    public void refresh() {
        if (this.getState() && MinecraftClient.getInstance().player != null) {
            String message = MflpSettingsList.getInstance().WB_MESSAGE.getState();
            if (message.contains("%p") && lastName != null) {
                message = message.replace("%p", lastName);
            }
            MinecraftClient.getInstance().player.networkHandler.sendChatMessage(message);
            InstancedValues.getInstance().timeSinceLastWbInMils = 0;
        }
    }
}
