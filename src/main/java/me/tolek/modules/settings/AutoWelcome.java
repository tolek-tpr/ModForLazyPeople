package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;
import me.tolek.modules.settings.base.MflpSetting;
import me.tolek.util.InstancedValues;
import me.tolek.util.MflpUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class AutoWelcome extends BooleanSetting {

    public String lastName;

    public AutoWelcome() {
        super("Auto welcome", false, "Automatically says welcome! when a new person joins synergy");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
        // if (this.getState == false) then do something
    }

    @Override
    public void refresh() {
        if (this.getState() && MinecraftClient.getInstance().player != null) {
            String message = MflpSettingsList.getInstance().WELCOME_MESSAGE.getState();
            if (message.contains("%p") && lastName != null) {
                message = message.replace("%p", lastName);
            }
            MinecraftClient.getInstance().player.networkHandler.sendChatMessage(message);
        }
    }
}
