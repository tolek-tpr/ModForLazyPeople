package me.tolek.gui.widgets.settingsWidgets;

import me.tolek.modules.settings.base.FloatSetting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class FloatWidget extends TextFieldWidget {
    public FloatWidget(int x, int y, FloatSetting setting, TextRenderer tx) {
        super(tx, x + 6, y, 148, 20, Text.literal(String.valueOf(setting.getState())));
        this.setMaxLength(Integer.MAX_VALUE);
        this.setText(String.valueOf(setting.getState()));
        this.setTooltip(Tooltip.of(Text.translatable(setting.getTooltip())));
        this.setChangedListener((state) -> {
            if (setting.validateFloat(state)) {
                this.setEditableColor(14737632);
                setting.setState(Float.parseFloat(state));
            } else {
                this.setEditableColor(16711680);
            }
        });
    }
}
