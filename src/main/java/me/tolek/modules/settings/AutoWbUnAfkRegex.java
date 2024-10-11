package me.tolek.modules.settings;

import me.tolek.modules.settings.base.StringSetting;
import me.tolek.util.RegexUtil;

public class AutoWbUnAfkRegex extends StringSetting {

    public AutoWbUnAfkRegex() {
        super("Un-AFK RegEx", "^%u is no longer AFK\\.$", "The RegEx to use to match if a message contains the user has returned from afk text.");
        this.setState("^%u is no longer AFK\\.$");
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
