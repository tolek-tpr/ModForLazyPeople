package me.tolek.util;

import me.tolek.modules.settings.MflpSettingsList;

public class ChatUtil {

    public static boolean canSendWb() {
        MflpSettingsList settingsList = MflpSettingsList.getInstance();
        InstancedValues iv = InstancedValues.getInstance();
        return !(settingsList.AUTO_WELCOME_BACK.getState() && settingsList.AUTO_IGNORE_WB_MESSAGES.getState() && iv.timeSinceLastWbMillis < settingsList.AUTO_IGNORE_WB_MESSAGES_DURATION.getState() * 1000L);
    }

}
