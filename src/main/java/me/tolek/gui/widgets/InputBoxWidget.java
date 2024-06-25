package me.tolek.gui.widgets;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.EditBox;
import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class InputBoxWidget extends TextFieldWidget {

    private EditBox editBox;
    private Consumer<Integer> keyConsumer = (s -> {});

    public InputBoxWidget(TextRenderer textRenderer, int x, int y, int width, int height, Text message) {
        super(textRenderer, x, y, width, height, message);
        this.setText(message.getString());
        //this.editBox.setCursorChangeListener(onCursor);
    }

    public void setKeyConsumer(Consumer<Integer> keyConsumer) {
        this.keyConsumer = keyConsumer;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        keyConsumer.accept(keyCode);

        return super.keyPressed(keyCode, scanCode, modifiers);
    }



}
