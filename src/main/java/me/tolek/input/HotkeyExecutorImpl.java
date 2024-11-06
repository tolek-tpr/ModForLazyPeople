package me.tolek.input;

import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.event.KeyboardListener;
import me.tolek.event.MouseListener;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.modules.settings.base.BooleanSetting;
import me.tolek.util.MflpUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class HotkeyExecutorImpl extends EventImpl implements MouseListener, KeyboardListener {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private final MflpSettingsList settingsList = MflpSettingsList.getInstance();

    private final ArrayList<Integer> pressedKeys = new ArrayList<>();

    @Override
    public void onEnable() {
        EventManager.getInstance().add(MouseListener.class, this);
        EventManager.getInstance().add(KeyboardListener.class, this);
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(MouseListener.class, this);
        EventManager.getInstance().remove(KeyboardListener.class, this);
    }


    @Override
    public void onKey(int keyCode, int scanCode, int modifiers) {
        if (client.world == null) return;

        final Window window = client.getWindow();
        final long windowHandle = window.getHandle();

        if (GLFW.glfwGetKey(windowHandle, keyCode) == 1) {
            // Pressed
            if (!this.pressedKeys.contains(keyCode))
                pressedKeys.add(keyCode);

            settingsList.getSettings().forEach(setting -> {
                if (setting instanceof BooleanSetting boolSetting && boolSetting.hotkey != null) {
                    if (MflpUtil.listEqualsIgnoreOrder(boolSetting.getHotkey().getKeys(), this.pressedKeys)) {
                        //boolSetting.hotkey.setPressed(true);
                        if (boolSetting.getState()) {
                            boolSetting.setState(false);
                            boolSetting.notifyPressed();
                        } else {
                            boolSetting.setState(true);
                            boolSetting.notifyPressed();
                        }
                    }
                }
            });
        } else {
            // Unpressed
            pressedKeys.remove((Integer) keyCode);
        }
    }

    @Override
    public void onMouseClick(int x, int y, int button, int action) {

    }

    @Override
    public void onMouseMove(int x, int y) {}

    @Override
    public void onMouseScroll(int x, int y) {}
}
