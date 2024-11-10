package me.tolek.gui.screens;

import me.tolek.gui.widgets.autoReply.AutoReplyWidget;
import me.tolek.gui.widgets.InputBoxWidget;
import me.tolek.gui.widgets.MenuPickerWidget;
import me.tolek.modules.autoReply.AutoRepliesList;
import me.tolek.modules.autoReply.AutoReply;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class AutoReplyScreen extends Screen {

    private AutoRepliesList autoReplies = AutoRepliesList.getInstance();

    public AutoReplyScreen() {
        super(Text.translatable("mflp.autoReplyScreen.title"));
    }

    @Override
    public void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            client.setScreen((Screen) null);
        }).dimensions(width / 2 - 75, height - 29, 150, 20).build());

        MenuPickerWidget mpw = new MenuPickerWidget(10, 22, client);
        addDrawableChild(mpw);

        addDrawableChild(ButtonWidget.builder(Text.translatable("mflp.create"), (button -> {
            InputBoxWidget ibw = new InputBoxWidget(textRenderer, width / 2 - 75,   height / 2 - 40, 150, 20, Text.translatable("mflp.name"));

            ibw.setKeyConsumer((keyCode) -> {
                if (keyCode == InputUtil.GLFW_KEY_ESCAPE) {
                    client.setScreen(new AutoReplyScreen());
                } else if (keyCode == InputUtil.GLFW_KEY_ENTER) {
                    autoReplies.addAutoReply(new AutoReply(ibw.getText(), new ArrayList<>(), new ArrayList<>()));
                    client.setScreen(new AutoReplyScreen());
                }
            });
            addDrawableChild(ibw);
        })).dimensions(400, 22, 70, 20).build());

        int step = 2;
        for (AutoReply m : autoReplies.getAutoReplies()) {
            AutoReplyWidget arw = new AutoReplyWidget(width / 2, 42 + step, m, textRenderer, this.client);
            addDrawableChild(arw);
            arw.children().forEach(this::addDrawableChild);
            step += 22;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        if (autoReplies.getAutoReplies().isEmpty()) {
            context.drawTextWithShadow(textRenderer, Text.translatable("mflp.autoReply.noAutoRepliesAdded"),
                    width / 2 - textRenderer.getWidth(Text.translatable("mflp.autoReply.noAutoRepliesAdded")) / 2, 46 + textRenderer.fontHeight / 2, 0xffffff);
        }
    }

}
