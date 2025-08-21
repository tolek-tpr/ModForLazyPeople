package me.tolek.gui.screens;

import me.tolek.gui.widgets.DoubleSliderWidget;
import me.tolek.gui.widgets.IntSliderWidget;
import me.tolek.gui.widgets.ScrollableListWidget;
import me.tolek.gui.widgets.animations.AnimationBooleanWidget;
import me.tolek.modules.settings.AnimationSettings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class AnimationsScreen extends Screen {

    public AnimationsScreen() {
        super(Text.literal("mflp.screen.animations.name"));
    }

    @Override
    public void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            assert client != null;
            client.setScreen(new MflpSettingsScreen());
        }).dimensions(width / 2 - 75, height - 29, 150, 20).build());

        ScrollableListWidget slw = new ScrollableListWidget(this.client, width, height - 84, 44, 22);

        for (AnimationSettings.AnimationSetting setting : AnimationSettings.getInstance().getFloatSettings()) {
            if (setting instanceof AnimationSettings.AnimationDoubleSetting ds) {
                DoubleSliderWidget dsw = new DoubleSliderWidget(width / 2 + 5, 0, 150, 20, ds.value(), ds.min(), ds.max())
                        .setCallback(ds::setValue);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(ds.settingName()) + 10, 20, ds.settingName(), textRenderer);
                slw.addRow(label, dsw);
            }
            if (setting instanceof AnimationSettings.AnimationIntSetting is) {
                IntSliderWidget isw = new IntSliderWidget(width / 2 + 5, 0, 150, 20, is.value(), is.min(), is.max())
                        .setCallback(is::setValue);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(is.settingName()) + 10, 20, is.settingName(), textRenderer);
                slw.addRow(label, isw);
            }
            if (setting instanceof AnimationSettings.AnimationBoolSetting bs) {
                AnimationBooleanWidget abw = new AnimationBooleanWidget(width / 2 + 5, 0, bs);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(bs.settingName()) + 10, 20, bs.settingName(), textRenderer);
                slw.addRow(label, abw);
            }
        }

        addDrawableChild(slw);
    }

}
