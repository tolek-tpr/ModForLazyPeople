package me.tolek.gui.widgets;

import me.tolek.gui.screens.MflpSettingsScreen;
import me.tolek.modules.settings.base.BooleanSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class BooleanSettingWidget extends ButtonWidget {

    public BooleanSettingWidget(int x, int y, Text message, BooleanSetting bs, MinecraftClient client) {
        super(x, y, 150, 20, message, (button) -> {
            bs.run();
            client.setScreen(new MflpSettingsScreen());
        }, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
    }

}
