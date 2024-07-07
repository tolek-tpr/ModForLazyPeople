package me.tolek.gui.widgets.settingsWidgets.rework;

import me.tolek.modules.settings.base.BooleanSetting;
import me.tolek.modules.settings.base.StringSetting;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class StringWidget extends AbstractSettingWidget {

    private StringSetting setting;
    private TextRenderer tx;

    private int x;
    private int y;

    public StringWidget(int x, int y, Text text, StringSetting setting, TextRenderer tx) {
        super(x, y, 310, 20, text);
        this.setting = setting;
        this.tx = tx;
        this.x = x;
        this.y =y;

        // Rendering code
        TextFieldWidget widget = new TextFieldWidget(tx, x, y, 150, 20, Text.literal(setting.getState()));
        widget.setMaxLength(Integer.MAX_VALUE);
        widget.setText(setting.getState());
        widget.setTooltip(Tooltip.of(Text.literal(setting.getTooltip())));
        widget.setChangedListener((state) -> {
            if (setting.validateString(state)) {
                widget.setEditableColor(14737632);
                setting.setState(state);
            } else {
                widget.setEditableColor(16711680);
            }
        });
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTextWithShadow(tx, setting.getName(), x - 155,
                y + 10 - tx.fontHeight / 2, 0xffffff);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
