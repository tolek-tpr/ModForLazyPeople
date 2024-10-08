package me.tolek.event;

import java.util.ArrayList;

public interface MinecraftStartListener extends Listener {

    void onStart();
    void onStartFinished();

    public static class MinecraftStartEvent extends Event<MinecraftStartListener> {
        public MinecraftStartEvent() {}

        @Override
        public void fire(ArrayList<MinecraftStartListener> listeners) {
            for(MinecraftStartListener listener : listeners)
                listener.onStart();
        }

        @Override
        public Class<MinecraftStartListener> getListenerType()
        {
            return MinecraftStartListener.class;
        }
    }

    public static class MinecraftStartFinishedEvent extends Event<MinecraftStartListener> {
        public MinecraftStartFinishedEvent() {}

        @Override
        public void fire(ArrayList<MinecraftStartListener> listeners) {
            for(MinecraftStartListener listener : listeners)
                listener.onStartFinished();
        }

        @Override
        public Class<MinecraftStartListener> getListenerType()
        {
            return MinecraftStartListener.class;
        }
    }

}
