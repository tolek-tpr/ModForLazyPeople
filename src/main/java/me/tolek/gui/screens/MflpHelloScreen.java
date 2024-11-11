package me.tolek.gui.screens;

import me.tolek.util.InstancedValues;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class MflpHelloScreen extends Screen {

    private final Screen parent;

    public MflpHelloScreen() {
        super(Text.translatable("mflp.welcomeScreen.quickGuide.title"));
        this.parent = null;
    }

    public MflpHelloScreen(Screen parent) {
        super(Text.translatable("mflp.welcomeScreen.quickGuide.title"));
        this.parent = parent;
    }

    private final InstancedValues iv = InstancedValues.getInstance();

    @Override
    public void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            Objects.requireNonNull(client).setScreen(null);
        }).dimensions(width / 2 - 75, height / 2 + 80, 150, 20).build());
        iv.shownWelcomeScreen = true;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        Text welcomeText = Text.translatable("mflp.welcomeScreen.welcomeText");
        Text quickGuideLine1 = Text.translatable("mflp.welcomeScreen.line1");
        Text quickGuideLine2 = Text.translatable("mflp.welcomeScreen.line2");
        Text quickGuideMacroCommand = Text.translatable("mflp.welcomeScreen.command");
        Text quickGuideLine3 = Text.translatable("mflp.welcomeScreen.line3");
        Text quickGuideLine4 = Text.translatable("mflp.welcomeScreen.line4");
        Text quickGuideLine5 = Text.translatable("mflp.welcomeScreen.line5");
        Text quickGuideLine6 = Text.translatable("mflp.welcomeScreen.line6");
        Text quickGuideLine7 = Text.translatable("mflp.welcomeScreen.line7");
        Text quickGuideLine8 = Text.translatable("mflp.welcomeScreen.line8").formatted(Formatting.RED, Formatting.BOLD);
        //Text quickGuide5 = Text.literal("To open this screen once closed, run /mflpwelcome");

        context.drawTextWithShadow(textRenderer, welcomeText, width / 2 - textRenderer.getWidth(welcomeText) / 2, height / 2 - height / 4, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuideLine1, width / 2 - textRenderer.getWidth(quickGuideLine1) / 2, height / 2 - 30, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuideLine2, width / 2 - textRenderer.getWidth(quickGuideLine2) / 2, height / 2 - 20, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuideMacroCommand, width / 2 - textRenderer.getWidth(quickGuideMacroCommand) / 2, height / 2 - 10, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuideLine3, width / 2 - textRenderer.getWidth(quickGuideLine3) / 2, height / 2, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuideLine4, width / 2 - textRenderer.getWidth(quickGuideLine4) / 2, height / 2 + 10, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuideLine5, width / 2 - textRenderer.getWidth(quickGuideLine5) / 2, height / 2 + 20, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuideLine6, width / 2 - textRenderer.getWidth(quickGuideLine6) / 2, height / 2 + 30, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuideLine7, width / 2 - textRenderer.getWidth(quickGuideLine7) / 2, height / 2 + 40, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuideLine8, width / 2 - textRenderer.getWidth(quickGuideLine8) / 2, height / 2 + 50, 0xffffff);
        //context.drawTextWithShadow(textRenderer, quickGuide5, width / 2 - textRenderer.getWidth(quickGuide5) / 2, height / 2 + 30, 0xffffff);
    }

    @Override
    public void close() {
        Objects.requireNonNull(client).setScreen(parent);
    }

}
