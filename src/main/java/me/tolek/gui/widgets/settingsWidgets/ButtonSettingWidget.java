package me.tolek.gui.widgets.settingsWidgets;

import me.tolek.gui.screens.MflpSettingsScreen;
import me.tolek.modules.settings.base.BooleanSetting;
import me.tolek.modules.settings.base.ButtonSetting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@Environment(EnvType.CLIENT)
public class ButtonSettingWidget extends ButtonWidget {
    public ButtonSettingWidget(int x, int y, ButtonSetting setting) {
        super(x + 5, y, 150, 20, Text.literal(setting.buttonName), (button) -> {
            setting.run();
        }, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.setTooltip(Tooltip.of(Text.literal(setting.getTooltip())));
    }
}
