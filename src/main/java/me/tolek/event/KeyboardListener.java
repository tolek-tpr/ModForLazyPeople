package me.tolek.event;

import java.util.ArrayList;

public interface KeyboardListener extends Listener {

    void onKey(int keyCode, int scanCode, int modifiers);

    public static class KeyEvent extends Event<KeyboardListener> {
        private final int keyCode;
        private final int scanCode;
        private final int modifiers;

        public KeyEvent(int keyCode, int scanCode, int modifiers) {
            this.keyCode = keyCode;
            this.scanCode = scanCode;
            this.modifiers = modifiers;
        }

        @Override
        public void fire(ArrayList<KeyboardListener> listeners) {
            for(KeyboardListener listener : listeners)
                listener.onKey(keyCode, scanCode, modifiers);
        }

        @Override
        public Class<KeyboardListener> getListenerType()
        {
            return KeyboardListener.class;
        }
    }

}
