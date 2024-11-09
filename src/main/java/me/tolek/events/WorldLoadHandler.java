package me.tolek.events;

import me.tolek.interfaces.IWorldLoadListener;
import me.tolek.interfaces.IWorldLoadManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WorldLoadHandler implements IWorldLoadManager
{
    private static final WorldLoadHandler INSTANCE = new WorldLoadHandler();

    private final List<IWorldLoadListener> worldLoadPreHandlers = new ArrayList<>();
    private final List<IWorldLoadListener> worldLoadPostHandlers = new ArrayList<>();

    public static IWorldLoadManager getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void registerWorldLoadPreHandler(IWorldLoadListener listener)
    {
        if (!this.worldLoadPreHandlers.contains(listener))
        {
            this.worldLoadPreHandlers.add(listener);
        }
    }

    @Override
    public void unregisterWorldLoadPreHandler(IWorldLoadListener listener)
    {
        this.worldLoadPreHandlers.remove(listener);
    }

    @Override
    public void registerWorldLoadPostHandler(IWorldLoadListener listener)
    {
        if (!this.worldLoadPostHandlers.contains(listener))
        {
            this.worldLoadPostHandlers.add(listener);
        }
    }

    @Override
    public void unregisterWorldLoadPostHandler(IWorldLoadListener listener)
    {
        this.worldLoadPostHandlers.remove(listener);
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onWorldLoadPre(@Nullable ClientWorld worldBefore, @Nullable ClientWorld worldAfter, MinecraftClient mc)
    {
        if (!this.worldLoadPreHandlers.isEmpty())
        {
            for (IWorldLoadListener listener : this.worldLoadPreHandlers)
            {
                listener.onWorldLoadPre(worldBefore, worldAfter, mc);
            }
        }
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onWorldLoadPost(@Nullable ClientWorld worldBefore, @Nullable ClientWorld worldAfter, MinecraftClient mc)
    {
        if (!this.worldLoadPostHandlers.isEmpty() &&
                (worldBefore != null || worldAfter != null))
        {
            for (IWorldLoadListener listener : this.worldLoadPostHandlers)
            {
                listener.onWorldLoadPost(worldBefore, worldAfter, mc);
            }
        }
    }
}