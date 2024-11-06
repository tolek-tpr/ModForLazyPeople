package me.tolek.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import me.tolek.ModForLazyPeople;
import me.tolek.gui.screens.*;
import me.tolek.network.WebSocketServerHandler;
import me.tolek.util.InstancedValues;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class MenuPickerWidget extends ContainerWidget {

    private List<ClickableWidget> children = new ArrayList<>();

    private static final Identifier CONNECTED_ICON = Identifier.of(ModForLazyPeople.MOD_ID, "checkmark");
    private static final Identifier DISCONNECTED_ICON = Identifier.of(ModForLazyPeople.MOD_ID, "cross");

    public MenuPickerWidget(int x, int y, MinecraftClient client) {
        super(x, y, 150, 20, Text.literal("test"));

        Screen screen = client.currentScreen;

        ButtonWidget macrosButton = ButtonWidget.builder(Text.translatable("mflp.mainConfig.macrosButton"), (button) -> {
            client.setScreen(new MflpMacroConfig(client));
        }).dimensions(10, 22, 70, 20).build();
        ButtonWidget settingsWidget = ButtonWidget.builder(Text.translatable("mflp.mainConfig.settingsButton"), (button) -> {
            client.setScreen(new MflpSettingsScreen());
        }).dimensions(82, 22, 70, 20).build();
        ButtonWidget autoReplyWidget = ButtonWidget.builder(Text.translatable("mflp.mainConfig.autoReplyButton"), (button) -> {
            client.setScreen(new AutoReplyScreen());
        }).dimensions(154, 22, 80, 20).build();
        ButtonWidget hotkeysWidget = ButtonWidget.builder(Text.translatable("mflp.mainConfig.hotkeyButton"), (button) -> {
            client.setScreen(new MflpHotkeysScreen());
        }).dimensions(236, 22, 80, 20).build();

        RenderSystem.enableBlend();
        TextIconButtonWidget discordWidget = TextIconButtonWidget.builder(Text.empty(), ConfirmLinkScreen.opening(screen, InstancedValues.getInstance().discordUrl), true)
                .texture(Identifier.of(ModForLazyPeople.MOD_ID, "discord"), 16, 16)
                .build();

        TextIconButtonWidget changelogsWidget = TextIconButtonWidget.builder(Text.empty(), (msg) -> client.setScreen(new ChangelogsScreen(screen)), true)
                .texture(Identifier.of(ModForLazyPeople.MOD_ID, "changelogs"), 16, 16)
                .build();
        RenderSystem.disableBlend();

        discordWidget.setDimensionsAndPosition(20, 20, screen.width - 30, screen.height - 30);
        changelogsWidget.setDimensionsAndPosition(20, 20, screen.width - 55, screen.height - 30);

        boolean isConnected = !WebSocketServerHandler.getInstance().isDisconnected();

        ButtonWidget reconnectButton = ButtonWidget.builder(Text.translatable("mflp.reconnect"), (button) -> {
                    WebSocketServerHandler.getInstance().reconnect();
                    client.setScreen(screen);
                }).dimensions(10, screen.height - 30, 70, 20)
                .tooltip(Tooltip.of(Text.translatable("mflp.reconnect.tooltip")))
                .build();
        reconnectButton.active = !isConnected;

        IconWithTooltipWidget connectionStatusIcon = new IconWithTooltipWidget(16, 16, Text.empty(), isConnected ? CONNECTED_ICON : DISCONNECTED_ICON);
        connectionStatusIcon.setTooltip(Tooltip.of(isConnected ? Text.translatable("mflp.mainConfig.connected.tooltip") : Text.translatable("mflp.mainConfig.disconnected.tooltip")));
        connectionStatusIcon.setPosition(82, screen.height - 28);

        addChild(macrosButton);
        addChild(settingsWidget);
        addChild(autoReplyWidget);
        addChild(hotkeysWidget);
        addChild(discordWidget);
        addChild(changelogsWidget);
        addChild(reconnectButton);
        addChild(connectionStatusIcon);
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
