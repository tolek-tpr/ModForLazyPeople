package me.tolek.gui.screens;

import me.tolek.gui.widgets.*;
import me.tolek.gui.widgets.settingsWidgets.*;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.modules.settings.base.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class MflpSettingsScreen extends Screen {

    public MflpSettingsScreen() {
        super(Text.translatable("mflp.settingsScreen.title"));
    }

    private final MflpSettingsList settingsList = MflpSettingsList.getInstance();
    private ScrollableListWidget slw;

    @Override
    public void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            client.setScreen((Screen) null);
        }).dimensions(width / 2 - 75, height - 29, 150, 20).build());

        MenuPickerWidget mpw = new MenuPickerWidget(10, 22, client);
        mpw.children().forEach(this::addDrawableChild);

        slw = new ScrollableListWidget(this.client, width, height - 84, 44, 22);
        slw.setRenderBackground(false);

        for (MflpSetting setting : settingsList.getSettings()) {
            // BOOLEAN SETTING
            if (setting instanceof BooleanSetting) {
                BooleanSetting bs = (BooleanSetting) setting;

                BooleanWidget bw = new BooleanWidget(width / 2, 0, bs);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(setting.getName()) + 10, 20, Text.literal(setting.getName()), textRenderer);
                slw.addRow(label, bw);
            }

            // FLOAT SETTING (not implemented)
            if (setting instanceof FloatSetting) {
                FloatSetting fs = (FloatSetting) setting;
            }

            // INT SETTING
            if (setting instanceof IntegerSetting) {
                IntegerSetting is = (IntegerSetting) setting;

                IntegerWidget iw = new IntegerWidget(width / 2, 0, is, textRenderer);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(setting.getName()) + 10, 20, Text.literal(setting.getName()), textRenderer);
                slw.addRow(label, iw);
            }

            // STRING SETTING
            if (setting instanceof StringSetting) {
                StringSetting ss = (StringSetting) setting;

                StringWidget sw = new StringWidget(width / 2, 0, ss, textRenderer);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(setting.getName()) + 10, 20, Text.literal(setting.getName()), textRenderer);
                slw.addRow(label, sw);
            }

            // LIST SETTING
            if (setting instanceof ListSetting) {
                ListSetting ls = (ListSetting) setting;

                ListWidget lw = new ListWidget(width / 2, 0, ls);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(setting.getName()) + 10, 20, Text.literal(setting.getName()), textRenderer);
                slw.addRow(label, lw);
            }

            if (setting instanceof ButtonSetting) {
                ButtonSetting bs = (ButtonSetting) setting;

                ButtonSettingWidget bw = new ButtonSettingWidget(width / 2, 0, bs);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(setting.getName()) + 10, 20, Text.literal(setting.getName()), textRenderer);
                slw.addRow(label, bw);
            }
        }
        addDrawableChild(slw);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        client.setScreen(null);
    }

}
