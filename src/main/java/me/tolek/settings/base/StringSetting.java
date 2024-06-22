package me.tolek.settings.base;

public abstract class StringSetting extends MflpSetting {

    private String state;

    public StringSetting(String name, String defaultValue) {
        super(name, defaultValue, "string");
    }

    public abstract void run();
    public abstract boolean validateString(String s);

    public void setState(String state) { this.state = state; }

    public String getState() { return this.state; }
}
