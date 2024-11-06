package me.tolek.gui.widgets.settingsWidgets;

import me.tolek.gui.screens.MflpSettingsScreen;
import me.tolek.modules.settings.base.BooleanSetting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@Environment(EnvType.CLIENT)
public class BooleanWidget extends ButtonWidget {

    private final BooleanSetting setting;

    public BooleanWidget(int x, int y, BooleanSetting setting) {
        super(x + 5, y, 150, 20, setting.getState() ? Text.translatable("mflp.true").formatted(Formatting.GREEN)
                : Text.translatable("mflp.false").formatted(Formatting.RED), (button) -> {
            setting.run();
        }, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.setTooltip(Tooltip.of(Text.translatable(setting.getTooltip())));
        this.setting = setting;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        this.setMessage(setting.getState() ? Text.translatable("mflp.true").formatted(Formatting.GREEN)
                : Text.translatable("mflp.false").formatted(Formatting.RED));
        super.renderWidget(context, mouseX, mouseY, delta);
    }

}
