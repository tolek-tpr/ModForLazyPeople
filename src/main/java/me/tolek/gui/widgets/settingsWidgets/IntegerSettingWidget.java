package me.tolek.gui.widgets.settingsWidgets;

import me.tolek.modules.settings.base.IntegerSetting;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class IntegerSettingWidget extends TextFieldWidget {

    public IntegerSettingWidget(TextRenderer textRenderer, int x, int y, IntegerSetting setting) {
        super(textRenderer, x, y, 150, 20, Text.literal(String.valueOf(setting.getState())));
        this.setMaxLength(Integer.MAX_VALUE);
        this.setText(String.valueOf(setting.getState()));
        this.setTooltip(Tooltip.of(Text.literal(setting.getTooltip())));
        this.setChangedListener((state) -> {
            if (setting.validateInt(state)) {
                this.setEditableColor(14737632);
                setting.setState(Integer.parseInt(state));
            } else {
                this.setEditableColor(16711680);
            }
        });
    }

}
