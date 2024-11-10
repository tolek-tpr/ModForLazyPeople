package me.tolek.modules.settings.base;

public abstract class MflpSetting {
    // transient = exclude

    private transient String name;
    private transient final Object defaultValue;
    private transient String tooltip;
    public transient String type;
    public transient boolean render = true;

    public MflpSetting(String name, Object defaultValue, String type, String tooltip) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.type = type;
        this.tooltip = tooltip;
    }

    public void refresh() {}

    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }
    public String getTooltip() { return this.tooltip; }
    public void setTooltip(String tooltip) { this.tooltip = tooltip; }
    public Object getDefaultValue() { return this.defaultValue; }

}
