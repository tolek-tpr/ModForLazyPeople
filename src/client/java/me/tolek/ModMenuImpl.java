package me.tolek;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.tolek.gui.MflpConfig;
import net.minecraft.client.MinecraftClient;

public class ModMenuImpl implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            // Return the screen here with the one you created from Cloth Config Builder

            return new MflpConfig(MinecraftClient.getInstance());
        };
    }

}
