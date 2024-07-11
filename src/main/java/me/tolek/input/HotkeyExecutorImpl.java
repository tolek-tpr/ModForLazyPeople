package me.tolek.input;

import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.event.KeyboardListener;
import me.tolek.event.MouseListener;

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
        HotkeyManager.getInstance().getHotkeys().forEach(e -> e.setPressed(true));
    }

    @Override
    public void onMouseClick(int x, int y, int button, int action) {

    }

    @Override
    public void onMouseMove(int x, int y) {}

    @Override
    public void onMouseScroll(int x, int y) {}
}
