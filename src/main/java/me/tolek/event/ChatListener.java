package me.tolek.event;

import net.minecraft.text.Text;

import java.util.ArrayList;

public interface ChatListener extends Listener {

    void onMessageAdd(Text message);

    public static class ChatModifyEvent extends Event<ChatListener> {
        private final Text message;

        public ChatModifyEvent(Text message) {
            this.message = message;
        }

        @Override
        public void fire(ArrayList<ChatListener> listeners) {
            for(ChatListener listener : listeners)
                listener.onMessageAdd(message);
        }

        @Override
        public Class<ChatListener> getListenerType()
        {
            return ChatListener.class;
        }
    }

}
