package me.tolek.gui.screens;

import me.tolek.gui.widgets.autoReply.ArReplyWidget;
import me.tolek.gui.widgets.autoReply.ArSettingsBoxWidget;
import me.tolek.gui.widgets.InputBoxWidget;
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

@Environment(EnvType.CLIENT)
public class AutoReplySettingScreen extends Screen {

    private Screen parent;
    private AutoReply ar;
    private ArSettingsBoxWidget arss;

    public AutoReplySettingScreen(Screen parent, AutoReply ar) {
        super(Text.translatable("mflp.autoReplyConfigScreen.title"));

        this.parent = parent;
        this.ar = ar;
    }

    @Override
    public void init() {
        this.arss = new ArSettingsBoxWidget(10, 25, client, this.parent, this.ar, textRenderer, width, height);

        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            client.setScreen(parent);
        }).dimensions(width / 2 - 75, height - 30, 150, 20).build());

        addDrawableChild(arss);
        arss.children().forEach(this::addDrawableChild);

        addDrawableChild(ButtonWidget.builder(Text.literal("Add"), (button) -> {
            InputBoxWidget ibw = new InputBoxWidget(textRenderer, width / 2 - 75, height / 2 - 40, 150, 20, Text.literal("Keyword"));
            ibw.setKeyConsumer((keyCode) -> {
                if (keyCode == InputUtil.GLFW_KEY_ESCAPE) {
                    client.setScreen(new AutoReplySettingScreen(this.parent, this.ar));
                } else if (keyCode == InputUtil.GLFW_KEY_ENTER) {
                    ar.addKeyword(ibw.getText());
                    client.setScreen(new AutoReplySettingScreen(this.parent, this.ar));
                }
            });
            addDrawableChild(ibw);
        }).dimensions(20 + 142, 63 - textRenderer.fontHeight / 2, 30, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Add"), (button) -> {
            InputBoxWidget ibw = new InputBoxWidget(textRenderer, width / 2 - 75, height / 2 - 40, 150, 20, Text.literal("Keyword"));
            ibw.setKeyConsumer((keyCode) -> {
                if (keyCode == InputUtil.GLFW_KEY_ESCAPE) {
                    client.setScreen(new AutoReplySettingScreen(this.parent, this.ar));
                } else if (keyCode == InputUtil.GLFW_KEY_ENTER) {
                    ar.addReply(ibw.getText());
                    client.setScreen(new AutoReplySettingScreen(this.parent, this.ar));
                }
            });
            addDrawableChild(ibw);
        }).dimensions(width / 2 + 5 + 142, 63 - textRenderer.fontHeight / 2, 30, 20).build());

        int step = 2;
        int step2 = 2;
        if (ar.getKeywords() != null && !ar.getKeywords().isEmpty()) {
            for (int i = 0; i < ar.getKeywords().size(); ++i) {
                String s = ar.getKeywords().get(i);
                ArReplyWidget tr = new ArReplyWidget(20, 83 + step, s, ar, AutoRepliesList.toReplyTooltip, textRenderer, width, height, (button -> {
                    ar.removeKeyword(s);
                    client.setScreen(new AutoReplySettingScreen(this.parent, this.ar));
                }));
                int finalI = i;
                tr.getWidget().setChangedListener((state) -> {
                    ar.editToReply(finalI, state);
                });

                addDrawableChild(tr);
                tr.children().forEach(this::addDrawableChild);
                step += 22;
            }
        }

        if (ar.getReplies() != null && !ar.getReplies().isEmpty()) {
            for (int i = 0; i < ar.getReplies().size(); ++i) {
                String s = ar.getReplies().get(i);

                ArReplyWidget r = new ArReplyWidget(width / 2 + 5, 83 + step2, s, ar, AutoRepliesList.repliesTooltip, textRenderer, width, height, (button -> {
                    ar.removeReply(s);
                    client.setScreen(new AutoReplySettingScreen(this.parent, this.ar));
                }));
                int finalI = i;
                r.getWidget().setChangedListener((state) -> {
                    ar.editReplies(finalI, state);
                });
                addDrawableChild(r);
                r.children().forEach(this::addDrawableChild);
                step2 += 22;
            }
        }

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        if (ar != null) {
            Text macroName = Text.literal(ar.getName());
            context.drawTextWithShadow(textRenderer, macroName, width / 2 - textRenderer.getWidth(macroName) / 2, 10, 0xffffff);
        }

        context.drawTextWithShadow(textRenderer, Text.literal("Keywords"),
                20, 65, 0xffffff);
        context.drawTextWithShadow(textRenderer, Text.literal("Replies"),
                width / 2 + 5, 65, 0xffffff);

        remove(arss);
        arss.children().forEach(this::remove);
        addDrawableChild(arss);
        arss.children().forEach(this::addDrawableChild);
        context.getScaledWindowHeight();
    }

}
