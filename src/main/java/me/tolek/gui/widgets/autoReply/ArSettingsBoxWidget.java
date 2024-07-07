package me.tolek.gui.widgets;

import me.tolek.gui.screens.AutoReplySettingScreen;
import me.tolek.gui.screens.MflpConfigureMacroScreen;
import me.tolek.modules.Macro.MacroList;
import me.tolek.modules.autoReply.AutoRepliesList;
import me.tolek.modules.autoReply.AutoReply;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ArSettingsBoxWidget extends ContainerWidget {

    private List<ClickableWidget> children = new ArrayList<>();
    private AutoRepliesList autoReplies = AutoRepliesList.getInstance();
    private TextRenderer tx;
    private int x;
    private int y;
    private AutoReply ar;
    private Screen parent;
    private int width;
    private int height;

    public ArSettingsBoxWidget(int x, int y, MinecraftClient client, Screen parent, AutoReply ar, TextRenderer tx, int width, int height) {
        super(x, y, 150, 20, Text.literal("test"));

        this.tx = tx;
        this.x = x;
        this.y = y;
        this.ar = ar;
        this.parent = parent;
        this.width = width;
        this.height = height;

        ButtonWidget rename = ButtonWidget.builder(Text.literal("Rename"), (button) -> {
            InputBoxWidget inputBox = new InputBoxWidget(tx, width / 2 - 75, height / 2 - 40, 150, 20, Text.literal(this.ar.getName()));
            inputBox.setKeyConsumer((keyCode -> {
                if (keyCode == InputUtil.GLFW_KEY_ESCAPE) {
                    client.setScreen(new AutoReplySettingScreen(this.parent, this.ar));
                } else if (keyCode == InputUtil.GLFW_KEY_ENTER) {
                    this.ar.setName(inputBox.getText());
                    client.setScreen(new AutoReplySettingScreen(this.parent, this.ar));
                }
            }));

            addChild(inputBox);
        }).dimensions(x, y, 70, 20).build();
        ButtonWidget duplicate = ButtonWidget.builder(Text.literal("Duplicate"), (button) -> {
            AutoReply ara = this.ar.copy();
            autoReplies.addAutoReply(ara);
            client.setScreen(parent);
        }).dimensions(x + 72, y, 70, 20).build();

        addChild(rename);
        addChild(duplicate);
        //addChild(addCommand);
    }

    @Override
    public List<? extends ClickableWidget> children() {
        return children;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
    }

    public void addChild(ClickableWidget child) {
        children.add(child);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
