package me.tolek.modules.settings.base;

public class ColorSetting extends MflpSetting {

    private int state;

    public ColorSetting(String name, Object defaultValue, String tooltip) {
        super(name, defaultValue, "color", tooltip);
    }

    /**
     *
     * @param hexColor - Integer, ALWAYS ALPHA RED GREEN BLUE
     */
    public void setColor(int hexColor) { this.state = hexColor; }
    public int getColor() { return this.state; }

}
