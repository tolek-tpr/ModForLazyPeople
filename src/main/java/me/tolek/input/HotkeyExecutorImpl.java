package me.tolek.input;

import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.event.KeyboardListener;
import me.tolek.event.MouseListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class HotkeyExecutorImpl extends EventImpl implements MouseListener, KeyboardListener {

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
        HotkeyManager.getInstance().getHotkeys().forEach(e ->
                e.setKeyState(keyCode, InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), keyCode)));
    }


    @Override
    public void onMouseClick(int x, int y, int button, int action) {
        int a = GLFW.glfwGetMouseButton(MinecraftClient.getInstance().getWindow().getHandle(), button);
        boolean b = a == 1;

        HotkeyManager.getInstance().getHotkeys().forEach(e -> {
            e.setKeyState(button, b);
        });
    }

    @Override
    public void onMouseMove(int x, int y) {}

    @Override
    public void onMouseScroll(int x, int y) {}
}
