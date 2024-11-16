package me.tolek.gui.widgets.hotkeys;

import me.tolek.input.Hotkey;
import me.tolek.modules.settings.base.HotkeyableSetting;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.function.Consumer;

public class HotkeySettingWidget extends ButtonWidget {

    @Nullable
    private Hotkey selectedHotkey;
    private Consumer<Hotkey> hotkeyConsumer = (b) -> {};
    private final PressAction onPress;
    private HashMap<Integer, Integer> keys = new HashMap<>();
    private final HotkeyableSetting setting;
    private boolean set = false;

    public HotkeySettingWidget(int x, int y, Text message, HotkeyableSetting setting, @Nullable Hotkey selectedHotkey) {
        super(x, y, 150, 20, message, (b) -> {}, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.selectedHotkey = selectedHotkey;
        this.onPress = (button) -> {
            this.selectedHotkey = setting.getHotkey();
            this.hotkeyConsumer.accept(this.selectedHotkey);
            this.keys = new HashMap<>();
            this.setMessage(Text.literal("NONE"));
            this.update();
        };
        this.setting = setting;
    }

    public void setHotkeyConsumer(Consumer<Hotkey> consumer) { this.hotkeyConsumer = consumer; }

    public void setSelectedHotkey(@Nullable Hotkey hotkey) { this.selectedHotkey = hotkey; }
    public @Nullable Hotkey getSelectedHotkey() { return this.selectedHotkey; }

    public void update() {
        Text originalMessage = this.getMessage();
        if (this.selectedHotkey != null && !set) {
            MutableText buttonText = Text.literal("> ").formatted(Formatting.YELLOW).append(originalMessage).formatted(Formatting.YELLOW)
                    .append(Text.literal(" <").formatted(Formatting.YELLOW));

            this.setMessage(buttonText);
            this.set = true;
        } else {
            this.setMessage(this.setting.getHotkey().getFormattedKeys());
            this.set = false;
        }
    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.selectedHotkey != null) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                this.selectedHotkey.setKeys(this.keys);
                this.keys = new HashMap<>();
                this.selectedHotkey = null;
                this.hotkeyConsumer.accept(null);
                this.update();
                return true;
            } else {
                this.keys.put(keyCode, scanCode);
                this.selectedHotkey.setKeys(this.keys);
                MutableText buttonText = Text.literal("> ").formatted(Formatting.YELLOW).append(setting.getHotkey().getFormattedKeys()).formatted(Formatting.YELLOW)
                        .append(Text.literal(" <").formatted(Formatting.YELLOW));

                this.setMessage(buttonText);
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

}
