package me.tolek.commands;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.ServerTickManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.List;

import static net.minecraft.server.command.CommandManager.*;

public class MflpCommand implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("mflp")
                .executes(context -> {
                    return 1;
                }).then(literal("freezeGame").requires(source -> {
                    return source.hasPermissionLevel(3);
                }).executes(context -> {
                    return freezeGame((ServerCommandSource) context.getSource());
                }))));
    }



    private int freezeGame(ServerCommandSource source) {
        boolean frozen = source.getServer().getTickManager().isFrozen();

        ServerTickManager serverTickManager = source.getServer().getTickManager();
        if (frozen) {
            if (serverTickManager.isSprinting()) {
                serverTickManager.stopSprinting();
            }

            if (serverTickManager.isStepping()) {
                serverTickManager.stopStepping();
            }
        }

        serverTickManager.setFrozen(!frozen);
        if (!frozen) {
            source.sendFeedback(() -> {
                return Text.translatable("commands.tick.status.frozen");
            }, true);
        } else {
            source.sendFeedback(() -> {
                return Text.translatable("commands.tick.status.running");
            }, true);
        }

        return frozen ? 1 : 0;
    }
}
