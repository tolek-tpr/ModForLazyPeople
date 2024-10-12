package me.tolek.gui.widgets.settingsWidgets;

import me.tolek.modules.settings.base.StringSetting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class StringWidget extends TextFieldWidget {

    public StringWidget(int x, int y, StringSetting setting, TextRenderer tx) {
        super(tx, x + 6, y, 148, 20, Text.literal(setting.getState()));
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
