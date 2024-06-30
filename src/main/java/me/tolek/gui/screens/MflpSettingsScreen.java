package me.tolek.gui.screens;

import me.tolek.gui.widgets.*;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.modules.settings.base.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.Widget;
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

        //ElementListWidget elementListWidget = new ElementListWidget
        ScrollableListWidget slw = new ScrollableListWidget(this.client, 310, height - 82, 42, 22);

        int step = 2;
        for (MflpSetting setting : settingsList.getSettings()) {
            // BOOLEAN SETTING
            if (setting instanceof BooleanSetting) {
                BooleanSetting bs = (BooleanSetting) setting;

                BooleanSettingWidgetContainer bswc = new BooleanSettingWidgetContainer(width / 2, 42 + step,
                        Text.literal(bs.getName()), bs, textRenderer, client);
                addDrawableChild(bswc);
                addDrawableChild(bswc.bsw);
                slw.children().add(bswc);
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
                slw.children().add(iswc);
            }

            // STRING SETTING
            if (setting instanceof StringSetting) {
                StringSetting ss = (StringSetting) setting;

                StringSettingWidgetContainer sswc = new StringSettingWidgetContainer(width / 2, 42 + step,
                        Text.literal(ss.getName()), ss, textRenderer, client);
                addDrawableChild(sswc);
                addDrawableChild(sswc.ssw);
                slw.children().add(sswc);
            }

            // LIST SETTING
            if (setting instanceof ListSetting) {
                ListSetting ls = (ListSetting) setting;

                ListSettingWidgetContainer lswc = new ListSettingWidgetContainer(width / 2, 42 + step,
                        Text.literal(ls.getName()), ls, textRenderer, client);
                addDrawableChild(lswc);
                addDrawableChild(lswc.lsw);
                slw.children().add(lswc);
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
