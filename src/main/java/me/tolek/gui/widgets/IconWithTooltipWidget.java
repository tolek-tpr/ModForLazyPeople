package me.tolek.gui.widgets;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class IconWithTooltipWidget extends TextIconButtonWidget.IconOnly {

    /*private static final ButtonTextures TEXTURES = new ButtonTextures(
            Identifier.ofVanilla("widget/button"), Identifier.ofVanilla("widget/button_disabled"), Identifier.ofVanilla("widget/button_highlighted")
    );*/

    private Identifier modifiableTexture;

    public IconWithTooltipWidget(int width, int height, Text message, Identifier texture) {
        super(width, height, message, width, height, texture, (btn) -> {}, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        modifiableTexture = texture;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        /*context.drawGuiTexture(
                RenderLayer::getGuiTextured,
                TEXTURES.get(this.active, this.isSelected()),
                this.getX(),
                this.getY(),
                this.getWidth(),
                this.getHeight(),
                ColorHelper.getWhite(this.alpha)
        );*/
        int i = this.active ? 16777215 : 10526880;
        this.drawMessage(context, minecraftClient.textRenderer, i | MathHelper.ceil(this.alpha * 255.0F) << 24);
        int ii = this.getX() + this.getWidth() / 2 - this.textureWidth / 2;
        int j = this.getY() + this.getHeight() / 2 - this.textureHeight / 2;
        context.drawGuiTexture(RenderLayer::getGuiTextured, modifiableTexture, ii, j, this.textureWidth, this.textureHeight);
    }

    public void setTexture(Identifier texture) {
        modifiableTexture = texture;
    }

    public void drawMessage(DrawContext context, TextRenderer textRenderer, int color) {
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }
}
