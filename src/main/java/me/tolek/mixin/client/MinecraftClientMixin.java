package me.tolek.mixin.client;

import me.tolek.ModForLazyPeople;
import me.tolek.event.EventManager;
import me.tolek.network.MflpPlayersWorker;
import me.tolek.network.MflpServerConnection;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;

import static me.tolek.event.UpdateListener.UpdateEvent;
import static me.tolek.event.MinecraftQuitListener.MinecraftQuitEvent;
import static me.tolek.event.MinecraftStartListener.MinecraftStartEvent;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo ci) {
        EventManager.getInstance().fire(UpdateEvent.INSTANCE);
    }

    @Inject(at = @At("HEAD"), method = "scheduleStop")
    private void scheduleStop(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        MflpServerConnection mflpServer = new MflpServerConnection();
        MinecraftQuitEvent event = new MinecraftQuitEvent();
        EventManager.getInstance().fire(event);

        // put
        try {
            mflpServer.sendDeleteRequest("/api/mflp", "{\"username\": \"" +
                    client.getSession().getUsername() + "\"}");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Inject(at = @At("HEAD"), method = "run")
    private void run(CallbackInfo ci) {
        MinecraftStartEvent event = new MinecraftStartEvent();
        EventManager.getInstance().fire(event);
    }

    @Inject(at = @At("TAIL"), method = "onInitFinished")
    private void onInitFinished(@Coerce Object loadingContext, CallbackInfoReturnable<Runnable> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        MflpServerConnection mflpServer = new MflpServerConnection();
        // put
        try {
            if (client.getSession() == null) return;
            mflpServer.sendPostRequest("/api/mflp", "{\"username\": \"" +
                    client.getSession().getUsername() + "\"}");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            MflpPlayersWorker.getInstance().data = mflpServer.sendGetRequest("/api/mflp");
        } catch (Exception e) {
            ModForLazyPeople.LOGGER.info("Failed to connect to server! Make sure you're connected to the internet and the MFLP " +
                    "server is up at epsi.ddns.net:3000!");
        }
    }

}
