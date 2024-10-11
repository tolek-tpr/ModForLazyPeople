package me.tolek;

import me.tolek.event.EventImpl;
import me.tolek.events.AfkDetector;
import me.tolek.files.MflpConfigImpl;
import me.tolek.modules.Macro.MacroExecutor;
import me.tolek.modules.autoReply.AutoReplyExecutor;
import me.tolek.modules.settings.executor.AutoWelcomeBackImpl;
import me.tolek.modules.settings.executor.AutoWelcomeImpl;
import me.tolek.network.MflpPlayersWorker;
import net.fabricmc.api.ClientModInitializer;

import java.util.ArrayList;

public class ModForLazyPeopleClient implements ClientModInitializer {

    private final ArrayList<EventImpl> events = new ArrayList<>();

    @Override
    public void onInitializeClient() {
        events.add(new AfkDetector());
        events.add(new MacroExecutor());
        events.add(new AutoWelcomeBackImpl());
        events.add(new AutoWelcomeImpl());
        events.add(new AutoReplyExecutor());
        events.add(MflpPlayersWorker.getInstance());

        events.forEach(e -> e.setEnabled(true));
    }
}
