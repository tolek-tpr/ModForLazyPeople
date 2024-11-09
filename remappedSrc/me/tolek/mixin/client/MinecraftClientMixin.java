package me.tolek.mixin.client;

import me.tolek.ModForLazyPeople;
import me.tolek.event.EventManager;
import me.tolek.event.MinecraftQuitListener.MinecraftQuitEvent;
import me.tolek.event.MinecraftStartListener.MinecraftStartEvent;
import me.tolek.event.MinecraftStartListener.MinecraftStartFinishedEvent;
import me.tolek.event.UpdateListener.UpdateEvent;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.tolek.event.UpdateListener.UpdateEvent;
import static me.tolek.event.MinecraftQuitListener.MinecraftQuitEvent;
import static me.tolek.event.MinecraftStartListener.MinecraftStartEvent;
import static me.tolek.event.MinecraftStartListener.MinecraftStartFinishedEvent;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo ci) {
        EventManager.getInstance().fire(UpdateEvent.INSTANCE);
    }

    @Inject(at = @At("HEAD"), method = "scheduleStop")
    private void scheduleStop(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        MinecraftQuitEvent event = new MinecraftQuitEvent();
        EventManager.getInstance().fire(event);
    }

    @Inject(at = @At("HEAD"), method = "run")
    private void run(CallbackInfo ci) {
        MinecraftStartEvent event = new MinecraftStartEvent();
        EventManager.getInstance().fire(event);
    }

    @Inject(at = @At("TAIL"), method = "onInitFinished")
    private void onInitFinished(@Coerce Object loadingContext, CallbackInfoReturnable<Runnable> cir) {
        MinecraftStartFinishedEvent event = new MinecraftStartFinishedEvent();
        EventManager.getInstance().fire(event);
    }
}
