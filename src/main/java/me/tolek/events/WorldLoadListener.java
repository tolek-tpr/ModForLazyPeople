package me.tolek.events;

import me.tolek.interfaces.IWorldLoadListener;
import me.tolek.modules.settings.MflpSettingsList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;

public class WorldLoadListener implements IWorldLoadListener {

    @Override
    public void onWorldLoadPre(@Nullable ClientWorld worldBefore, @Nullable ClientWorld worldAfter, MinecraftClient mc) {
        // Always disable the Free Camera mode when leaving the world or switching dimensions
        MflpSettingsList.getInstance().FREE_CAM_ENABLED.setState(false);
    }

}
