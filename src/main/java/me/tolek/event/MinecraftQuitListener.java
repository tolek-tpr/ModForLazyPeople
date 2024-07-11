package me.tolek.event;

import java.util.ArrayList;

public interface MinecraftQuitListener extends Listener {

    void onQuit();

    public static class MinecraftQuitEvent extends Event<MinecraftQuitListener> {
        public MinecraftQuitEvent() {}

        @Override
        public void fire(ArrayList<MinecraftQuitListener> listeners) {
            for(MinecraftQuitListener listener : listeners)
                listener.onQuit();
        }

        @Override
        public Class<MinecraftQuitListener> getListenerType()
        {
            return MinecraftQuitListener.class;
        }
    }

}
