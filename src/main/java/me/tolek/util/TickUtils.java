package me.tolek.util;

import me.tolek.interfaces.IClientTickHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class TickUtils {

    private static final TickUtils INSTANCE = new TickUtils();

    private final List<IClientTickHandler> clientTickHandlers = new ArrayList<>();

    public static TickUtils getInstance()
    {
        return INSTANCE;
    }

    public void registerClientTickHandler(IClientTickHandler handler)
    {
        if (!this.clientTickHandlers.contains(handler))
        {
            this.clientTickHandlers.add(handler);
        }
    }

    @ApiStatus.Internal
    public void onClientTick(MinecraftClient mc)
    {
        if (!this.clientTickHandlers.isEmpty())
        {
            for (IClientTickHandler handler : this.clientTickHandlers)
            {
                handler.onClientTick(mc);
            }
        }
    }

}
