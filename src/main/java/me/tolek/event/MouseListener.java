package me.tolek.event;

import java.util.ArrayList;

public interface MouseListener extends Listener {

    void onMouseMove(int x, int y);
    void onMouseScroll(int x, int y);
    void onMouseClick(int x, int y, int button, int action);

    public static class MouseMoveEvent extends Event<MouseListener> {

        private final int x;
        private final int y;

        public MouseMoveEvent(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void fire(ArrayList<MouseListener> listeners) {
            for (MouseListener listener : listeners)
                listener.onMouseMove(this.x, this.y);
        }

        @Override
        public Class<MouseListener> getListenerType() {
            return MouseListener.class;
        }
    }

    public static class MouseScrollEvent extends Event<MouseListener> {

        private final int x;
        private final int y;

        public MouseScrollEvent(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void fire(ArrayList<MouseListener> listeners) {
            for (MouseListener listener : listeners)
                listener.onMouseScroll(this.x, this.y);
        }

        @Override
        public Class<MouseListener> getListenerType() {
            return MouseListener.class;
        }
    }

    public static class MouseClickEvent extends Event<MouseListener> {

        private final int x;
        private final int y;
        private final int button;
        private final int action;

        public MouseClickEvent(int x, int y, int button, int action) {
            this.x = x;
            this.y = y;
            this.button = button;
            this.action = action;
        }

        @Override
        public void fire(ArrayList<MouseListener> listeners) {
            for (MouseListener listener : listeners)
                listener.onMouseClick(this.x, this.y, this.button, this.action);
        }

        @Override
        public Class<MouseListener> getListenerType() {
            return MouseListener.class;
        }
    }

}
