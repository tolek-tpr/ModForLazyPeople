package me.tolek.gui.widgets.settingsWidgets;

import me.tolek.gui.screens.MflpSettingsScreen;
import me.tolek.modules.settings.base.ListSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ListWidget extends ButtonWidget {
    public ListWidget(int x, int y, ListSetting setting) {
        super(x + 5, y, 150, 20, Text.literal(String.valueOf(setting.getState())), (button -> {
            setting.run();
            MinecraftClient.getInstance().setScreen(new MflpSettingsScreen());
        }), ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.setTooltip(Tooltip.of(Text.literal(setting.getTooltip())));
    }
}
