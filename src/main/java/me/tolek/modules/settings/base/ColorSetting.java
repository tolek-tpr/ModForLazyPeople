package me.tolek.modules.settings.base;

import me.tolek.util.ColorUtil;
import me.tolek.util.RegexUtil;

public abstract class ColorSetting extends MflpSetting {

    private String a;
    private String r;
    private String g;
    private String b;
    private String state;

    public ColorSetting(String name, String defaultValue, String tooltip) {
        super(name, defaultValue, "color", tooltip);
        this.setColor(defaultValue);
    }

    public void setColor(String a, String r, String g, String b) {
        this.a = a;
        this.r = r;
        this.g = g;
        this.b = b;
        this.state = "#" + a + r + g + b;
    }
    public void setColor(String color) {
        if (color.equalsIgnoreCase("chroma")) {
            this.state = color;
            return;
        }
        this.a = color.substring(1, 3);
        this.r = color.substring(3, 5);
        this.g = color.substring(5, 7);
        this.b = color.substring(7, 9);
        this.state = color;
    }
    public String getColor() { return this.state; }

    public String[] getArgb() {
        if (getColor().equalsIgnoreCase("chroma")) return ColorUtil.getChromaARGB();
        return new String[]{ this.a, this.r, this.g, this.b };
    }

    public String getFormattedColor() {
        if (getColor().equalsIgnoreCase("chroma")) return "CHROMA";
        return "#" + this.a + this.r + this.g + this.b;
    }

    public boolean validateColor(String color) {
        return RegexUtil.evaluateRegex("^#[0-9A-F]{8}$", color) || color.equalsIgnoreCase("chroma");
    }

    public String getA() {
        if (getColor().equalsIgnoreCase("chroma")) return ColorUtil.getChromaARGB()[0];
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getR() {
        if (getColor().equalsIgnoreCase("chroma")) return ColorUtil.getChromaARGB()[1];
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getG() {
        if (getColor().equalsIgnoreCase("chroma")) return ColorUtil.getChromaARGB()[2];
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public String getB() {
        if (getColor().equalsIgnoreCase("chroma")) return ColorUtil.getChromaARGB()[3];
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

}
