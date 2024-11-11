package me.tolek.event;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public interface RenderListener extends Listener {

    void onRender(DrawContext context, RenderTickCounter partialTicks);

    public static class RenderEvent extends Event<RenderListener> {
        private final DrawContext context;
        private final RenderTickCounter partialTicks;

        public RenderEvent(DrawContext context, RenderTickCounter partialTicks) {
            this.context = context;
            this.partialTicks = partialTicks;
        }

        @Override
        public void fire(ArrayList<RenderListener> listeners) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);

            for(RenderListener listener : listeners)
                listener.onRender(context, partialTicks);

            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }

        @Override
        public Class<RenderListener> getListenerType()
        {
            return RenderListener.class;
        }
    }



}
