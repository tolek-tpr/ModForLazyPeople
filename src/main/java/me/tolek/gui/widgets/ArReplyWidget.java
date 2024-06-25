package me.tolek.gui.widgets;

import me.tolek.modules.autoReply.AutoReply;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ArReplyWidget extends ContainerWidget {

    private List<ClickableWidget> children = new ArrayList<>();
    private TextRenderer tx;
    private int x;
    private int y;
    private AutoReply ar;
    private int width;
    private int height;
    private TextFieldWidget widget;
    private TextIconButtonWidget ibw;
    public static Identifier CROSS_ICON = new Identifier("modforlazypeople", "cross");

    public ArReplyWidget(int x, int y, String tr, AutoReply ar, String tt, TextRenderer tx, int width, int height, ButtonWidget.PressAction pa) {
        super(x, y, 150, 20, Text.literal("test"));

        this.tx = tx;
        this.x = x;
        this.y = y;
        this.ar = ar;
        this.width = width;
        this.height = height;

        widget = new TextFieldWidget(tx, x, y, 150,
                20, Text.literal(tr));
        widget.setText(tr);
        widget.setMaxLength(Integer.MAX_VALUE);
        widget.setTooltip(Tooltip.of(Text.literal(tt)));

        ibw = new TextIconButtonWidget.Builder(Text.of(""), pa, true).dimension(20, 20)
                .texture(CROSS_ICON, 20, 20).build();
        ibw.setPosition(x + 152, y);

        addChild(widget);
        addChild(ibw);
    }

    public TextFieldWidget getWidget() { return this.widget; }
    public TextIconButtonWidget getIconButtonWidget() { return this.ibw; }

    @Override
    public List<? extends ClickableWidget> children() {
        return children;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {}

    public void addChild(ClickableWidget child) {
        children.add(child);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
