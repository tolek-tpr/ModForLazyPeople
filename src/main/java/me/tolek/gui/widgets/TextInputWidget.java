package me.tolek.gui.widgets;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class TextInputWidget extends TextFieldWidget {

    public TextInputWidget(TextRenderer textRenderer, int x, int y, int width, int height, Text text, Consumer<String> consumer) {
        super(textRenderer, width, height, text);
        this.setMaxLength(Integer.MAX_VALUE);
        this.setText(text.getString());
        this.setChangedListener(consumer);
        this.setPosition(x, y);
    }

}
