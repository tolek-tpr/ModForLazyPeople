package me.tolek.mixin.client;

import me.tolek.ModForLazyPeople;
import me.tolek.event.EventManager;
import me.tolek.interfaces.IScheduler;
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
import java.util.UUID;

import static me.tolek.event.UpdateListener.UpdateEvent;
import static me.tolek.event.MinecraftQuitListener.MinecraftQuitEvent;
import static me.tolek.event.MinecraftStartListener.MinecraftStartEvent;
import static me.tolek.event.MinecraftStartListener.MinecraftStartFinishedEvent;
import static me.tolek.network.MflpPlayersWorker.sendInfoToServer;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo ci) {
        EventManager.getInstance().fire(UpdateEvent.INSTANCE);
    }

    @Inject(at = @At("HEAD"), method = "scheduleStop")
    private void scheduleStop(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        MflpServerConnection mflpServer = ModForLazyPeople.serverConnection;
        MinecraftQuitEvent event = new MinecraftQuitEvent();
        EventManager.getInstance().fire(event);

        // put
        try {
            mflpServer.sendDeleteRequest("/api/mflp", "{\"username\": \"" +
                    client.getSession().getUsername() + "\"}");
        } catch (Exception e) {
            ModForLazyPeople.LOGGER.info("Failed to send delete request");
        }
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

        // Deprecated
        /*
        MinecraftClient client = MinecraftClient.getInstance();
        MflpServerConnection mflpServer = ModForLazyPeople.serverConnection;
        // If true then try to send again.
        sendInfoToServer();

        try {
            MflpPlayersWorker.getInstance().data = mflpServer.sendGetRequest("/api/mflp");
        } catch (Exception e) {
            ModForLazyPeople.LOGGER.info("Failed to connect to server! Make sure you're connected to the internet and the MFLP " +
                    "server is up at epsi.ddns.net:3000!");
        }*/
    }
}
