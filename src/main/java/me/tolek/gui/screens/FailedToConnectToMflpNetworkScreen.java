package me.tolek.gui.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class FailedToConnectToMflpNetworkScreen extends Screen {
    public FailedToConnectToMflpNetworkScreen() {
        super(Text.translatable("mflp.failedToConnectScreen.title"));
    }

    @Override
    protected void init() {
        ButtonWidget okayButton = ButtonWidget.builder(ScreenTexts.OK, (b) -> close())
                .dimensions(width / 2 + 120 - 100, height / 2 + height / 3, 100, 18)
                .build();
        addDrawableChild(okayButton);

        ButtonWidget retryButton = ButtonWidget.builder(Text.translatable("mflp.retry"), (b) -> {
                    // TODO: RECONNECTION CALL
                    close();
                })
                .dimensions(width / 2 - 120, height / 2 + height / 3, 100, 18)
                .build();
        addDrawableChild(retryButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        Text titleText = Text.translatable("mflp.failedToConnectScreen.title");
        Text descriptionText = Text.translatable("mflp.failedToConnectScreen.description");

        context.drawTextWithShadow(this.textRenderer, titleText, width / 2 - textRenderer.getWidth(titleText) / 2, height / 2 - height / 4, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, descriptionText, width / 2 - textRenderer.getWidth(descriptionText) / 2, height / 2 - 30, 0xFFFFFF);
    }
}
