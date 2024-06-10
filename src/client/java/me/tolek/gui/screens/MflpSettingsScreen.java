package me.tolek.gui.screens;

import me.tolek.Macro.Macro;
import me.tolek.settings.MflpSettingsList;
import me.tolek.settings.base.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class MflpSettingsScreen extends Screen {

    public MflpSettingsScreen() {
        super(Text.translatable("mflp.settingsScreen.title"));
    }

    private MflpSettingsList settingsList = MflpSettingsList.getInstance();

    @Override
    public void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            client.setScreen((Screen) null);
        }).dimensions(width / 2 - 75/*+ 160*/, height - 29, 150, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Macros"), (button) -> {
            client.setScreen(new MflpConfig(this.client));
        }).dimensions(width - width + 10, height - height + 22, 70, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Settings"), (button) -> {
            client.setScreen(new MflpSettingsScreen());
        }).dimensions(width - width + 85, height - height + 22, 70, 20).build());

        int step = 2;
        for (MflpSetting setting : settingsList.getSettings()) {
            // BOOLEAN SETTING
            if (setting instanceof BooleanSetting) {
                BooleanSetting bs = (BooleanSetting) setting;

                Text toggleText = bs.getState() ? Text.literal("True").formatted(Formatting.GREEN) :
                        Text.literal("False").formatted(Formatting.RED);

                ButtonWidget toggleButton = ButtonWidget.builder(toggleText, (button -> {
                    bs.run();
                    client.setScreen(new MflpSettingsScreen());
                })).dimensions(width / 2 - 155 + 160, 20 + step, 60, 20).build();

                addDrawableChild(toggleButton);
            }

            // FLOAT SETTING (not implemented)
            if (setting instanceof FloatSetting) {
                FloatSetting fs = (FloatSetting) setting;
            }

            // INT SETTING (not implemented)
            if (setting instanceof IntegerSetting) {
                IntegerSetting is = (IntegerSetting) setting;
            }

            // STRING SETTING (not implemented)
            if (setting instanceof StringSetting) {
                StringSetting is = (StringSetting) setting;
            }

            step += 22;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        int step = 0;
        for (MflpSetting setting : settingsList.getSettings()) {
            Text macroName = Text.of(setting.getName());
            context.drawTextWithShadow(textRenderer, macroName, width / 2 - 155, 24 + step, 0xffffff);
            step += 24;
        }
    }

    @Override
    public void close() {
        client.setScreen(null);
    }

}
