package me.tolek.gui.widgets;

import me.tolek.gui.screens.MflpSettingsScreen;
import me.tolek.modules.settings.base.ListSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ListSettingWidget extends ButtonWidget {

    public ListSettingWidget(int x, int y, Text message, ListSetting ls, MinecraftClient client) {
        super(x, y, 150, 20, message, (button) -> {
            ls.run();
            client.setScreen(new MflpSettingsScreen());
        }, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.setTooltip(Tooltip.of(Text.literal(ls.getTooltip())));
    }

}
