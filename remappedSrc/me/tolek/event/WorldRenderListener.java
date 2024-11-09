package me.tolek.event;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.ArrayList;

public interface WorldRenderListener extends Listener {

    void onRender(WorldRenderContext context);

    public static class WorldRenderEvent extends Event<WorldRenderListener> {
        private final WorldRenderContext context;

        public WorldRenderEvent(WorldRenderContext context) {
            this.context = context;
        }

        @Override
        public void fire(ArrayList<WorldRenderListener> listeners) {
            for(WorldRenderListener listener : listeners)
                listener.onRender(context);
        }

        @Override
        public Class<WorldRenderListener> getListenerType()
        {
            return WorldRenderListener.class;
        }
    }

}
