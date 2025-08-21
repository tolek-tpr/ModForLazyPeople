package me.tolek.event;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public interface HudRenderListener extends Listener {

    void onRender(DrawContext context, RenderTickCounter partialTicks);

    public static class HudRenderEvent extends Event<HudRenderListener> {
        private final DrawContext context;
        private final RenderTickCounter partialTicks;

        public HudRenderEvent(DrawContext context, RenderTickCounter partialTicks) {
            this.context = context;
            this.partialTicks = partialTicks;
        }

        @Override
        public void fire(ArrayList<HudRenderListener> listeners) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);

            for(HudRenderListener listener : listeners)
                listener.onRender(context, partialTicks);

            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }

        @Override
        public Class<HudRenderListener> getListenerType()
        {
            return HudRenderListener.class;
        }
    }



}
