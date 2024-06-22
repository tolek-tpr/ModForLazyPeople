package me.tolek.gui.screens;

import me.tolek.Macro.Macro;
import me.tolek.gui.widgets.InputBoxWidget;
import me.tolek.settings.MflpSettingsList;
import me.tolek.settings.base.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.InputUtil;
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
                })).dimensions(width / 2 - 155 + 160, 20 + step, 150, 20).build();

                addDrawableChild(toggleButton);
            }

            // FLOAT SETTING (not implemented)
            if (setting instanceof FloatSetting) {
                FloatSetting fs = (FloatSetting) setting;
            }

            // INT SETTING
            if (setting instanceof IntegerSetting) {
                IntegerSetting is = (IntegerSetting) setting;

                TextFieldWidget textFieldWidget = new TextFieldWidget(textRenderer, width / 2 - 155 + 160, 20 + step, 150, 20, Text.literal("" + is.getState()));
                textFieldWidget.setText("" + is.getState());
                textFieldWidget.setMaxLength(Integer.MAX_VALUE);
                textFieldWidget.setChangedListener((state) -> {
                    if (is.validateInt(state)) {
                        textFieldWidget.setEditableColor(14737632);
                        is.setState(Integer.parseInt(state));
                    } else {
                        textFieldWidget.setEditableColor(16711680);
                    }
                });

                addDrawableChild(textFieldWidget);
            }

            // STRING SETTING
            if (setting instanceof StringSetting) {
                StringSetting ss = (StringSetting) setting;

                TextFieldWidget textFieldWidget = new TextFieldWidget(textRenderer, width / 2 - 155 + 160, 20 + step, 150, 20, Text.literal(ss.getState()));
                textFieldWidget.setText(ss.getState());
                textFieldWidget.setMaxLength(Integer.MAX_VALUE);
                textFieldWidget.setChangedListener((state) -> {
                    if (ss.validateString(state)) {
                        textFieldWidget.setEditableColor(14737632);
                        ss.setState(state);
                    } else {
                        textFieldWidget.setEditableColor(16711680);
                    }
                });

                addDrawableChild(textFieldWidget);
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
