package me.tolek.modules.settings;

import me.tolek.modules.settings.base.StringSetting;
import me.tolek.util.RegexUtil;

public class AutoWbJoinRegex extends StringSetting {

    public AutoWbJoinRegex() {
        super("mflp.setting.autoWbJoinRegex.name", "^%u has joined\\.$", "mflp.setting.autoWbJoinRegex.tooltip");
        this.setState("^%u has joined\\.$");
        this.render = false;
    }

    @Override
    public void run() {

    }

    @Override
    public boolean validateString(String s) {
        return RegexUtil.validateRegex(s).value1;
    }
}
