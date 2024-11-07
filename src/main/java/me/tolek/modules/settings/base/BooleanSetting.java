package me.tolek.modules.settings.base;

import me.tolek.input.Hotkey;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;

public abstract class BooleanSetting extends HotkeyableSetting {

    private boolean state;
    //public Hotkey hotkey;

    public BooleanSetting(String name, boolean defaultValue, String tt) {
        super(name, defaultValue, "boolean", tt);
        this.hotkey = new Hotkey(new HashMap<>());
    }

    public abstract void run();

    public void setState(boolean state) { this.state = state; }

    public boolean getState() { return this.state; }

    @Override
    public void notifyPressed() {
        final boolean pressed = this.getState();

        MutableText text = Text.literal("Toggled ").append(Text.translatable(this.getName())).append(" ").append(pressed ? Text.translatable("mflp.true").formatted(Formatting.GREEN)
                : Text.translatable("mflp.false").formatted(Formatting.RED));
        MinecraftClient.getInstance().inGameHud.setOverlayMessage(text, false);
    }

    @Override
    public void cycle() {
        this.run();
    }

}
