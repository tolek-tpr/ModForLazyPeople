package me.tolek.gui.widgets.settingsWidgets.rework;

import me.tolek.gui.screens.MflpSettingsScreen;
import me.tolek.gui.widgets.settingsWidgets.StringSettingWidget;
import me.tolek.gui.widgets.settingsWidgets.StringSettingWidgetContainer;
import me.tolek.modules.settings.base.StringSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class StringWidget extends AbstractSettingWidget {

    private final StringSetting setting;
    private final TextRenderer tx;
    private ArrayList<ClickableWidget> children = new ArrayList<>();

    private final int x;
    private final int y;

    public StringWidget(int x, int y, Text text, StringSetting setting, TextRenderer tx) {
        super(x, y, 310, 20, text);
        this.setting = setting;
        this.tx = tx;
        this.x = x;
        this.y = y;

        // Rendering code
        InputBox widget = new InputBox(tx, x + 5, y, setting);

        children.add(widget);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTextWithShadow(tx, setting.getName(), x - 155,
                y + 10 - tx.fontHeight / 2, 0xffffff);
        children.forEach(c -> c.render(context, mouseX, mouseY, delta));
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    public ArrayList<ClickableWidget> children() {
        return children;
    }

    public class InputBox extends TextFieldWidget {
        public InputBox(TextRenderer textRenderer, int x, int y, StringSetting setting) {
            super(textRenderer, x, y, 150, 20, Text.literal(setting.getState()));
            this.setMaxLength(Integer.MAX_VALUE);
            this.setText(setting.getState());
            this.setTooltip(Tooltip.of(Text.literal(setting.getTooltip())));
            this.setChangedListener((state) -> {
                if (setting.validateString(state)) {
                    this.setEditableColor(14737632);
                    setting.setState(state);
                } else {
                    this.setEditableColor(16711680);
                }
            });
        }
    }

}
