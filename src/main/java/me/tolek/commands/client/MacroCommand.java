package me.tolek.commands.client;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.tolek.modules.Macro.Macro;
import me.tolek.modules.Macro.MacroList;
import me.tolek.util.MflpUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;


import java.util.List;

public class MacroCommand implements ClientModInitializer {

    private MacroList macroList = MacroList.getInstance();
    private MflpUtil mflpUtil = new MflpUtil();

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("macro")
                    .then(ClientCommandManager.literal("createMacro")
                            .then(ClientCommandManager.argument("Macro Name", StringArgumentType.string())
                                    .then(ClientCommandManager.argument("commands", StringArgumentType.string())
                                            .executes(context -> {
                                                final String macroName = StringArgumentType.getString(context, "Macro Name");
                                                final String commands = StringArgumentType.getString(context, "commands");
                                                return createMacro(context.getSource(), macroName, commands);
                                            })
                                    )
                            )
                    )
                    .then(ClientCommandManager.literal("removeMacro")
                            .then(ClientCommandManager.argument("Macro Name", StringArgumentType.string())
                                    .executes(context -> {
                                        final String macroName = StringArgumentType.getString(context, "Macro Name");
                                        return removeMacro(context.getSource(), macroName);
                                    })
                            )
                    )
                    .then(ClientCommandManager.literal("addCommands")
                            .then(ClientCommandManager.argument("Macro Name", StringArgumentType.string())
                                    .then(ClientCommandManager.argument("commands", StringArgumentType.string())
                                            .executes(context -> {
                                                final String macroName = StringArgumentType.getString(context, "Macro Name");
                                                final String commands = StringArgumentType.getString(context, "commands");
                                                return addCommandToMacro(context.getSource(), macroName, commands);
                                            })
                                    )
                            )
                    )
                    .then(ClientCommandManager.literal("removeCommands")
                            .then(ClientCommandManager.argument("Macro Name", StringArgumentType.string())
                                    .then(ClientCommandManager.argument("commands", StringArgumentType.string())
                                            .executes(context -> {
                                                final String macroName = StringArgumentType.getString(context, "Macro Name");
                                                final String commands = StringArgumentType.getString(context, "commands");
                                                return removeCommandFromMacro(context.getSource(), macroName, commands);
                                            })
                                    )
                            )
                    )
                    .then(ClientCommandManager.literal("renameMacro")
                            .then(ClientCommandManager.argument("Macro Name", StringArgumentType.string())
                                    .then(ClientCommandManager.argument("New Macro Name", StringArgumentType.string())
                                            .executes(context -> {
                                                final String macroName = StringArgumentType.getString(context, "Macro Name");
                                                final String newMacroName = StringArgumentType.getString(context, "New Macro Name");
                                                return renameMacro(context.getSource(), macroName, newMacroName);
                                            })
                                    )
                            )
                    )
                    .then(ClientCommandManager.literal("repeatMacro")
                            .then(ClientCommandManager.argument("Macro Name", StringArgumentType.string())
                                    .then(ClientCommandManager.argument("Amount", IntegerArgumentType.integer())
                                            .executes(context -> {
                                                final String macroName = StringArgumentType.getString(context, "Macro Name");
                                                final int amount = IntegerArgumentType.getInteger(context, "Amount");
                                                return repeatMacro(context.getSource(), macroName, amount);
                                            })
                                    )
                            )
                    )
                    .then(ClientCommandManager.literal("makeRepeating")
                            .then(ClientCommandManager.argument("Macro Name", StringArgumentType.string())
                                    .then(ClientCommandManager.argument("Amount", IntegerArgumentType.integer())
                                            .executes(context -> {
                                                final String macroName = StringArgumentType.getString(context, "Macro Name");
                                                final int amount = IntegerArgumentType.getInteger(context, "Amount");
                                                return addRepeatingAttribute(context.getSource(), macroName, amount);
                                            })
                                    )
                            )
                    )
            );
        });
    }

    private int noSuchMacroExistsError(FabricClientCommandSource ctx) {
        Text message = Text.literal("The macro does not exist!").formatted(Formatting.RED);
        mflpUtil.sendMessage(ctx.getClient().player, message);
        return 0;
    }

    private int addRepeatingAttribute(FabricClientCommandSource ctx, String macroName, int amount) {
        macroName = macroName.replace('-', ' ');

        Macro ml = null;

        for(Macro m : macroList.getMacros()) {
            if (m.getName().equals(macroName)) {
                ml = m;
                m.setRepeatAmount(amount);
            }
        }

        if (ml == null) return noSuchMacroExistsError(ctx);

        Text amountText = Text.literal(macroName).formatted(Formatting.BOLD).formatted(Formatting.GOLD);
        Text message = Text.literal("Added a repeat flag to macro ").append(amountText);
        Text tooltip = Text.literal("Commands: " + ml.getCommands()).formatted(Formatting.AQUA);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip);
        message = message.copy().setStyle(message.getStyle().withHoverEvent(hoverEvent));
        mflpUtil.sendMessage(ctx.getClient().player, message);

        return 1;
    }

    private int repeatMacro(FabricClientCommandSource ctx, String macroName, int amount) {
        macroName = macroName.replace('-', ' ');

        Macro ml = null;

        for(Macro m : macroList.getMacros()) {
            if (m.getName().equals(macroName)) {
                ml = m;
                for (int i = 0; i < amount; ++i) {
                    m.runMacro(ctx.getClient().player);
                }
            }
        }

        if (ml == null) return noSuchMacroExistsError(ctx);

        Text amountText = Text.literal(String.valueOf(amount)).formatted(Formatting.BOLD).formatted(Formatting.GOLD);
        Text message = Text.literal("Repeating macro ").append(amountText).append(Text.literal(" times."));
        Text tooltip = Text.literal("Commands: " + ml.getCommands()).formatted(Formatting.AQUA);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip);
        message = message.copy().setStyle(message.getStyle().withHoverEvent(hoverEvent));
        mflpUtil.sendMessage(ctx.getClient().player, message);

        return 1;
    }

    private int renameMacro(FabricClientCommandSource ctx, String macroName, String newMacroName) {
        macroName = macroName.replace('-', ' ');
        newMacroName = newMacroName.replace('-', ' ');

        Macro ml = null;

        for(Macro m : macroList.getMacros()) {
            if (m.getName().equals(macroName)) {
                ml = m;
                m.setName(newMacroName);
            }
        }

        if (ml == null) return noSuchMacroExistsError(ctx);

        Text macroNameText = Text.literal(newMacroName).formatted(Formatting.BOLD).formatted(Formatting.GOLD);
        Text message = Text.literal("Renamed macro to: ").append(macroNameText);
        Text tooltip = Text.literal("Commands: " + ml.getCommands()).formatted(Formatting.AQUA);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip);
        message = message.copy().setStyle(message.getStyle().withHoverEvent(hoverEvent));
        mflpUtil.sendMessage(ctx.getClient().player, message);

        return 1;
    }

    private int removeCommandFromMacro(FabricClientCommandSource ctx, String macroName, String unparesedCommand) {
        unparesedCommand = unparesedCommand.replace('-', ' ');
        List<String> commands = List.of(unparesedCommand.split("\\."));
        macroName = macroName.replace('-', ' ');

        Macro ml = null;

        for (Macro m : macroList.getMacros()) {
            if (m.getName().equals(macroName)) {
                ml = m;
                boolean a = m.removeCommands(commands);
                if (!a) {
                    Text message = Text.literal("The macro does not contain this command!").formatted(Formatting.RED);
                    mflpUtil.sendMessage(ctx.getClient().player, message);

                    return 1;
                }
            }
        }

        if (ml == null) return noSuchMacroExistsError(ctx);

        Text macroNameText = Text.literal(macroName).formatted(Formatting.BOLD).formatted(Formatting.GOLD);
        Text message = Text.literal("Removed commands from macro: ").append(macroNameText);
        Text tooltip = Text.literal("Commands: " + ml.getCommands()).formatted(Formatting.AQUA);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip);
        message = message.copy().setStyle(message.getStyle().withHoverEvent(hoverEvent));
        mflpUtil.sendMessage(ctx.getClient().player, message);

        return 1;
    }

    private int addCommandToMacro(FabricClientCommandSource ctx, String macroName, String unparesedCommand) {
        unparesedCommand = unparesedCommand.replace('-', ' ');
        List<String> commands = List.of(unparesedCommand.split("\\."));
        macroName = macroName.replace('-', ' ');

        Macro ml = null;

        for (Macro m : macroList.getMacros()) {
            if (m.getName().equals(macroName)) {
                ml = m;
                m.addCommands(commands);
            }
        }

        if (ml == null) return noSuchMacroExistsError(ctx);

        Text macroNameText = Text.literal(macroName).formatted(Formatting.BOLD).formatted(Formatting.GOLD);
        Text message = Text.literal("Added commands to macro: ").append(macroNameText);
        Text tooltip = Text.literal("Commands: " + ml.getCommands()).formatted(Formatting.AQUA);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip);
        message = message.copy().setStyle(message.getStyle().withHoverEvent(hoverEvent));
        mflpUtil.sendMessage(ctx.getClient().player, message);

        return 1;
    }

    private int removeMacro(FabricClientCommandSource ctx, String macroName) {
        String name = macroName.replace('-', ' ');
        for (Macro m : macroList.getMacros()) {
            if (m.getName().equals(name)) {
                macroList.removeMacro(m);
                Text macroNameText = Text.literal(name).formatted(Formatting.GOLD);
                ctx.sendFeedback(Text.literal("Removed macro: ").append(macroNameText));
                return 1;
            }
        }
        ctx.sendFeedback(Text.literal("Could not find macro!").formatted(Formatting.RED));
        return 1;
    }

    private int createMacro(FabricClientCommandSource ctx, String macroName, String unparesedCommand) {
        unparesedCommand = unparesedCommand.replace('-', ' ');
        List<String> commands = List.of(unparesedCommand.split("\\."));
        macroName = macroName.replace('-', ' ');

        KeyBinding keyBinding = new KeyBinding("mflp.keybinding.undefined", InputUtil.UNKNOWN_KEY.getCode(), "mflp.keybindCategory.MFLP");

        Macro m = new Macro(keyBinding, commands, macroName, 1);
        m.setKey(InputUtil.UNKNOWN_KEY.getCode());
        macroList.addMacro(m);

        Text macroNameText = Text.literal(macroName).formatted(Formatting.BOLD).formatted(Formatting.GOLD);
        Text message = Text.literal("Added macro: ").append(macroNameText);
        Text tooltip = Text.literal("Commands: " + commands).formatted(Formatting.AQUA);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip);
        message = message.copy().setStyle(message.getStyle().withHoverEvent(hoverEvent));
        mflpUtil.sendMessage(ctx.getClient().player, message);

        return 1;
    }
}
