package me.tolek;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.tolek.event.EventImpl;
import me.tolek.events.AfkDetector;
import me.tolek.files.MflpConfigImpl;
import me.tolek.modules.Macro.MacroExecutor;
import me.tolek.modules.autoReply.AutoReplyExecutor;
import me.tolek.modules.settings.executor.AutoWelcomeBackImpl;
import me.tolek.modules.settings.executor.AutoWelcomeImpl;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static net.minecraft.server.command.CommandManager.*;

public class ModForLazyPeople implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("modforlazypeople");

	private ArrayList<EventImpl> events = new ArrayList<>();

	@Override
	public void onInitialize() {
		LOGGER.info("Running mod for lazy people.");

		events.add(new AfkDetector());
		events.add(new MacroExecutor());
		events.add(new AutoWelcomeBackImpl());
		events.add(new AutoWelcomeImpl());
		events.add(new AutoReplyExecutor());
		events.add(new MflpConfigImpl());

		events.forEach(e -> e.setEnabled(true));
	}
}