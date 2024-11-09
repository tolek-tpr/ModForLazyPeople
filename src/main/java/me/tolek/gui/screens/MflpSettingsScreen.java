package me.tolek.gui.screens;

import me.tolek.gui.widgets.*;
import me.tolek.gui.widgets.settingsWidgets.*;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.modules.settings.base.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class MflpSettingsScreen extends Screen {

    public MflpSettingsScreen() {
        super(Text.translatable("mflp.settingsScreen.title"));
    }

    private final MflpSettingsList settingsList = MflpSettingsList.getInstance();
    private ScrollableListWidget slw;

    @Override
    public void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            assert client != null;
            client.setScreen((Screen) null);
        }).dimensions(width / 2 - 75, height - 29, 150, 20).build());

        MenuPickerWidget mpw = new MenuPickerWidget(10, 22, client);
        mpw.children().forEach(this::addDrawableChild);

        slw = new ScrollableListWidget(this.client, width, height - 84, 44, 22);
        slw.setRenderBackground(false);

        for (MflpSetting setting : settingsList.getSettings()) {
            if (!setting.render) continue;
            // BOOLEAN SETTING
            if (setting instanceof BooleanSetting bs) {
                BooleanWidget bw = new BooleanWidget(width / 2, 0, bs);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(Text.translatable(setting.getName())) + 10, 20, Text.translatable(setting.getName()), textRenderer);
                slw.addRow(label, bw);
            }

            // FLOAT SETTING
            if (setting instanceof FloatSetting fs) {
                FloatWidget fw = new FloatWidget(width / 2, 0, fs, textRenderer);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(Text.translatable(setting.getName())) + 10, 20 , Text.translatable(setting.getName()), textRenderer);
                slw.addRow(label, fw);
            }

            // INT SETTING
            if (setting instanceof IntegerSetting is) {
                IntegerWidget iw = new IntegerWidget(width / 2, 0, is, textRenderer);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(Text.translatable(setting.getName())) + 10, 20, Text.translatable(setting.getName()), textRenderer);
                slw.addRow(label, iw);
            }

            // STRING SETTING
            if (setting instanceof StringSetting ss) {
                StringWidget sw = new StringWidget(width / 2, 0, ss, textRenderer);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(Text.translatable(setting.getName())) + 10, 20, Text.translatable(setting.getName()), textRenderer);
                slw.addRow(label, sw);
            }

            // LIST SETTING
            if (setting instanceof ListSetting ls) {
                ListWidget lw = new ListWidget(width / 2, 0, ls);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(Text.translatable(setting.getName())) + 10, 20, Text.translatable(setting.getName()), textRenderer);
                slw.addRow(label, lw);
            }

            if (setting instanceof ButtonSetting bs) {
                ButtonSettingWidget bw = new ButtonSettingWidget(width / 2, 0, bs);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(Text.translatable(setting.getName())) + 10, 20, Text.translatable(setting.getName()), textRenderer);
                slw.addRow(label, bw);
            }
        }
        addDrawableChild(slw);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

}
