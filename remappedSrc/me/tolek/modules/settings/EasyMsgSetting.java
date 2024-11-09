package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;
import me.tolek.util.ScreenUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;

public class EasyMsgSetting extends BooleanSetting {

    public EasyMsgSetting() {
        super("mflp.setting.easyMsgSetting.name", false, "mflp.setting.easyMsgSetting.tooltip");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

    @Override
    public void refresh() {
        if (this.getState() && MinecraftClient.getInstance().player != null) {
            ScreenUtil.openScreen(new ChatScreen("/msg "));
        }
    }

    public void refresh(String message) {
        if (this.getState() && MinecraftClient.getInstance().player != null) {
            ScreenUtil.openScreen(new ChatScreen(message));
        }
    }

}
