package me.tolek.gui.widgets;

import me.tolek.ModForLazyPeople;
import me.tolek.gui.screens.AutoReplyScreen;
import me.tolek.gui.screens.MflpMacroConfig;
import me.tolek.gui.screens.MflpSettingsScreen;
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

        TextIconButtonWidget discordWidget = TextIconButtonWidget.builder(Text.empty(), ConfirmLinkScreen.opening(screen, InstancedValues.getInstance().discordUrl), true)
                .texture(Identifier.of(ModForLazyPeople.MOD_ID, "discord"), 16, 16)
                .build();

        discordWidget.setDimensionsAndPosition(20, 20, screen.width - 30, screen.height - 30);

        ButtonWidget reconnectButton = ButtonWidget.builder(Text.translatable("mflp.reconnect"), (button) -> {
                    WebSocketServerHandler.getInstance().reconnect();
                }).dimensions(10, screen.height - 30, 70, 20)
                .tooltip(Tooltip.of(Text.translatable("mflp.reconnect.tooltip")))
                .build();

        addChild(macrosButton);
        addChild(settingsWidget);
        addChild(autoReplyWidget);
        addChild(discordWidget);
        addChild(reconnectButton);


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
