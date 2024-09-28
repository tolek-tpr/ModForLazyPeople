package me.tolek.gui.widgets.settingsWidgets;

import me.tolek.gui.screens.MflpSettingsScreen;
import me.tolek.modules.settings.base.BooleanSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class BooleanWidget extends ButtonWidget {
    public BooleanWidget(int x, int y, BooleanSetting setting) {
        super(x + 5, y, 150, 20, setting.getState() ? Text.literal("True").formatted(Formatting.GREEN)
                : Text.literal("False").formatted(Formatting.RED), (button) -> {
            setting.run();
            MinecraftClient.getInstance().setScreen(new MflpSettingsScreen());
        }, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.setTooltip(Tooltip.of(Text.literal(setting.getTooltip())));
    }
}
