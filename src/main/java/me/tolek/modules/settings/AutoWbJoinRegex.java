package me.tolek.modules.settings;

import me.tolek.modules.settings.base.StringSetting;
import me.tolek.util.RegexUtil;

public class AutoWbJoinRegex extends StringSetting {

    public AutoWbJoinRegex() {
        super("Join RegEx", "^[a-zA-Z0-9_]{3,16} has joined\\.$", "The RegEx to use to match if a message contains the user has joined text.");
        this.setState("^[a-zA-Z0-9_]{3,16} has joined\\.$");
        this.render = false;
    }

    @Override
    public void run() {

    }

    @Override
    public boolean validateString(String s) {
        return RegexUtil.validateRegex(s);
    }
}
