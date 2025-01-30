package me.tolek.files;

import me.tolek.MflpInfo;
import me.tolek.modules.autoReply.AutoReply;
import me.tolek.modules.macro.Macro;
import me.tolek.modules.settings.CustomMessagePerServerList;
import me.tolek.modules.settings.CustomPlayerMessageList;
import me.tolek.modules.settings.MflpSettingsList;
import net.minecraft.client.option.KeyBinding;

import java.util.ArrayList;

public class ModData {

    public record SettingsData(MflpSettingsList settingsList) {}
    public record MainConfigData(MflpInfo config, CustomPlayerMessageList playerMessageList, CustomMessagePerServerList serverMessageList) {}
    public record MacroData(ArrayList<ShortMacro> macros) {}
    public record AutoReplyData(ArrayList<AutoReply> autoReplies) {}

    public static class ShortMacro implements ISerializable {

        public String name;
        public ArrayList<String> commands;
        public int key;
        public int repeatAmt = 1;
        public boolean isUneditable = false;
        public boolean isOn = true;
        public int worldSpecificOptionIndex = 0;
        public String allowedServers = "";
        public int executeOption = 0;

        public ShortMacro(String name, ArrayList<String> commands, int key, int repeatAmt, boolean isUneditable, boolean isOn, int worldSpecificOptionIndex, String allowedServers, int executeOption) {
            this.name = name;
            this.commands = commands;
            this.key = key;
            this.repeatAmt = repeatAmt;
            this.isUneditable = isUneditable;
            this.isOn = isOn;
            this.worldSpecificOptionIndex = worldSpecificOptionIndex;
            this.allowedServers = allowedServers;
            this.executeOption = executeOption;
        }

        public static ShortMacro fromMacro(Macro m) {
            return new ShortMacro(m.getName(), m.getCommands(), m.getKey(), m.getRepeatAmount(), m.getUneditable(), m.getTurnedOn(),
                    m.getWorldSpecificOptionIndex(), m.getAllowedServers(), m.getExecuteOption());
        }

        public static Macro toMacro(ShortMacro sm) {
            Macro m = new Macro(new KeyBinding("mflp.keybinding.undefined",
                    sm.key,
                    "mflp.keybindCategory.MFLP"), sm.commands, sm.name, sm.repeatAmt, sm.isUneditable, sm.isOn);
            m.setKey(sm.key);
            m.setAllowedServers(sm.allowedServers);
            m.setWorldSpecificOptionIndex(sm.worldSpecificOptionIndex);
            m.setExecuteOption(sm.executeOption);
            return m;
        }

    }

}
