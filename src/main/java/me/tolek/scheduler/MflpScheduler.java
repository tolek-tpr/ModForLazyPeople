package me.tolek.scheduler;

import me.tolek.event.EventImpl;
import me.tolek.event.UpdateListener;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class MflpScheduler extends EventImpl implements UpdateListener {

    private static MflpScheduler instance;

    private MflpScheduler() {}

    public static MflpScheduler getInstance() {
        if (instance == null) instance = new MflpScheduler();
        return instance;
    }

    private final ArrayList<Task> registeredTasks = new ArrayList<>();
    private final ArrayList<Task> toRemove = new ArrayList<>();

    @Override
    public void onEndTick() {
        registeredTasks.forEach(Task::tick);
        toRemove.forEach(registeredTasks::remove);
        toRemove.clear();
        System.out.println("end tick");
    }

    private Task registerTask(Task task) {
        registeredTasks.add(task);
        return task;
    }

    private void removeByUuid(UUID uuid) {
        AtomicReference<Task> t = new AtomicReference<>();
        registeredTasks.forEach(task -> t.set(task.getUuid() == uuid ? task : null));
        toRemove.add(t.get());
    }

    public Task scheduleDelayedTask(Identifier mod, Runnable runnable, long delay) {
        return registerTask(new Task(delay, 1, mod, runnable));
    }

    public Task scheduleSelfCancellingRepeatingTask(Identifier mod, Runnable runnable, long delay, long times) {
        return registerTask(new Task(delay, times, mod, runnable));
    }

    public Task scheduleRepeatingTask(Identifier mod, Runnable runnable, long waitAmount) {
        return registerTask(new Task(waitAmount, -1, mod, runnable));
    }

    public void cancelTask(UUID uuid) {
        removeByUuid(uuid);
    }

    public void cancelTask(Task t) { toRemove.add(t); }

    @Override
    public void onUpdate() {}

}
