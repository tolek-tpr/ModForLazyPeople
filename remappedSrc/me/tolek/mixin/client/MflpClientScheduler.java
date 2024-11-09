package me.tolek.mixin.client;

import me.tolek.interfaces.IScheduler;
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
public class MflpClientScheduler implements IScheduler {

    // THIS CODE IS FAULTY, WHEN USING IT WITH SOMETHING THAT USES THREADS LIKE HTTP REQUESTS IT WILL LAG THE GAME!!!!
    // FIX ME

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
    @Deprecated
    public UUID scheduleRepeating(int time, Consumer<Boolean> executor) {
        UUID uuid = UUID.randomUUID();
        schedulers.put(uuid, new MflpScheduler(time, true, executor));
        uuids.add(uuid);
        return uuid;
    }

    @Override
    @Deprecated
    public void cancelTask(UUID task) {
        schedulers.remove(task);
        uuids.remove(task);
    }

    @Override
    @Deprecated
    public UUID scheduleNonRepeating(int time, Consumer<Boolean> executor) {
        UUID uuid = UUID.randomUUID();
        schedulers.put(uuid, new MflpScheduler(time, false, executor));
        uuids.add(uuid);
        return uuid;
    }
}
