package me.tolek.gui.widgets;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.function.Consumer;

public class DoubleSliderWidget extends SliderWidget {

    private double min = 0;
    private double max = 1;
    private Consumer<Double> callback = (d) -> {};
    private double internalValue;

    public DoubleSliderWidget(int x, int y, int width, int height, double value) {
        super(x, y, width, height, Text.literal(value + ""), value);
        this.value = normalize(value, min, max);
        this.internalValue = value;
        this.updateMessage();
    }

    public DoubleSliderWidget(int x, int y, int width, int height, double value, double min, double max) {
        super(x, y, width, height, Text.literal(value + ""), value);
        this.min = min;
        this.max = max;
        this.value = normalize(value, min, max);
        this.internalValue = value;
        this.updateMessage();
    }

    public DoubleSliderWidget setCallback(Consumer<Double> callback) {
        this.callback = callback;
        return this;
    }

    public DoubleSliderWidget setBounds(double min, double max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Text.literal(String.format("%.2f", internalValue)));
    }

    @Override
    protected void applyValue() {
        value = MathHelper.clamp(this.value, 0.0, 1.0);
        internalValue = MathHelper.lerp(MathHelper.clamp(this.value, 0.0, 1.0), this.min, this.max);
        callback.accept(internalValue);
    }

    public static double normalize(double v1, double min, double max) {
        return (v1 - min) / (max - min);
    }

}
