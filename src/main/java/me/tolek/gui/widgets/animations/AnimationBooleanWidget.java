package me.tolek.gui.widgets.animations;

import me.tolek.modules.settings.AnimationSettings;
import me.tolek.modules.settings.base.BooleanSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class AnimationBooleanWidget extends ButtonWidget {

    private final AnimationSettings.AnimationBoolSetting setting;

    public AnimationBooleanWidget(int x, int y, AnimationSettings.AnimationBoolSetting setting) {
        super(x, y, 150, 20, setting.value() ? Text.translatable("mflp.true").formatted(Formatting.GREEN)
                : Text.translatable("mflp.false").formatted(Formatting.RED), (button) -> {
            setting.setValue(!setting.value());
        }, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.setting = setting;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        this.setMessage(setting.value() ? Text.translatable("mflp.true").formatted(Formatting.GREEN)
                : Text.translatable("mflp.false").formatted(Formatting.RED));
        super.renderWidget(context, mouseX, mouseY, delta);
    }

}
