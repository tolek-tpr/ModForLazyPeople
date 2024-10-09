package me.tolek.modules.settings;

import me.tolek.modules.settings.base.StringSetting;
import me.tolek.util.RegexUtil;

public class AutoWbUnAfkRegex extends StringSetting {

    public AutoWbUnAfkRegex() {
        super("Un-AFK RegEx", "^[a-zA-Z0-9_]{3,16} is no longer AFK\\.$", "The RegEx to use to match if a message contains the user has returned from afk text.");
        this.setState("^[a-zA-Z0-9_]{3,16} is no longer AFK\\.$");
    }

    @Override
    public void run() {

    }

    @Override
    public boolean validateString(String s) {
        return RegexUtil.validateRegex(s);
    }

}
