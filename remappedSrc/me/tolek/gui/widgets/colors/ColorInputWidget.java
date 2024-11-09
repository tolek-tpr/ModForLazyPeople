package me.tolek.gui.widgets.colors;

import me.tolek.modules.settings.base.ColorSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class ColorInputWidget extends TextFieldWidget {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private final TextRenderer tx = client.textRenderer;
    private final ColorSetting setting;

    public ColorInputWidget(Text text, int x, int y, ColorSetting setting) {
        super(MinecraftClient.getInstance().textRenderer, 75, 20, text);
        this.setX(x);
        this.setY(y);
        this.setMaxLength(Integer.MAX_VALUE);
        this.setText(text.getString());
        this.setChangedListener((state) -> {
            if (setting.validateColor(state)) {
                this.setEditableColor(14737632);
                setting.setColor(state);
            } else {
                this.setEditableColor(16711680);
            }
        });
        this.setting = setting;
    }

    public void update() {
        this.setText(setting.getFormattedColor());
    }

}
