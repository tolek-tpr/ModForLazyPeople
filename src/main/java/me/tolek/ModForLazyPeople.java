package me.tolek;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.*;

public class ModForLazyPeople implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("modforlazypeople");

	@Override
	public void onInitialize() {
		LOGGER.info("Running mod for lazy people.");

	}
}