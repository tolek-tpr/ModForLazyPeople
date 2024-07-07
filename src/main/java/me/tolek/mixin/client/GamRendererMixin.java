package me.tolek.mixin.client;


import me.tolek.event.EventManager;
import me.tolek.interfaces.RenderListener;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.tolek.interfaces.RenderListener.RenderEvent;

@Mixin(GameRenderer.class)
public abstract class GamRendererMixin implements AutoCloseable {

    @Inject(
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z",
                    opcode = Opcodes.GETFIELD,
                    ordinal = 0),
            method = "renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V")
    private void onRenderWorld(float tickDelta, long limitTime,
                               MatrixStack matrices, CallbackInfo ci)
    {
        RenderEvent event = new RenderEvent(matrices, tickDelta);
        EventManager.getInstance().fire(event);
    }
}
