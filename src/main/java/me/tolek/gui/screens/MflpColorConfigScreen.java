package me.tolek.gui.screens;

import me.tolek.gui.widgets.MenuPickerWidget;
import me.tolek.gui.widgets.ScrollableListWidget;
import me.tolek.gui.widgets.colors.ColorInputWidget;
import me.tolek.gui.widgets.settingsWidgets.*;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.modules.settings.base.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class MflpColorConfigScreen extends Screen {

    public MflpColorConfigScreen() {
        super(Text.translatable("mflp.mainConfig.colorsScreen"));
    }

    private final MflpSettingsList settingsList = MflpSettingsList.getInstance();

    @Override
    public void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            client.setScreen((Screen) null);
        }).dimensions(width / 2 - 75, height - 29, 150, 20).build());

        MenuPickerWidget mpw = new MenuPickerWidget(10, 22, client);
        mpw.children().forEach(this::addDrawableChild);

        ScrollableListWidget slw = new ScrollableListWidget(this.client, width, height - 84, 44, 22);
        slw.setRenderBackground(false);

        for (MflpSetting setting : settingsList.getSettings()) {
            if (!setting.render) continue;

            // COLOR SETTING
            if (setting instanceof ColorSetting cs) {
                ColorInputWidget colorInputWidget = new ColorInputWidget(Text.literal(cs.getFormattedColor()), width / 2, 0, cs);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(Text.translatable(setting.getName())) + 10, 20, Text.translatable(setting.getName()), textRenderer);
                label.setTooltip(Tooltip.of(Text.translatable(cs.getTooltip())));
                ButtonWidget resetButton = ButtonWidget.builder(Text.translatable("mflp.setting.resetButton.name"), (button) -> {
                    cs.setColor(String.valueOf(cs.getDefaultValue()));
                    colorInputWidget.update();
                }).dimensions(width / 2 + 77, 0, 50, 20).tooltip(Tooltip.of(Text.translatable("mflp.setting.resetButton.tooltip")))
                        .build();
                slw.addRow(label, colorInputWidget, resetButton);
            }
        }
        addDrawableChild(slw);
    }

}
