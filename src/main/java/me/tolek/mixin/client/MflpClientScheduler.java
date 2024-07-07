package me.tolek.mixin.client;

import me.tolek.interfaces.TimerInterface;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(MinecraftClient.class)
public class MflpClientTimer implements TimerInterface {

    //@Unique
    private long ticksUntil;
    //@Unique
    private Consumer<Boolean> executor = (b -> {});

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (--this.ticksUntil == 0L) {
            executor.accept(true);
        }
    }

    @Override
    public void setTimer(long ticksUntil, Consumer<Boolean> executor) {
        this.ticksUntil = ticksUntil;
        this.executor = executor;
    }
}
