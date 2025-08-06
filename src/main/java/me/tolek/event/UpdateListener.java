package me.tolek.event;

import java.util.ArrayList;

public interface UpdateListener extends Listener {

    public void onUpdate();
    default public void onEndTick() {}

    public static class UpdateEvent extends Event<UpdateListener> {
        public static final UpdateEvent INSTANCE = new UpdateEvent();

        @Override
        public void fire(ArrayList<UpdateListener> listeners)
        {
            for(UpdateListener listener : listeners)
                listener.onUpdate();
        }

        @Override
        public Class<UpdateListener> getListenerType()
        {
            return UpdateListener.class;
        }
    }

    public static class EndTickEvent extends Event<UpdateListener> {

        @Override
        public void fire(ArrayList<UpdateListener> listeners)
        {
            for(UpdateListener listener : listeners)
                listener.onEndTick();
        }

        @Override
        public Class<UpdateListener> getListenerType()
        {
            return UpdateListener.class;
        }

    }

}
