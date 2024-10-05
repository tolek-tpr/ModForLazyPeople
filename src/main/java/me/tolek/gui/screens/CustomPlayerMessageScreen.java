package me.tolek.gui.screens;

import me.tolek.gui.widgets.ScrollableListWidget;
import me.tolek.modules.settings.CustomPlayerMessageList;
import me.tolek.util.Tuple;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;

import static me.tolek.gui.widgets.autoReply.ArReplyWidget.CROSS_ICON;

public class CustomPlayerMessageScreen extends Screen {

    public CustomPlayerMessageScreen() {
        super(Text.translatable("mflp.playerBlacklistScreen.title"));
    }

    private final TextRenderer tx = MinecraftClient.getInstance().textRenderer;
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final CustomPlayerMessageList list = CustomPlayerMessageList.getInstance();

    @Override
    public void init() {
        ScrollableListWidget slw = new ScrollableListWidget(this.client, width, height - 84, 44, 22);
        slw.setRenderBackground(false);

        addDrawableChild(ButtonWidget.builder(Text.literal("Add"), (button) -> {
            list.addMessage(new Tuple<>("", ""));
            clearAndInit();
        }).dimensions(20 + 142, 63 - textRenderer.fontHeight / 2, 30, 20).build());

        int step = 0;
        for (int i = 0; i < list.getMessages().size(); i++) {
            Tuple<String, String> currentTuple = list.getMessages().get(i);

            TextIconButtonWidget ibw = new TextIconButtonWidget.Builder(Text.of(""), (button) -> {
                list.getMessages().remove(currentTuple);
                clearAndInit();
            }, true).dimension(20, 20)
                    .texture(CROSS_ICON, 20, 20).build();
            ibw.setPosition(20 + 152, 83 + step);
            addDrawableChild(ibw);

            TextFieldWidget widget = new TextFieldWidget(tx, 20, 83 + step, 150, 20, Text.literal(currentTuple.value1));
            TextFieldWidget widget2 = new TextFieldWidget(tx, width / 2 + 5, 83 + step, 150, 20,
                    Text.literal(currentTuple.value2));

            widget.setMaxLength(Integer.MAX_VALUE);
            widget2.setMaxLength(Integer.MAX_VALUE);
            widget.setText(currentTuple.value1);
            widget2.setText(currentTuple.value2);

            widget.setChangedListener((value) -> currentTuple.value1 = value);
            widget2.setChangedListener((value) -> currentTuple.value2 = value);
            addDrawableChild(widget);
            addDrawableChild(widget2);
            step += 22;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        Text macroName = Text.literal("Custom player messages");
        context.drawTextWithShadow(textRenderer, macroName, width / 2 - textRenderer.getWidth(macroName) / 2, 10, 0xffffff);

        if (list.getMessages().isEmpty()) {
            Text noMessagesSet = Text.literal("No custom messages set!");
            context.drawTextWithShadow(tx, noMessagesSet, width / 2 - tx.getWidth(noMessagesSet) / 2, height / 2, 0xffffff);
        }

        context.drawTextWithShadow(tx, Text.literal("Player"),
                20, 65, 0xffffff);
        context.drawTextWithShadow(tx, Text.literal("Message"),
                width / 2 + 5, 65, 0xffffff);
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(new MflpSettingsScreen());
    }

}