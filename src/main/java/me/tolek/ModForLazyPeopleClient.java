package me.tolek;

import me.tolek.event.EventImpl;
import me.tolek.events.AfkDetector;
import me.tolek.events.PartyEvents;
import me.tolek.modules.macro.MacroExecutor;
import me.tolek.modules.autoReply.AutoReplyExecutor;
import me.tolek.modules.settings.executor.AutoWelcomeBackImpl;
import me.tolek.modules.settings.executor.AutoWelcomeImpl;
import me.tolek.modules.settings.executor.EasyMsgExecutor;
import me.tolek.network.IconNetworkHandler;
import me.tolek.network.PartyNetworkHandler;
import me.tolek.network.WebSocketServerHandler;
import net.fabricmc.api.ClientModInitializer;

import java.util.ArrayList;

public class ModForLazyPeopleClient implements ClientModInitializer {

    private final ArrayList<EventImpl> events = new ArrayList<>();
    private final WebSocketServerHandler server = WebSocketServerHandler.getInstance(); // PRE-LOAD for key gen

    @Override
    public void onInitializeClient() {
        ModForLazyPeople.LOGGER.info("Running Mod For Lazy People on the Client!");

        events.add(new AfkDetector());
        events.add(new MacroExecutor());
        events.add(new AutoWelcomeBackImpl());
        events.add(new AutoWelcomeImpl());
        events.add(new AutoReplyExecutor());
        events.add(new PartyEvents());
        events.add(IconNetworkHandler.getInstance());
        events.add(new PartyNetworkHandler());
        events.add(EasyMsgExecutor.getInstance());

        events.forEach(e -> e.setEnabled(true));
    }
}
