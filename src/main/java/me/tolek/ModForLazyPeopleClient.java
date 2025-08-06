package me.tolek;

import me.tolek.event.EventImpl;
import me.tolek.events.*;
import me.tolek.input.HotkeyExecutorImpl;
import me.tolek.interfaces.IWorldLoadListener;
import me.tolek.modules.macro.MacroExecutor;
import me.tolek.modules.autoReply.AutoReplyExecutor;
import me.tolek.modules.settings.executor.AutoWelcomeBackImpl;
import me.tolek.modules.settings.executor.AutoWelcomeImpl;
import me.tolek.modules.settings.executor.EasyMsgExecutor;
import me.tolek.network.client.WebsocketHandler;
import me.tolek.network.IconHandler;
import me.tolek.scheduler.MflpScheduler;
import me.tolek.util.TickUtils;
import net.fabricmc.api.ClientModInitializer;

import java.util.ArrayList;

public class ModForLazyPeopleClient implements ClientModInitializer {

    private final ArrayList<EventImpl> events = new ArrayList<>();

    @Override
    public void onInitializeClient() {
        ModForLazyPeople.LOGGER.info("Running Mod For Lazy People on the Client!");

        events.add(new AfkDetector());
        events.add(new MacroExecutor());
        events.add(new AutoWelcomeBackImpl());
        events.add(new AutoWelcomeImpl());
        events.add(new AutoReplyExecutor());
        //events.add(new PartyEvents());
        events.add(IconHandler.getInstance());
        //events.add(new PartyNetworkHandler());
        events.add(EasyMsgExecutor.getInstance());
        events.add(new HotkeyExecutorImpl());
        events.add(MflpScheduler.getInstance());

        events.forEach(e -> e.setEnabled(true));

        IWorldLoadListener worldLoadListener = new WorldLoadListener();
        WorldLoadHandler.getInstance().registerWorldLoadPreHandler(worldLoadListener);
        WorldLoadHandler.getInstance().registerWorldLoadPostHandler(worldLoadListener);
        WebsocketHandler.getInstance();

        TickUtils.getInstance().registerClientTickHandler(new ClientTickHandler());
    }
}
