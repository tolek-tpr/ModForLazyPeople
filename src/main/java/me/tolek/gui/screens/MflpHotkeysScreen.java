package me.tolek.gui.screens;

import me.tolek.gui.widgets.MenuPickerWidget;
import me.tolek.gui.widgets.ScrollableListWidget;
import me.tolek.gui.widgets.hotkeys.HotkeySettingWidget;
import me.tolek.gui.widgets.settingsWidgets.*;
import me.tolek.input.Hotkey;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.modules.settings.base.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class MflpHotkeysScreen extends Screen {

    @Nullable
    public Hotkey selectedHotkey;

    private final ArrayList<HotkeySettingWidget> settingWidgets = new ArrayList<>();

    public MflpHotkeysScreen() {
        super(Text.translatable("mflp.hotkeysScreen.title"));
    }

    @Override
    public void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            client.setScreen((Screen) null);
        }).dimensions(width / 2 - 75, height - 29, 150, 20).build());

        MenuPickerWidget mpw = new MenuPickerWidget(10, 22, client);
        mpw.children().forEach(this::addDrawableChild);

        ScrollableListWidget slw = new ScrollableListWidget(this.client, width, height - 84, 44, 22);
        slw.setRenderBackground(false);

        MflpSettingsList settingsList = MflpSettingsList.getInstance();
        for (MflpSetting setting : settingsList.getSettings()) {
            if (!setting.render) continue;

            if (setting instanceof HotkeyableSetting hs) {
                if (hs.getHotkey() == null) continue;
                HotkeySettingWidget settingWidget = new HotkeySettingWidget(width / 2, 0, hs.getHotkey().getFormattedKeys(), hs,
                        this.selectedHotkey);
                settingWidget.setHotkeyConsumer(hotkey -> this.selectedHotkey = hotkey);
                TextWidget label = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, textRenderer.getWidth(Text.translatable(setting.getName())) + 10, 20, Text.translatable(setting.getName()), textRenderer);
                slw.addRow(label, settingWidget);
                this.settingWidgets.add(settingWidget);
            }
        }
        addDrawableChild(slw);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && this.selectedHotkey != null) {
            this.settingWidgets.forEach(w -> w.keyPressed(keyCode, scanCode, modifiers));
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.selectedHotkey != null) {
            this.settingWidgets.forEach(w -> {
                if (!(mouseX > w.getX() && mouseX < (w.getX() + w.getWidth()) && mouseY > w.getY() && mouseY < (w.getY() + w.getHeight()))) {
                    if (w.getSelectedHotkey() != null) {
                        this.selectedHotkey = null;
                        w.setSelectedHotkey(null);
                        w.update();
                    }
                }
            });
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void close() {
        if (this.selectedHotkey != null) return;
        client.setScreen(null);
    }

}
