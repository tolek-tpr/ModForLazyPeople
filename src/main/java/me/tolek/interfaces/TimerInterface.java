package me.tolek.interfaces;

import java.util.function.Consumer;

public interface TimerInterface {
    void setTimer(long ticksUntil, Consumer<Boolean> executor);
}
