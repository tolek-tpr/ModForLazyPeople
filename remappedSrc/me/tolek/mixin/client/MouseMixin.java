package me.tolek.mixin.client;

import me.tolek.event.EventManager;
import me.tolek.event.MouseListener.MouseClickEvent;
import me.tolek.event.MouseListener.MouseMoveEvent;
import me.tolek.event.MouseListener.MouseScrollEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.tolek.event.MouseListener.MouseMoveEvent;
import static me.tolek.event.MouseListener.MouseScrollEvent;
import static me.tolek.event.MouseListener.MouseClickEvent;

@Mixin(Mouse.class)
public class MouseMixin {

    @Shadow @Final private MinecraftClient client;
    @Shadow private double eventDeltaHorizontalWheel;
    @Shadow private double eventDeltaVerticalWheel;

    @Inject(method = "onCursorPos",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/Mouse;hasResolutionChanged:Z", ordinal = 0))
    private void hookOnMouseMove(long handle, double xpos, double ypos, CallbackInfo ci)
    {
        Window window = this.client.getWindow();
        int mouseX = (int) (((Mouse) (Object) this).getX() * (double) window.getScaledWidth() / (double) window.getWidth());
        int mouseY = (int) (((Mouse) (Object) this).getY() * (double) window.getScaledHeight() / (double) window.getHeight());

        MouseMoveEvent event = new MouseMoveEvent(mouseX, mouseY);
        EventManager.getInstance().fire(event);
    }

    @Inject(method = "onMouseScroll", cancellable = true,
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;currentScreen:Lnet/minecraft/client/gui/screen/Screen;", ordinal = 0))
    private void hookOnMouseScroll(long handle, double xOffset, double yOffset, CallbackInfo ci)
    {
        Window window = this.client.getWindow();
        int mouseX = (int) (((Mouse) (Object) this).getX() * (double) window.getScaledWidth() / (double) window.getWidth());
        int mouseY = (int) (((Mouse) (Object) this).getY() * (double) window.getScaledHeight() / (double) window.getHeight());

        MouseScrollEvent event = new MouseScrollEvent(mouseX, mouseY);
        EventManager.getInstance().fire(event);

        this.eventDeltaHorizontalWheel = 0.0;
        this.eventDeltaVerticalWheel = 0.0;
    }
    @Inject(method = "onMouseButton", cancellable = true,
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;IS_SYSTEM_MAC:Z", ordinal = 0))
    private void hookOnMouseClick(long handle, final int button, final int action, int mods, CallbackInfo ci)
    {
        Window window = this.client.getWindow();
        int mouseX = (int) (((Mouse) (Object) this).getX() * (double) window.getScaledWidth() / (double) window.getWidth());
        int mouseY = (int) (((Mouse) (Object) this).getY() * (double) window.getScaledHeight() / (double) window.getHeight());

        MouseClickEvent event = new MouseClickEvent(mouseX, mouseY, button, action);
        EventManager.getInstance().fire(event);
    }

}
