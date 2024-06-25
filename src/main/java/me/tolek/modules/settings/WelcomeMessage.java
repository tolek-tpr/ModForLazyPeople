package me.tolek.modules.settings;

import me.tolek.modules.settings.base.StringSetting;

public class WelcomeMessage extends StringSetting {

    public WelcomeMessage() {
        super("Welcome message", "welcome!", "What message auto welcome should say");
        this.setState("welcome!");
    }

    @Override
    public void run() {

    }

    @Override
    public boolean validateString(String s) {
        return !s.contains("-");
    }
}

