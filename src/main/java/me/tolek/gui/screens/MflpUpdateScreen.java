package me.tolek.gui.screens;

import me.tolek.util.InstancedValues;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class MflpUpdateScreen extends Screen {

    private Screen parent;
    private InstancedValues iv = InstancedValues.getInstance();

    public MflpUpdateScreen(Screen parent) {
        super(Text.translatable("mflp.updateScreen.title"));
        this.parent = parent;
    }

    @Override
    public void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            client.setScreen(parent);
        }).dimensions(width / 2 - 155, height / 2 + 80, 150, 20).build());
        addDrawableChild(
                ButtonWidget.builder(Text.literal("Download"), ConfirmLinkScreen.opening(this, iv.githubUrl))
                        .dimensions(width / 2 + 2, height / 2 + 80, 150, 20)
                        .build()
        );
        iv.shownUpdateScreen = true;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        Text updateAvailable = Text.literal("Update available!");
        Text currentVersion = Text.literal("Mod for lazy people version: " + iv.version);
        context.drawTextWithShadow(textRenderer, updateAvailable, width / 2 - textRenderer.getWidth(updateAvailable) / 2, height / 2 - height / 4, 0xffffff);
        context.drawTextWithShadow(textRenderer, currentVersion, width / 2 - textRenderer.getWidth(currentVersion) / 2, height / 2 - height / 4 - 20, 0xffffff);
    }

}
