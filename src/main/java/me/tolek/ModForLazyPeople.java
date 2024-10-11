package me.tolek;

import me.tolek.event.EventImpl;
import me.tolek.events.AfkDetector;
import me.tolek.files.MflpConfigImpl;
import me.tolek.modules.Macro.MacroExecutor;
import me.tolek.modules.autoReply.AutoReplyExecutor;
import me.tolek.modules.settings.executor.AutoWelcomeBackImpl;
import me.tolek.modules.settings.executor.AutoWelcomeImpl;
import me.tolek.network.MflpPlayersWorker;
import me.tolek.network.MflpServerConnection;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ModForLazyPeople implements ModInitializer {

	public static final String MOD_ID = "modforlazypeople";
    public static final Logger LOGGER = LoggerFactory.getLogger("modforlazypeople");
	public static final MflpServerConnection serverConnection = new MflpServerConnection();

	private final ArrayList<EventImpl> events = new ArrayList<>();

	@Override
	public void onInitialize() {
		LOGGER.info("Running mod for lazy people.");

		events.add(new AfkDetector());
		events.add(new MacroExecutor());
		events.add(new AutoWelcomeBackImpl());
		events.add(new AutoWelcomeImpl());
		events.add(new AutoReplyExecutor());
		events.add(new MflpConfigImpl());
		events.add(MflpPlayersWorker.getInstance());

		events.forEach(e -> e.setEnabled(true));
	}
}