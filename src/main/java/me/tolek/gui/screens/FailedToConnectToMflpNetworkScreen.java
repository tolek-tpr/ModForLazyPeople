package me.tolek.gui.screens;

import me.tolek.network.WebSocketServerHandler;
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
        final int buttonsWidth = 150;
        final int padding = 20;
        final int buttonsHeight = 20;

        ButtonWidget okayButton = ButtonWidget.builder(ScreenTexts.OK, (b) -> close())
                .dimensions(width / 2 + buttonsWidth - buttonsWidth + padding, height / 2 + height / 8, buttonsWidth, buttonsHeight)
                .build();
        addDrawableChild(okayButton);

        ButtonWidget retryButton = ButtonWidget.builder(Text.translatable("mflp.retry"), (b) -> {
                    WebSocketServerHandler.getInstance().reconnect();
                    close();
                })
                .dimensions(width / 2 - buttonsWidth - padding, height / 2 + height / 8, buttonsWidth, buttonsHeight)
                .build();
        addDrawableChild(retryButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        Text titleText = Text.translatable("mflp.failedToConnectScreen.title");
        Text descriptionText = Text.translatable("mflp.failedToConnectScreen.description");
        Text reconnectionText = Text.translatable("mflp.failedToConnectScreen.reconnectionText");

        context.drawTextWithShadow(this.textRenderer, titleText, width / 2 - textRenderer.getWidth(titleText) / 2, height / 2 - height / 4, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, descriptionText, width / 2 - textRenderer.getWidth(descriptionText) / 2, height / 2 - 30, 0xFFFFFF);
        context.drawTextWithShadow(textRenderer, reconnectionText, width / 2 - textRenderer.getWidth(reconnectionText) / 2, height / 2 - 20, 0xFFFFFF);
    }
}
