package me.tolek.gui.screens;

import me.tolek.gui.widgets.*;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.modules.settings.base.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class MflpSettingsScreen extends Screen {

    public MflpSettingsScreen() {
        super(Text.translatable("mflp.settingsScreen.title"));
    }

    private final MflpSettingsList settingsList = MflpSettingsList.getInstance();

    @Override
    public void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            client.setScreen((Screen) null);
        }).dimensions(width / 2 - 75, height - 29, 150, 20).build());

        MenuPickerWidget mpw = new MenuPickerWidget(10, 22, client);
        mpw.children().forEach(this::addDrawableChild);

        int step = 2;
        for (MflpSetting setting : settingsList.getSettings()) {
            // BOOLEAN SETTING
            if (setting instanceof BooleanSetting) {
                BooleanSetting bs = (BooleanSetting) setting;

                BooleanSettingWidgetContainer bswc = new BooleanSettingWidgetContainer(width / 2, 42 + step,
                        Text.literal(bs.getName()), bs, textRenderer, client);
                addDrawableChild(bswc);
                addDrawableChild(bswc.bsw);
            }

            // FLOAT SETTING (not implemented)
            if (setting instanceof FloatSetting) {
                FloatSetting fs = (FloatSetting) setting;
            }

            // INT SETTING
            if (setting instanceof IntegerSetting) {
                IntegerSetting is = (IntegerSetting) setting;

                IntegerSettingWidgetContainer iswc = new IntegerSettingWidgetContainer(width / 2, 42 + step,
                        Text.literal(is.getName()), is, textRenderer, client);
                addDrawableChild(iswc);
                addDrawableChild(iswc.isw);
            }

            // STRING SETTING
            if (setting instanceof StringSetting) {
                StringSetting ss = (StringSetting) setting;

                StringSettingWidgetContainer sswc = new StringSettingWidgetContainer(width / 2, 42 + step,
                        Text.literal(ss.getName()), ss, textRenderer, client);
                addDrawableChild(sswc);
                addDrawableChild(sswc.ssw);
            }

            step += 22;
        }
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
