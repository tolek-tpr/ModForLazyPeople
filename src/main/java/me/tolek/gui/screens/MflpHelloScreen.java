package me.tolek.gui.screens;

import me.tolek.util.InstancedValues;
import me.tolek.util.MflpUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@Environment(EnvType.CLIENT)
public class MflpHelloScreen extends Screen {

    public MflpHelloScreen() {
        super(Text.of("Mflp Quick Guide"));
    }
    private InstancedValues iv = InstancedValues.getInstance();
    private MflpUtil mflpUtil = new MflpUtil();

    @Override
    public void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            client.setScreen(null);
        }).dimensions(width / 2 - 75, height / 2 + 80, 150, 20).build());
        iv.shownWelcomeScreen = true;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        Text welcomeText = Text.literal("Welcome to MFLP");
        Text quickGuide = Text.literal("Mflp is a mod for creating command macros, and adding QOL features.");
        Text quickGuide1 = Text.literal("To create a macro run the command: ");
        Text quickGuideMacroCommand = Text.literal("/macro createMacro <macro name> <commands>");
        Text quickGuide2 = Text.literal("the commands syntax is as follows: say-hi.say-hi-2");
        Text quickGuide3 = Text.literal("Dashes = spaces, and the dot separates commands.");
        Text quickGuide4 = Text.literal("For removing macros, use /macro removeMacro <macro name>");
        Text quickGuide5 = Text.literal("If your macro uses a space in its name replace it with a dash");
        Text quickGuide6 = Text.literal("If you want to request new features message me on discord");
        Text quickGuide7 = Text.literal("As of update 2.5.0, you can use the \"create\" button to add macros").formatted(Formatting.RED, Formatting.BOLD);
        //Text quickGuide5 = Text.literal("To open this screen once closed, run /mflpwelcome");

        context.drawTextWithShadow(textRenderer, welcomeText, width / 2 - textRenderer.getWidth(welcomeText) / 2, height / 2 - height / 4, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuide, width / 2 - textRenderer.getWidth(quickGuide) / 2, height / 2 - 30, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuide1, width / 2 - textRenderer.getWidth(quickGuide1) / 2, height / 2 - 20, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuideMacroCommand, width / 2 - textRenderer.getWidth(quickGuideMacroCommand) / 2, height / 2 - 10, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuide2, width / 2 - textRenderer.getWidth(quickGuide2) / 2, height / 2, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuide3, width / 2 - textRenderer.getWidth(quickGuide3) / 2, height / 2 + 10, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuide4, width / 2 - textRenderer.getWidth(quickGuide4) / 2, height / 2 + 20, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuide5, width / 2 - textRenderer.getWidth(quickGuide5) / 2, height / 2 + 30, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuide6, width / 2 - textRenderer.getWidth(quickGuide6) / 2, height / 2 + 40, 0xffffff);
        context.drawTextWithShadow(textRenderer, quickGuide7, width / 2 - textRenderer.getWidth(quickGuide7) / 2, height / 2 + 50, 0xffffff);
        //context.drawTextWithShadow(textRenderer, quickGuide5, width / 2 - textRenderer.getWidth(quickGuide5) / 2, height / 2 + 30, 0xffffff);
    }

    @Override
    public void close() {
        client.setScreen((Screen) null);
    }

}
