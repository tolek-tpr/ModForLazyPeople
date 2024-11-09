package me.tolek;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.tolek.gui.screens.MflpMacroConfig;
import net.minecraft.client.MinecraftClient;

public class ModMenuImpl implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new MflpMacroConfig(MinecraftClient.getInstance());
    }

}
