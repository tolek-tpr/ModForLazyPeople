package me.tolek.modules.settings;

import me.tolek.interfaces.IScheduler;
import me.tolek.modules.settings.base.BooleanSetting;
import me.tolek.modules.settings.base.MflpSetting;
import me.tolek.util.InstancedValues;
import me.tolek.util.MflpUtil;
import me.tolek.util.Tuple;
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

    private boolean validateMessageList(Text message) {
        message = Text.literal(message.getString().toLowerCase());
        for (Tuple t : CustomPlayerMessageList.getInstance().getMessages()) {
            if (message.getString().contains(((String) t.value1).toLowerCase())) return true;
        }
        return false;
    }

    @Override
    public void refresh() {
        if (this.getState() && MinecraftClient.getInstance().player != null) {
            String message = MflpSettingsList.getInstance().WB_MESSAGE.getState();
            if (MflpSettingsList.getInstance().WB_PLAYER_BLACKLIST.getState().contains(lastName)) {
                message = message.replaceAll("%p", "");
            } else if (message.contains("%p")) {
                message = message.replace("%p", lastName);
            }
            MinecraftClient.getInstance().player.networkHandler.sendChatMessage(message);
            InstancedValues.getInstance().timeSinceLastWbInMils = 0;
        }
    }

    public void refresh(Text message) {
        if (this.getState() && MinecraftClient.getInstance().player != null) {
            String reply = MflpSettingsList.getInstance().WB_MESSAGE.getState();
            String messageForName = CustomPlayerMessageList.getInstance().getMessageForName(lastName.toLowerCase());

            if (validateMessageList(message)) reply = messageForName == null ? reply : messageForName;

            if (MflpSettingsList.getInstance().WB_PLAYER_BLACKLIST.getState().toLowerCase().contains(lastName.toLowerCase())) {
                reply = reply.replaceAll("%p", "");
            } else if (reply.contains("%p")) {
                reply = reply.replace("%p", lastName);
            }

            MinecraftClient.getInstance().player.networkHandler.sendChatMessage(reply);
            InstancedValues.getInstance().timeSinceLastWbInMils = 0;
        }
    }

}
