package me.tolek.events;

import me.tolek.ModForLazyPeople;
import me.tolek.event.EventManager;
import me.tolek.event.HudRenderListener;
import me.tolek.gui.screens.UsingDisallowedModuleWarningScreen;
import me.tolek.util.DisallowedModulesUtil;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.util.InstancedValues;
import me.tolek.util.ScreenUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.network.ClientConnection;

import java.net.InetSocketAddress;

public class FabricEventsRegistry implements ClientModInitializer {

    private final InstancedValues iv = InstancedValues.getInstance();
    private final MflpSettingsList settingsList = MflpSettingsList.getInstance();

    @Override
    public void onInitializeClient() {
        // TODO: remove?
        /*ClientLoginConnectionEvents.INIT.register((i, j) -> {
            iv.pauseWelcomeBack = true;
            // bad idea but w/e
            ((IScheduler) MinecraftClient.getInstance()).scheduleNonRepeating(200, b -> iv.pauseWelcomeBack = false);
        });
        ClientLoginConnectionEvents.DISCONNECT.register((i, j) -> {
            // when the connection is ended.
            iv.pauseWelcomeBack = false;
            iv.isAfk = false;
        });
        */
        ClientPlayConnectionEvents.JOIN.register((clientPlayNetworkHandler, packetSender, minecraftClient) -> {
            ClientConnection connection = clientPlayNetworkHandler.getConnection();

            if (connection.getAddress() instanceof InetSocketAddress socketAddr) {
                String host = socketAddr.getHostString(); // hostname only
                ModForLazyPeople.LOGGER.info("Connected to {}", host);
                if (DisallowedModulesUtil.usingDisallowedModule(host)) {
                    ScreenUtil.openScreen(new UsingDisallowedModuleWarningScreen(host));
                }
            }

        });
        // TODO: this is deprecated
        HudRenderCallback.EVENT.register((context, tickDelta) -> { EventManager.getInstance().fire(new HudRenderListener.HudRenderEvent(context, tickDelta)); });
        ClientSendMessageEvents.ALLOW_CHAT.register((msg) -> !(settingsList.AUTO_WELCOME_BACK.getState() && settingsList.AUTO_IGNORE_WB_MESSAGES.getState() && iv.timeSinceLastWbMillis < settingsList.AUTO_IGNORE_WB_MESSAGES_DURATION.getState() * 1000 && msg.contains("wb")));
        WorldRenderEvents.BLOCK_OUTLINE.register((ctx, blockOutlineContext) -> false);
    }

}
