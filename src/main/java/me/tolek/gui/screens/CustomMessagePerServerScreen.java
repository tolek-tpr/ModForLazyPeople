package me.tolek.gui.screens;

import me.tolek.ModForLazyPeople;
import me.tolek.gui.widgets.ScrollableListWidget;
import me.tolek.gui.widgets.IconButtonWidgetWithoutButtonTextures;
import me.tolek.gui.widgets.TextInputWidget;
import me.tolek.modules.settings.CustomMessagePerServerList;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.util.RegexUtil;
import me.tolek.util.Tuple;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static me.tolek.gui.widgets.autoReply.ArReplyWidget.CROSS_ICON;

public class CustomMessagePerServerScreen extends Screen {

    public CustomMessagePerServerScreen() {
        super(Text.translatable("mflp.messagePerServer.title"));
    }

    private final CustomMessagePerServerList list = CustomMessagePerServerList.getInstance();
    private final MflpSettingsList settingsList = MflpSettingsList.getInstance();
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final TextRenderer tx = client.textRenderer;

    private static final Identifier VALID_REGEX_ICON = Identifier.of(ModForLazyPeople.MOD_ID, "checkmark");
    private static final Identifier INVALID_REGEX_ICON = Identifier.of(ModForLazyPeople.MOD_ID, "cross");

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
        TextInputWidget defaultUnafkWidget = new TextInputWidget(tx, 200, 36, 150, 20, Text.literal(settingsList.WB_UN_AFK_REGEX.getState()),
                settingsList.WB_UN_AFK_REGEX::setState);

        addDrawableChild(defaultJoinWidget);
        addDrawableChild(defaultUnafkWidget);

        IconButtonWidgetWithoutButtonTextures defaultJoinRegexValidityIcon = setupValidityIcon(defaultJoinWidget);
        defaultJoinRegexValidityIcon.setY(38);

        IconButtonWidgetWithoutButtonTextures defaultUnafkRegexValidityIcon = setupValidityIcon(defaultUnafkWidget);
        defaultUnafkRegexValidityIcon.setY(38);

        addDrawableChild(defaultJoinRegexValidityIcon);
        addDrawableChild(defaultUnafkRegexValidityIcon);

        for (int i = 0; i < list.getMessages().size(); i++) {
            Tuple<String, Tuple<String, String>> currentTuple = list.getMessages().get(i);

            TextIconButtonWidget ibw = new TextIconButtonWidget.Builder(Text.of(""), (button) -> {
                list.getMessages().remove(currentTuple);
                this.client.setScreen(new CustomMessagePerServerScreen());
            }, true).dimension(20, 20)
                    .texture(CROSS_ICON, 20, 20).build();
            ibw.setPosition(20 + 152, 83);

            TextInputWidget serverTIW = new TextInputWidget(tx, 20, 83, 150, 20, Text.literal(currentTuple.value1), (value) -> currentTuple.value1 = value);
            TextInputWidget joinTIW = new TextInputWidget(tx, 200, 83, 150, 20, Text.literal(currentTuple.value2.value1), (value) -> currentTuple.value2.value1 = value);
            TextInputWidget afkTIW = new TextInputWidget(tx, 380, 83, 150, 20, Text.literal(currentTuple.value2.value2), (value) -> currentTuple.value2.value2 = value);

            IconButtonWidgetWithoutButtonTextures joinRegexValidityIcon = setupValidityIcon(joinTIW);
            IconButtonWidgetWithoutButtonTextures afkRegexValidityIcon = setupValidityIcon(afkTIW);

            slw.addRow(serverTIW, ibw, joinTIW, joinRegexValidityIcon, afkTIW, afkRegexValidityIcon);
        }

        addDrawableChild(slw);
    }

    private static IconButtonWidgetWithoutButtonTextures setupValidityIcon(TextInputWidget textInputWidget) {
        IconButtonWidgetWithoutButtonTextures regexValidityIcon = new IconButtonWidgetWithoutButtonTextures(16, 16, Text.empty(), VALID_REGEX_ICON, (btn) -> {});
        regexValidityIcon.setX(textInputWidget.getX() + 152);
        updateRegexValidityIcon(regexValidityIcon, textInputWidget.getText());

        textInputWidget.setChangedListener(state -> updateRegexValidityIcon(regexValidityIcon, state));

        return regexValidityIcon;
    }

    private static void updateRegexValidityIcon(IconButtonWidgetWithoutButtonTextures regexValidityIcon, String currentRegex) {
        Tuple<Boolean, String> validationResult = RegexUtil.validateRegex(currentRegex);

        if (validationResult.value1) {
            regexValidityIcon.setTexture(VALID_REGEX_ICON);
            regexValidityIcon.setTooltip(Tooltip.of(Text.literal("Valid RegEx.")));
        } else {
            regexValidityIcon.setTexture(INVALID_REGEX_ICON);
            regexValidityIcon.setTooltip(Tooltip.of(Text.literal("Invalid RegEx: %s".formatted(validationResult.value2))));
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float tickDelta) {
        super.render(context, mouseX, mouseY, tickDelta);

        Text screenName = Text.literal("Custom server join messages");
        context.drawTextWithShadow(textRenderer, screenName, width / 2 - textRenderer.getWidth(screenName) / 2, 10, 0xffffff);

        Text usernameRegexInfo = Text.literal("To use the RegEx for usernames, type in %u.");
        context.drawTextWithShadow(textRenderer, usernameRegexInfo, width / 2 - textRenderer.getWidth(usernameRegexInfo) / 2,
                12 + tx.fontHeight, 0xffffff);

        if (list.getMessages().isEmpty()) {
            Text noMessagesSet = Text.literal("No custom messages set!");
            context.drawTextWithShadow(tx, noMessagesSet, width / 2 - tx.getWidth(noMessagesSet) / 2, height / 2, 0xffffff);
        }

        context.drawTextWithShadow(tx, Text.literal("Server"),
                20, 65, 0xffffff);
        context.drawTextWithShadow(tx, Text.literal("Join Message (REGEX)"),
                200, 65, 0xffffff);
        context.drawTextWithShadow(tx, Text.literal("Unafk Message (REGEX)"),
                360, 65, 0xffffff);

        context.drawTextWithShadow(tx, Text.literal("Default Join Message (REGEX)"),
                20, 36 - 2 - tx.fontHeight, 0xffffff);
        context.drawTextWithShadow(tx, Text.literal("Default Unafk Message (REGEX)"),
                200, 36 - 2 - tx.fontHeight, 0xffffff);

    }

    @Override
    public void close() {
        this.client.setScreen(new MflpSettingsScreen());
    }

}
