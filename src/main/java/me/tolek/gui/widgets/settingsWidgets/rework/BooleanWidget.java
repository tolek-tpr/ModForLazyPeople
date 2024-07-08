package me.tolek.gui.widgets.settingsWidgets.rework;

import me.tolek.gui.screens.MflpSettingsScreen;
import me.tolek.modules.settings.base.BooleanSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

public class BooleanWidget extends AbstractSettingWidget {

    private final BooleanSetting setting;
    private final TextRenderer tx;
    private ArrayList<ClickableWidget> children = new ArrayList<>();

    private final int x;
    private final int y;

    public BooleanWidget(int x, int y, Text text, BooleanSetting setting, TextRenderer tx) {
        super(x, y, 310, 20, text);
        this.setting = setting;
        this.tx = tx;
        this.x = x;
        this.y =y;

        // Rendering code
        Text toggleText = setting.getState() ? Text.literal("True").formatted(Formatting.GREEN)
                : Text.literal("False").formatted(Formatting.RED);
        ButtonWidget toggleButton = ButtonWidget.builder(toggleText, (button) -> {
            setting.run();
            MinecraftClient.getInstance().setScreen(new MflpSettingsScreen());
        }).tooltip(Tooltip.of(Text.literal(setting.getTooltip())))
                .dimensions(x + 5, y, 150, 20)
                .build();
        children.add(toggleButton);
    }

    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTextWithShadow(tx, setting.getName(), x - 155,
                y + 10 - tx.fontHeight / 2, 0xffffff);
        children.forEach(c -> c.render(context, mouseX, mouseY, delta));
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    public ArrayList<ClickableWidget> children() {
        return children;
    }

}
