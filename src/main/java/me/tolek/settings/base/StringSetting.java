package me.tolek.settings.base;

import net.minecraft.text.Text;

public abstract class StringSetting extends MflpSetting {

    private String state;

    public StringSetting(String name, String defaultValue, String tt) {
        super(name, defaultValue, "string", tt);
    }

    public abstract void run();
    public abstract boolean validateString(String s);

    public void setState(String state) { this.state = state; }

    public String getState() { return this.state; }
}
