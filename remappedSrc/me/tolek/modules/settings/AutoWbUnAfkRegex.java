package me.tolek.modules.settings;

import me.tolek.modules.settings.base.StringSetting;
import me.tolek.util.RegexUtil;

public class AutoWbUnAfkRegex extends StringSetting {

    public AutoWbUnAfkRegex() {
        super("mflp.setting.unAfkRegex.name", "^\\[!\\] %u is no longer AFK\\.$", "mflp.setting.unAfkRegex.tooltip");
        this.setState("^\\[!\\] %u is no longer AFK\\.$");
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
