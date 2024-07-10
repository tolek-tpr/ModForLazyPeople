package me.tolek.mixin.client;

import me.tolek.interfaces.TimerInterface;
import me.tolek.util.MflpScheduler;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;
import java.util.function.Consumer;

@Mixin(MinecraftClient.class)
public class MflpClientScheduler implements TimerInterface {


    @Unique
    private Map<UUID, MflpScheduler> schedulers = new HashMap<>();
    private List<UUID> uuids = new ArrayList<>();

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        List<UUID> toRemove = new ArrayList<>();

        for (UUID uuid : uuids) {
            if (schedulers.get(uuid) == null) continue;
            schedulers.get(uuid).decrease();
            if (schedulers.get(uuid).getTime() == 0 && !schedulers.get(uuid).getRerun()) {
                schedulers.remove(uuid);
                toRemove.add(uuid);
            }

        }

        toRemove.forEach(uuids::remove);
    }

    @Override
    public UUID scheduleRepeating(int time, Consumer<Boolean> executor) {
        UUID uuid = UUID.randomUUID();
        schedulers.put(uuid, new MflpScheduler(time, true, executor));
        uuids.add(uuid);
        return uuid;
    }

    @Override
    public UUID scheduleNonRepeating(int time, Consumer<Boolean> executor) {
        UUID uuid = UUID.randomUUID();
        schedulers.put(uuid, new MflpScheduler(time, false, executor));
        uuids.add(uuid);
        return uuid;
    }
}
