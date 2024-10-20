package me.tolek;

import me.tolek.event.EventImpl;
import me.tolek.files.MflpConfigImpl;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ModForLazyPeople implements ModInitializer {

	public static final String MOD_ID = "modforlazypeople";
    public static final Logger LOGGER = LoggerFactory.getLogger("MFLP");

	private final ArrayList<EventImpl> events = new ArrayList<>();

	@Override
	public void onInitialize() {
		LOGGER.info("Running mod for lazy people.");

		events.add(new MflpConfigImpl());

		events.forEach(e -> e.setEnabled(true));
	}
}