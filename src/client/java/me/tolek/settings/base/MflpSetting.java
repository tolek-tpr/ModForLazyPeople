package me.tolek.settings.base;

public abstract class MflpSetting {

    private String name;
    private Object defaultValue;
    public String type;

    public MflpSetting(String name, Object defaultValue, String type) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.type = type;
    }

    public void refresh() {}

    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }

}
