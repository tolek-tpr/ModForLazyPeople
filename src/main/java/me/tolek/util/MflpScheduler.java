package me.tolek.util;

import java.util.function.Consumer;

public class MflpScheduler {

    private Consumer<Boolean> executor;
    private int originalTime;
    private int time;
    private boolean shouldRerun;

    public MflpScheduler(int time, boolean rerun, Consumer<Boolean> executor) {
        this.originalTime = time;
        this.time = time;
        this.shouldRerun = rerun;
        this.executor = executor;
    }

    public int getTime() { return this.time; }
    public boolean getRerun() { return this.shouldRerun; }

    public void decrease() {
        this.time = this.time - 1;
        if (this.time == 0) executor.accept(true);
        if (this.time == 0 && shouldRerun) reset();
    }
    public void reset() { this.time = this.originalTime; }

}
