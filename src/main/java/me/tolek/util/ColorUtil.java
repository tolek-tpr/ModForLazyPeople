package me.tolek.util;

public class ColorUtil {

    public static int argbToInt(int a, int r, int g, int b) {
        a = a & 0xFF;
        r = r & 0xFF;
        g = g & 0xFF;
        b = b & 0xFF;

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static double hex2DecBetween1And0(String hexInput) {
        int dec = Integer.parseInt(hexInput, 16);
        int maxVal = (int) Math.pow(16, hexInput.length()) - 1;
        return dec / (double) maxVal;
    }

    public static int floatToInt255(float value) {
        // Clamp the value between 0.0 and 1.0 to avoid out-of-range values
        value = Math.max(0.0f, Math.min(1.0f, value));
        // Scale the float to an integer range of 0-255
        return Math.round(value * 255);
    }

    public static int rgbaToInt(float red, float green, float blue, float alpha) {
        // Convert float (0.0-1.0) to int (0-255)
        int r = floatToInt255(red);
        int g = floatToInt255(green);
        int b = floatToInt255(blue);
        int a = floatToInt255(alpha);

        // Combine the components into a single integer in RGBA order
        return (r << 24) | (g << 16) | (b << 8) | a;
    }

}
