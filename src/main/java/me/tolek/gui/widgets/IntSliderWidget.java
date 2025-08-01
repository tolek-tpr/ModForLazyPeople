package me.tolek.gui.widgets;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.function.Consumer;

public class IntSliderWidget extends SliderWidget {

    private int min = 0;
    private int max = 1;
    private Consumer<Integer> callback = (d) -> {};
    private int internalValue;

    public IntSliderWidget(int x, int y, int width, int height, int value) {
        super(x, y, width, height, Text.literal(value + ""), value);
        this.value = normalize(value, min, max);
        this.internalValue = value;
        this.updateMessage();
    }

    public IntSliderWidget(int x, int y, int width, int height, int value, int min, int max) {
        super(x, y, width, height, Text.literal(value + ""), value);
        this.min = min;
        this.max = max;
        this.value = normalize(value, min, max);
        this.internalValue = value;
        this.updateMessage();
    }

    public IntSliderWidget setCallback(Consumer<Integer> callback) {
        this.callback = callback;
        return this;
    }

    public IntSliderWidget setBounds(int min, int max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Text.literal(internalValue + ""));
    }

    @Override
    protected void applyValue() {
        value = MathHelper.clamp(this.value, 0.0, 1.0);
        internalValue = (int) MathHelper.lerp(MathHelper.clamp(this.value, 0.0, 1.0), this.min, this.max);
        callback.accept(internalValue);
    }

    public static double normalize(double v1, double min, double max) {
        return (v1 - min) / (max - min);
    }

}
