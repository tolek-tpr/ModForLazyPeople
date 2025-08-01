package me.tolek.event;

import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public interface RenderListener extends Listener {

    void onRender(MatrixStack matrices, float tickDelta);

    public static class RenderEvent extends Event<RenderListener> {
        private final MatrixStack matrices;
        private final float tickDelta;

        public RenderEvent(MatrixStack matrices, float tickDelta) {
            this.matrices = matrices;
            this.tickDelta = tickDelta;
        }

        @Override
        public void fire(ArrayList<RenderListener> listeners) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);

            for(RenderListener listener : listeners)
                listener.onRender(matrices, tickDelta);

            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }

        @Override
        public Class<RenderListener> getListenerType()
        {
            return RenderListener.class;
        }
    }



}
