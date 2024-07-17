package me.tolek.gui.widgets.macros;

import me.tolek.input.Hotkey;
import me.tolek.modules.Macro.Macro;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class HotkeyButton extends ButtonWidget {

    private Hotkey selectedHotkey = null;
    private ArrayList<Integer> keys = new ArrayList<>();
    private Macro m;

    public HotkeyButton(int x, int y, Macro m) {
        super(x, y, 80, 20, m.getFormattedKeys(), (button) -> {},
                ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.m = m;
    }

    @Override
    public void onPress() {
        selectedHotkey = m.getKeyBinding();
        System.out.println("test");
        this.update();
        super.onPress();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // if empty list of buttons pressed then do clear, otherwise set selected to null
        // tf was I on when writing this lmao
        if (selectedHotkey != null) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                if (keys.isEmpty()) {
                    keys.add(-1);
                    m.setKeys(keys);
                }
                this.selectedHotkey = null;
                update();
            } else {
                keys.add(keyCode);
                update();
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void update() {
        //this.setMessage(Text.of(selectedHotkey == null ? "NULL" : "NOT NULL"));
        if (selectedHotkey == null) {
            this.setMessage(m.getFormattedKeys());
        } else {
            MutableText text = Text.literal("> ").formatted(Formatting.YELLOW);
            Text keysName = m.getFormattedKeys();
            Text a = Text.literal(" <").formatted(Formatting.YELLOW);
            keysName.getStyle().withFormatting(Formatting.YELLOW, Formatting.UNDERLINE);
            text.append(keysName);
            text.append(a);

            this.setMessage(text);
        }
    }

}
