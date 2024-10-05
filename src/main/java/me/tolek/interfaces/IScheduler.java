package me.tolek.interfaces;

import java.util.UUID;
import java.util.function.Consumer;

public interface IScheduler {
    UUID scheduleRepeating(int time, Consumer<Boolean> executor);
    UUID scheduleNonRepeating(int time, Consumer<Boolean> executor);
    void cancelTask(UUID uuid);
}
