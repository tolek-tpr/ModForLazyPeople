package me.tolek.gui.widgets.settingsWidgets;

import me.tolek.gui.screens.MflpSettingsScreen;
import me.tolek.modules.settings.base.ListSetting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ListWidget extends ButtonWidget {

    private final ListSetting setting;

    public ListWidget(int x, int y, ListSetting setting) {
        super(x + 5, y, 150, 20, Text.translatable(String.valueOf(setting.getState())), (button -> {
            setting.run();
        }), ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.setTooltip(Tooltip.of(Text.translatable(setting.getTooltip())));
        this.setting = setting;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        //super.render(context, mouseX, mouseY, delta);
        this.setMessage(Text.translatable(String.valueOf(setting.getState())));
        super.renderWidget(context, mouseX, mouseY, delta);
    }

}
