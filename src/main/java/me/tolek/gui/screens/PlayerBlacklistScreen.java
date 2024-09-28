package me.tolek.gui.screens;

import me.tolek.gui.widgets.ScrollableListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class PlayerBlacklistScreen extends Screen {

    public PlayerBlacklistScreen() {
        super(Text.translatable("mflp.playerBlacklistScreen.title"));
    }

    @Override
    public void init() {
        ScrollableListWidget slw = new ScrollableListWidget(this.client, width, height - 84, 44, 22);
        slw.setRenderBackground(false);


    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(new MflpSettingsScreen());
    }

}
