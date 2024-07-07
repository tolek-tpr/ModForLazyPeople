package me.tolek.gui.widgets.autoReply;

import me.tolek.gui.screens.AutoReplyScreen;
import me.tolek.gui.screens.AutoReplySettingScreen;
import me.tolek.gui.screens.MflpConfig;
import me.tolek.gui.screens.MflpConfigureMacroScreen;
import me.tolek.modules.autoReply.AutoRepliesList;
import me.tolek.modules.autoReply.AutoReply;
import me.tolek.util.MflpUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class AutoReplyWidget extends ContainerWidget {

    private List<ClickableWidget> children = new ArrayList<>();
    private AutoRepliesList autoReplies = AutoRepliesList.getInstance();
    private AutoReply ar;
    private TextRenderer tx;
    private int x;
    private int y;
    private MinecraftClient client;

    public AutoReplyWidget(int x, int y, AutoReply ar, TextRenderer tx, MinecraftClient client) {
        super(x, y, 310, 20, Text.literal("auto reply widget"));
        this.ar = ar;
        this.tx = tx;
        this.x = x;
        this.y = y;
        this.client = client;

        Text toggleText = ar.isTurnedOn() ? Text.literal("True").formatted(Formatting.GREEN) :
                Text.literal("False").formatted(Formatting.RED);
        ButtonWidget toggleButton = ButtonWidget.builder(toggleText, (button -> {
            ar.setTurnedOn(!ar.isTurnedOn());
            client.setScreen(new AutoReplyScreen());
        })).dimensions(x + 5, y, 60, 20).build();
        addChild(toggleButton);

        Text removeText = Text.translatable("mflp.configScreen.removeButton");
        ButtonWidget removeButton = ButtonWidget.builder(removeText, (button -> {
            autoReplies.remove(ar);
            if (client != null) {
                client.setScreen(new AutoReplyScreen());
            }
        })).dimensions(x + 67, y, 70, 20).build();
        addChild(removeButton);

        Text editText = Text.translatable("mflp.configScreen.editButton");
        TextIconButtonWidget tibw = new TextIconButtonWidget.Builder(editText, (button) -> {
            if (client != null) {
                client.setScreen(new AutoReplySettingScreen(new AutoReplyScreen(), ar));
            }
        }, true).texture(MflpUtil.pencilIcon, 20, 20).dimension(20, 20).build();
        tibw.setPosition(x - 180, y);
        addChild(tibw);
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTextWithShadow(tx, ar.getName(), x - 155, y + 10 - tx.fontHeight / 2, 0xffffff);
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

}
