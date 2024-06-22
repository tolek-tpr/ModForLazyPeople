package me.tolek.settings;

import me.tolek.settings.base.StringSetting;
import net.minecraft.text.Text;

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

