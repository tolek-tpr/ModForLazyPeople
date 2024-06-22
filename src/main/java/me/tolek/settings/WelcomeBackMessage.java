package me.tolek.settings;

import me.tolek.settings.base.StringSetting;
import net.minecraft.text.Text;

public class WelcomeBackMessage extends StringSetting {

    public WelcomeBackMessage() {
        super("Welcome back message", "wb", "What message auto welcome back should say");
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
