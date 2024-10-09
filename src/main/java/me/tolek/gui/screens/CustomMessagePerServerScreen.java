package me.tolek.gui.screens;

import me.tolek.gui.widgets.ScrollableListWidget;
import me.tolek.gui.widgets.TextInputWidget;
import me.tolek.modules.settings.CustomMessagePerServerList;
import me.tolek.modules.settings.MflpSettingsList;
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

public class CustomMessagePerServerScreen extends Screen {

    public CustomMessagePerServerScreen() {
        super(Text.translatable("mflp.messagePerServer.title"));
    }

    private final CustomMessagePerServerList list = CustomMessagePerServerList.getInstance();
    private final MflpSettingsList settingsList = MflpSettingsList.getInstance();
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final TextRenderer tx = client.textRenderer;

    @Override
    public void init() {
        ScrollableListWidget slw = new ScrollableListWidget(this.client, width - 2, height - 84, 80, 22);
        slw.setRenderBackground(false);

        addDrawableChild(ButtonWidget.builder(Text.literal("Add"), (button) -> {
            list.addMessagesForServer("", new Tuple<>("", ""));
            clearAndInit();
        }).dimensions(20 + 142, 63 - textRenderer.fontHeight / 2, 30, 20).build());

        TextInputWidget defaultJoinWidget = new TextInputWidget(tx, 20, 36, 150, 20, Text.literal(settingsList.WB_JOIN_REGEX.getState()), 
                settingsList.WB_JOIN_REGEX::setState);
        TextInputWidget defaultUnafkWidget = new TextInputWidget(tx, 340, 36, 150, 20, Text.literal(settingsList.WB_UN_AFK_REGEX.getState()),
                settingsList.WB_UN_AFK_REGEX::setState);

        addDrawableChild(defaultJoinWidget);
        addDrawableChild(defaultUnafkWidget);

        for (int i = 0; i < list.getMessages().size(); i++) {
            Tuple<String, Tuple<String, String>> currentTuple = list.getMessages().get(i);

            TextIconButtonWidget ibw = new TextIconButtonWidget.Builder(Text.of(""), (button) -> {
                list.getMessages().remove(currentTuple);
                this.client.setScreen(new CustomMessagePerServerScreen());
            }, true).dimension(20, 20)
                    .texture(CROSS_ICON, 20, 20).build();
            ibw.setPosition(20 + 152, 83);

            TextInputWidget serverTIW = new TextInputWidget(tx, 20, 83, 150, 20, Text.literal(currentTuple.value1), (value) -> currentTuple.value1 = value);
            TextInputWidget joinTIW = new TextInputWidget(tx, 340, 83, 150, 20, Text.literal(currentTuple.value2.value1), (value) -> currentTuple.value2.value1 = value);
            TextInputWidget afkTIW = new TextInputWidget(tx, 660, 83, 150, 20, Text.literal(currentTuple.value2.value2), (value) -> currentTuple.value2.value2 = value);

            slw.addRow(serverTIW, ibw, joinTIW, afkTIW);
        }

        addDrawableChild(slw);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float tickDelta) {
        super.render(context, mouseX, mouseY, tickDelta);

        Text screenName = Text.literal("Custom player messages");
        context.drawTextWithShadow(textRenderer, screenName, width / 2 - textRenderer.getWidth(screenName) / 2, 10, 0xffffff);

        if (list.getMessages().isEmpty()) {
            Text noMessagesSet = Text.literal("No custom messages set!");
            context.drawTextWithShadow(tx, noMessagesSet, width / 2 - tx.getWidth(noMessagesSet) / 2, height / 2, 0xffffff);
        }

        context.drawTextWithShadow(tx, Text.literal("Server"),
                20, 65, 0xffffff);
        context.drawTextWithShadow(tx, Text.literal("Join Message (REGEX)"),
                340, 65, 0xffffff);
        context.drawTextWithShadow(tx, Text.literal("Unafk Message (REGEX)"),
                660, 65, 0xffffff);

        context.drawTextWithShadow(tx, Text.literal("Default Join Message (REGEX)"),
                20, 36 - 2 - tx.fontHeight, 0xffffff);
        context.drawTextWithShadow(tx, Text.literal("Default Unafk Message (REGEX)"),
                340, 36 - 2 - tx.fontHeight, 0xffffff);

    }

    @Override
    public void close() {
        this.client.setScreen(new MflpSettingsScreen());
    }

}
