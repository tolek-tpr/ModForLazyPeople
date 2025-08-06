package me.tolek.scheduler;

import net.minecraft.util.Identifier;

import java.util.UUID;

public class Task {

    public final long time;
    public final long repeatAmount;
    public final Identifier owner;
    public final UUID uuid;
    public final Runnable r;

    private long elapsed = 0;
    private long fired = 0;

    public Task(long time, long repeatAmount, Identifier owner, Runnable r) {
        this.time = time;
        this.repeatAmount = repeatAmount;
        this.owner = owner;
        this.r = r;
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() { return this.uuid; }

    public void tick() {
        ++elapsed;
        if (elapsed == time) {
            r.run();
            fired++;
            elapsed = 0;
        }
        if (repeatAmount == -1) return;
        if (fired == repeatAmount) MflpScheduler.getInstance().cancelTask(this);
    }

    public long getElapsedTicks() { return elapsed; }
    public long getTicksLeft() { return time - elapsed; }
    public long getElapsedSec() { return Math.round(elapsed / 20); }
    public long getSecLeft() { return Math.round((time - elapsed) / 20); }

}
