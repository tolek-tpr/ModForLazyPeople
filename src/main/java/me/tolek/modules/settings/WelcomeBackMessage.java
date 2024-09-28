package me.tolek.modules.settings;

import me.tolek.modules.settings.base.StringSetting;

public class WelcomeBackMessage extends StringSetting {

    public WelcomeBackMessage() {
        super("Welcome back message", "wb", "What message auto welcome back should say. Use %p to say the username of the player that joined.");
        this.setState("wb");
    }

    @Override
    public void run() {

    }

    @Override
    public boolean validateString(String s) {
        return !s.contains("-");
    }
}
