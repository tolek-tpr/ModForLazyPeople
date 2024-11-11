package me.tolek.mixin.client;

import me.tolek.event.EventManager;
import me.tolek.events.WorldLoadHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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

    @Shadow
    public ClientWorld world;

    @Unique
    private ClientWorld worldBefore;

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

    @Inject(method = "joinWorld(Lnet/minecraft/client/world/ClientWorld;)V", at = @At("HEAD"))
    private void onLoadWorldPre(@Nullable ClientWorld worldClientIn, CallbackInfo ci)
    {
        // Only handle dimension changes/respawns here.
        // The initial join is handled in MixinClientPlayNetworkHandler onGameJoin
        if (this.world != null)
        {
            this.worldBefore = this.world;
            ((WorldLoadHandler) WorldLoadHandler.getInstance()).onWorldLoadPre(this.world, worldClientIn, (MinecraftClient)(Object) this);
        }
    }

    @Inject(method = "joinWorld(Lnet/minecraft/client/world/ClientWorld;)V", at = @At("RETURN"))
    private void onLoadWorldPost(@Nullable ClientWorld worldClientIn, CallbackInfo ci)
    {
        if (this.worldBefore != null)
        {
            ((WorldLoadHandler) WorldLoadHandler.getInstance()).onWorldLoadPost(this.worldBefore, worldClientIn, (MinecraftClient)(Object) this);
            this.worldBefore = null;
        }
    }

    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("HEAD"))
    private void onDisconnectPre(Screen screen, CallbackInfo ci)
    {
        this.worldBefore = this.world;
        ((WorldLoadHandler) WorldLoadHandler.getInstance()).onWorldLoadPre(this.worldBefore, null, (MinecraftClient)(Object) this);
    }

    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("RETURN"))
    private void onDisconnectPost(Screen screen, CallbackInfo ci)
    {
        ((WorldLoadHandler) WorldLoadHandler.getInstance()).onWorldLoadPost(this.worldBefore, null, (MinecraftClient)(Object) this);
        this.worldBefore = null;
    }
}
