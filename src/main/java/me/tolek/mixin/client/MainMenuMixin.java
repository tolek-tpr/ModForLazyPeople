package me.tolek.mixin.client;

import me.tolek.gui.screens.MflpHelloScreen;
import me.tolek.gui.screens.MflpUpdateScreen;
import me.tolek.util.InstancedValues;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MainMenuMixin {

    private InstancedValues iv = InstancedValues.getInstance();

    @Inject(at = @At("TAIL"), method = "init")
    private void init(CallbackInfo ci) {
        InstancedValues iv = InstancedValues.getInstance();



        // Example usage: Load data


        if (!iv.shownWelcomeScreen) MinecraftClient.getInstance().setScreen(new MflpHelloScreen());
        if (iv.updateAvailable && !iv.shownUpdateScreen) MinecraftClient.getInstance().setScreen(iv.shownWelcomeScreen ? new MflpUpdateScreen(null) : new MflpUpdateScreen(new MflpHelloScreen()));
    }

}
