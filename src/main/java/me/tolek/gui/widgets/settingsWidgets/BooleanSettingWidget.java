package me.tolek.gui.widgets;

import me.tolek.gui.screens.MflpSettingsScreen;
import me.tolek.modules.settings.base.BooleanSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class BooleanSettingWidget extends ContainerWidget{

    private List<ClickableWidget> children = new ArrayList<>();
    private BooleanSetting bs;
    private TextRenderer tx;
    private int x;
    private int y;
    private MinecraftClient client;

    public BooleanSettingWidget(int x, int y, Text text, BooleanSetting bs, TextRenderer tx, MinecraftClient client) {
        super(x, y, 310, 20, text);
        this.bs = bs;
        this.tx = tx;
        this.x = x;
        this.y = y;
        this.client = client;

        Text toggleText = bs.getState() ? Text.literal("True").formatted(Formatting.GREEN) :
                Text.literal("False").formatted(Formatting.RED);
        BooleanWidget bsw = new BooleanWidget(x + 5, y, toggleText, bs, client);
        addChild(bsw);
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTextWithShadow(tx, bs.getName(), x - 155, y + 10 - tx.fontHeight / 2, 0xffffff);
    }

    @Override
    public List<? extends ClickableWidget> children() {
        return children;
    }

    public void addChild(ClickableWidget child) {
        children.add(child);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    public class BooleanWidget extends ButtonWidget {

        public BooleanWidget(int x, int y, Text message, BooleanSetting bs, MinecraftClient client) {
            super(x, y, 150, 20, message, (button) -> {
                bs.run();
                client.setScreen(new MflpSettingsScreen());
            }, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
            this.setTooltip(Tooltip.of(Text.literal(bs.getTooltip())));
        }

    }

}
