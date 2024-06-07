package me.tolek;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

public class HotkeyExecutor implements ClientModInitializer {

    private static MflpConfigScreen mflpConfigScreen = MflpConfigScreen.getInstance();
    private Util util = new Util();

    private boolean gameFrozen = false;

    private final String FREEZE_COMMAND = "tick freeze";
    private final String UNFREEZE_COMMAND = "/tick unfreeze";
    private final String TICKSTEP_COMMAND = "/tick step";

    private final String[] WORLD_SETUP_COMMANDS = {
            "/gamerule doTileDrops false",
            "/gamerule doTraderSpawning false",
            "/gamerule doWeatherCycle false",
            "/gamerule doDaylightCycle false",
            "/gamerule doMobSpawning false",
            //"/gamerule doContainerDrops false",
            "/time set noon",
            "/weather clear"
    };

    private static KeyBinding tickFreezeKeybinding = new KeyBinding(
            "mflp.keybind.tickFreeze",
            InputUtil.Type.KEYSYM,
            mflpConfigScreen.getFreezeKey().getCode(),
            "mflp.keybindCategory.MFLP"
    );
    private static KeyBinding tickFreezeStepKey = new KeyBinding(
            "mflp.keybind.tickStep",
            InputUtil.Type.KEYSYM,
            mflpConfigScreen.getTickStepKey().getCode(),
            "mflp.keybindCategory.MFLP"
    );

    private static KeyBinding worldSetupKeybinding = new KeyBinding(
            "mflp.keybind.setupWorld",
            InputUtil.Type.KEYSYM,
            mflpConfigScreen.getWorldSetupKey().getCode(),
            "mflp.keybindCategory.MFLP"
    );

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!mflpConfigScreen.getToggleBetterFreeze()) return;
            ClientPlayerEntity p = client.player;

            try {
                while (tickFreezeKeybinding.wasPressed()) {
                    // this code is only client sided
                    //ServerTickManager stm = MinecraftClient.getInstance().getServer().getTickManager();
                    //boolean frozen = Boolean.parseBoolean(null);
                    //frozen = stm.isFrozen();

                    //if (frozen == Boolean.parseBoolean(null)) {
                    if (gameFrozen) {
                        util.sendCommand(p, UNFREEZE_COMMAND);
                        gameFrozen = !gameFrozen;
                        System.out.println("Using client side command");
                    } else {
                        util.sendCommand(p, FREEZE_COMMAND);
                        gameFrozen = !gameFrozen;
                        System.out.println("Using client side command");
                    }
                    /*} else {
                        if (frozen) {
                            util.sendCommand(p, UNFREEZE_COMMAND);
                            System.out.println("Using server side command");
                        } else {
                            util.sendCommand(p, FREEZE_COMMAND);
                            System.out.println("Using server side command");
                        }
                    }*/
                }
                while (tickFreezeStepKey.wasPressed()) {
                    util.sendCommand(p, TICKSTEP_COMMAND);
                }
                while (worldSetupKeybinding.wasPressed()) {
                    for (String command : WORLD_SETUP_COMMANDS) {
                        util.sendCommand(p, command);
                    }
                }
            } catch(Exception e) {
                client.player.sendMessage(Text.literal("A exception was thrown (Do you have op?)"));
            }
        });
    }

    public static void updateHotkeys() {
        tickFreezeKeybinding = new KeyBinding(
                "mflp.keybind.tickFreeze",
                InputUtil.Type.KEYSYM,
                mflpConfigScreen.getFreezeKey().getCode(),
                "mflp.keybindCategory.MFLP"
        );
        tickFreezeStepKey = new KeyBinding(
                "mflp.keybind.tickStep",
                InputUtil.Type.KEYSYM,
                mflpConfigScreen.getTickStepKey().getCode(),
                "mflp.keybindCategory.MFLP"
        );

        worldSetupKeybinding = new KeyBinding(
                "mflp.keybind.setupWorld",
                InputUtil.Type.KEYSYM,
                mflpConfigScreen.getWorldSetupKey().getCode(),
                "mflp.keybindCategory.MFLP"
        );
    }

}
