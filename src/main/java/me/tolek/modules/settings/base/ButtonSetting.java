package me.tolek.modules.settings.base;

public abstract class ButtonSetting extends MflpSetting {

    public String buttonName;

    public ButtonSetting() {
        super("Per player messages", null, "button", "Opens the custom message per player menu");
    }

    public abstract void run();

}
