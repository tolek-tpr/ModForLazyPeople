package me.tolek.interfaces;

import me.tolek.event.Event;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public interface RenderListener extends Listener {

    void onRender(MatrixStack stack, float partialTicks);

    public static class RenderEvent extends Event<RenderListener> {
        private final MatrixStack matrixStack;
        private final float partialTicks;

        public RenderEvent(MatrixStack matrixStack, float partialTicks)
        {
            this.matrixStack = matrixStack;
            this.partialTicks = partialTicks;
        }

        @Override
        public void fire(ArrayList<RenderListener> listeners)
        {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);

            for(RenderListener listener : listeners)
                listener.onRender(matrixStack, partialTicks);

            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }

        @Override
        public Class<RenderListener> getListenerType()
        {
            return RenderListener.class;
        }
    }



}
