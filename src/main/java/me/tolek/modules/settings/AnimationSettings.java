package me.tolek.modules.settings;

import net.minecraft.text.Text;

import java.util.ArrayList;

public class AnimationSettings {

    private static AnimationSettings instance;

    private AnimationSettings() {}

    public static AnimationSettings getInstance() {
        if (instance == null) instance = new AnimationSettings();
        return instance;
    }

    public AnimationDoubleSetting posX = new AnimationDoubleSetting(Text.translatable("mflp.animations.posX"), 0, -150, 150);
    public AnimationDoubleSetting posY = new AnimationDoubleSetting(Text.translatable("mflp.animations.posY"), 0, -150, 150);
    public AnimationDoubleSetting posZ = new AnimationDoubleSetting(Text.translatable("mflp.animations.posZ"), 0, -150, 50);
    public AnimationDoubleSetting rotX = new AnimationDoubleSetting(Text.translatable("mflp.animations.rotX"), 0, -180, 180);
    public AnimationDoubleSetting rotY = new AnimationDoubleSetting(Text.translatable("mflp.animations.rotY"), 0, -180, 180);
    public AnimationDoubleSetting rotZ = new AnimationDoubleSetting(Text.translatable("mflp.animations.rotZ"), 0, -180, 180);
    public AnimationDoubleSetting scale = new AnimationDoubleSetting(Text.translatable("mflp.animations.scale"), 1, 0.1, 2);
    public AnimationIntSetting swingSpeed = new AnimationIntSetting(Text.translatable("mflp.animations.swingSpeed"), 6, 2, 20);
    public AnimationBoolSetting cancelReequipAnimation = new AnimationBoolSetting(Text.translatable("mflp.animations.reequip"), false);

    public ArrayList<AnimationSetting> getFloatSettings() {
        ArrayList<AnimationSetting> arr = new ArrayList<>();
        arr.add(posX);
        arr.add(posY);
        arr.add(posZ);
        arr.add(rotX);
        arr.add(rotY);
        arr.add(rotZ);
        arr.add(scale);
        arr.add(swingSpeed);
        arr.add(cancelReequipAnimation);
        return arr;
    }

    public static class AnimationSetting {

        transient Text settingName;

        public AnimationSetting(Text settingName) {
            this.settingName = settingName;
        }

        public void setSettingName(Text settingName) { this.settingName = settingName; }
        public Text settingName() { return settingName; }

    }

    public static class AnimationBoolSetting extends AnimationSetting {

        boolean val;

        public AnimationBoolSetting(Text settingName, boolean val) {
            super(settingName);
            this.val = val;
        }

        public boolean value() { return this.val; }
        public void setValue(boolean val) { this.val = val; }

    }

    public static class AnimationIntSetting extends AnimationSetting {

        int value;
        transient int min, max;

        public AnimationIntSetting(Text settingName, int value, int min, int max) {
            super(settingName);
            this.value = value;
            this.min = min;
            this.max = max;
        }

        public void setValue(int val) { this.value = val; }
        public void setMin(int min) { this.min = min; }
        public void setMax(int max) { this.max = max; }

        public int value() { return value; }
        public int min() { return min; }
        public int max() { return max; }

    }

    public static class AnimationDoubleSetting extends AnimationSetting {

        double value;
        transient double min, max;

        public AnimationDoubleSetting(Text settingName, double value, double min, double max) {
            super(settingName);
            this.value = value;
            this.min = min;
            this.max = max;
        }

        public void setValue(double val) { this.value = val; }
        public void setMin(double min) { this.min = min; }
        public void setMax(double max) { this.max = max; }

        public double value() { return value; }
        public double min() { return min; }
        public double max() { return max; }

    }

}
