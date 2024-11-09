package me.tolek.gui.screens;

import me.tolek.util.CommandUtil;
import me.tolek.util.InstancedValues;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class NonMflpUserScreen extends Screen {

    private final String player;

    public NonMflpUserScreen(String nonMflpUserUsername) {
        super(Text.translatable("mflp.nonMflpUserScreen.name"));
        player = nonMflpUserUsername;
    }

    private String getFullCommand() {
        final String command = "msg %s %s";
        return command.formatted(player, getFullMessage());
    }

    private String getFullMessage() {
        assert client != null;
        final String message = "%s tried to invite you to their MFLP party, but you do not have MFLP installed, you can install it at %s";
        return message.formatted(client.getSession().getUsername(), InstancedValues.getInstance().modrinthUrl);
    }

    @Override
    protected void init() {
        final int buttonsWidth = 150;
        final int padding = 20;
        final int buttonsHeight = 20;

        ButtonWidget yesButton = ButtonWidget.builder(ScreenTexts.YES, (b) -> { CommandUtil.sendCommand(getFullCommand()); close(); })
                .dimensions(width / 2 + buttonsWidth - buttonsWidth + padding, height / 2 + height / 8, buttonsWidth, buttonsHeight)
                .build();
        addDrawableChild(yesButton);

        ButtonWidget noButton = ButtonWidget.builder(ScreenTexts.NO, (b) -> close())
                .dimensions(width / 2 - buttonsWidth - padding, height / 2 + height / 8, buttonsWidth, buttonsHeight)
                .build();
        addDrawableChild(noButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        Text titleText = Text.translatable("mflp.nonMflpUserScreen.title", player);
        Text messagePreviewText = Text.translatable("mflp.nonMflpUserScreen.messagePreview", getFullMessage());
        Text messageText = Text.literal(getFullMessage());

        context.drawTextWithShadow(textRenderer, titleText, width / 2 - textRenderer.getWidth(titleText) / 2, height / 2 - height / 4, 0xFFFFFF);
        context.drawTextWithShadow(textRenderer, messagePreviewText, width / 2 - textRenderer.getWidth(messagePreviewText) / 2, height / 2 - 30, 0xFFFFFF);
        context.drawTextWithShadow(textRenderer, messageText, width / 2 - textRenderer.getWidth(messageText) / 2, height / 2 - 20, 0xFFFFFF);
    }
}
