package me.tolek.modules.settings.base;

public abstract class ButtonSetting extends MflpSetting {

    public String buttonName;

    public ButtonSetting() {
        super("", null, "button", "");
    }

    public abstract void run();
}