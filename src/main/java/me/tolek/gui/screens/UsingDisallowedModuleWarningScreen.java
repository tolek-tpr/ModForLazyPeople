package me.tolek.gui.screens;

import me.tolek.gui.widgets.ScrollableListWidget;
import me.tolek.modules.settings.base.BooleanSetting;
import me.tolek.util.DisallowedModulesUtil;
import me.tolek.util.Tuple;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class UsingDisallowedModuleWarningScreen extends Screen {

    private final String serverAddress;

    public UsingDisallowedModuleWarningScreen(String serverAddress) {
        super(Text.translatable("mflp.usingDisallowedModuleWarningScreen.name"));
        this.serverAddress = serverAddress;
    }

    @Override
    protected void init() {
        ScrollableListWidget slw = new ScrollableListWidget(this.client, width, height - 168, 88, 22);

        for (Tuple<BooleanSetting, Boolean> offender : DisallowedModulesUtil.getDisallowedModules(serverAddress)) {
            Text labelText = Text.translatable(offender.value1.getName()).formatted(Formatting.RED);
            TextWidget label = new TextWidget(width / 2 - 155,
                    10 - textRenderer.fontHeight / 2, textRenderer.getWidth(labelText) + 10, 20, labelText, textRenderer);
            slw.addRow(label);
        }

        addDrawableChild(slw);

        final int buttonsWidth = 150;
        final int padding = 20;
        final int buttonsHeight = 20;

        ButtonWidget disableButton = ButtonWidget.builder(Text.translatable("mflp.usingDisallowedModuleWarningScreen.disableOffendingModules"), (b) -> { DisallowedModulesUtil.fixDisallowedModules(serverAddress); close(); })
                .dimensions(width / 2 + buttonsWidth - buttonsWidth + padding, height - 42, buttonsWidth, buttonsHeight)
                .build();
        addDrawableChild(disableButton);

        ButtonWidget playAnywayButton = ButtonWidget.builder(Text.translatable("mflp.usingDisallowedModuleWarningScreen.playAnyway"), (b) -> close())
                .dimensions(width / 2 - buttonsWidth - padding, height - 42, buttonsWidth, buttonsHeight)
                .tooltip(Tooltip.of(Text.translatable("mflp.usingDisallowedModuleWarningScreen.playAnyway.tooltip")))
                .build();
        addDrawableChild(playAnywayButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        Text titleText = Text.translatable("mflp.usingDisallowedModuleWarningScreen.title");
        Text implicationsText = Text.translatable("mflp.usingDisallowedModuleWarningScreen.implications");

        context.drawTextWithShadow(textRenderer, titleText, width / 2 - textRenderer.getWidth(titleText) / 2, 42, 0xFFFFFF);
        context.drawTextWithShadow(textRenderer, implicationsText, width / 2 - textRenderer.getWidth(implicationsText) / 2, 42 + textRenderer.fontHeight + 4, 0xFFFFFF);
    }

}
