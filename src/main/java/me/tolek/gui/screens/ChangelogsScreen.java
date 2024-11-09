package me.tolek.gui.screens;

import me.tolek.gui.widgets.ScrollableListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

// This will NOT be translated. (for now ;)). UPDATE: no.
public class ChangelogsScreen extends Screen {

    public ChangelogsScreen(Screen parent) {
        super(Text.translatable("mflp.changelogsScreen.title"));
        this.parent = parent;
    }

    private ScrollableListWidget slw;
    private final Screen parent;

    @Override
    protected void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> close()).dimensions(width / 2 - 75, height - 29, 150, 20).build());

        slw = new ScrollableListWidget(this.client, width, height - 84, 44, 22);

        // -- CHANGELOGS START -- \\

        addVersion("v3.0.2 - THE UPDATE UPDATE", "ADDED: Missing promotion/demotion buttons in Party GUI (/party manage) by @BeefyAndTheDucks",
                "ADDED: Player heads in Party GUI (/party manage) by @BeefyAndTheDucks",
                "ADDED: Settings for displaying the update radius of different redstone components by @tolek-tpr",
                "ADDED: A Hotkey tab in the main GUI allowing to toggle boolean settings and switch list settings by @tolek-tpr",
                "Added a Colors tab in the main GUI, for setting the colors for the Update Radius settings by @tolek-tpr");
        addVersion("v3.0.1 - THE MACRO UPDATE", "ADDED: New setting to macros, the ability to use macros only on specific servers by @tolek-tpr",
                "ADDED: A new command - /mflpconfig - to open the config screen by @tolek-tpr", "FIXED: MFLP Icons not showing up by @tolek-tpr",
                "FIXED: Party GUI not updating by @BeefyAndTheDucks");
        addVersion("v3.0.0 - THE PARTY UPDATE", "ADDED: Party system (/party commands)", "ADDED: This menu", "IMPROVED: MFLP Network (new /mflpnet command)");
        addVersion("v2.6.9 - BUG FIXES AGAIN", "FIXED: Default UnAfk RegEx by @BeefyAndTheDucks", "FIXED: MFLP icon not displaying when being far away from the player by @tolek-tpr");
        addVersion("v2.6.8 - TRANSLATIONS UPDATE", "ADDED: Translations for Polish, Danish and update the English ones by @tolek-tpr and @BeefyAndTheDucks",
                "FIXED: Auto reply replying to your own messages causing infinite loops by @tolek-tpr", "ADDED: A discord button in the MFLP screen by @BeefyAndTheDucks",
                "ADDED: Settings for toggling the MFLP icon in both name tags and tab");
        addVersion("v2.6.7 - THE BEEFY UPDATE", "FIXED: Small grammatical errors by @BeefyAndTheDucks", "FIXED: MFLP crashing servers by @BeefyAndTheDucks",
                "ADDED: User feedback to all RegEx fields by @BeefyAndTheDucks", "FIXED: Default settings not using the %u syntax in RegEx fields by @BeefyAndTheDucks",
                "BUMPED: Fabric version to 0.16.7 by @BeefyAndTheDucks");
        addVersion("v2.6.6 - THE INTEGRATION UPDATE", "ADDED: Custom join and un afk messages per server by @tolek-tpr",
                "ADDED: RegEx support for join and un afk messages by @BeefyAndTheDucks");
        addVersion("v2.6.5 - THE USER UPDATE", "ADDED: A icon next to a MFLP user's name and in tab by @tolek-tpr");
        addVersion("v2.6.4 - BUG FIXES", "FIXED: The ESC key not working in the pause menu by @tolek-tpr");
        addVersion("v2.6.3 - FILE UPDATE", "ADDED: A file version parameter for easier switching between file versions by @tolek-tpr");
        addVersion("v2.6.2 - THE PLAYER UPDATE", "ADDED: A feature where putting %p in the wb message fields says the players name along with your message by @tolek-tpr");
        addVersion("v2.6.1 - WB FIXES", "FIXED: Auto wb not working after auto welcome executes by @tolek-tpr");
        addVersion("v2.6.0 - SCREEN FIXES", "FIXED: Settings screen to use a scrollable list instead of going off screen on smaller resolutions by @tolek-tpr",
                "FIXED: Auto wb spamming chat when using chat history keeping mods");


        // -- CHANGELOGS  END  -- \\

        addDrawableChild(slw);
    }

    private void addVersion(String versionName, String... changes) {
        MutableText labelText = Text.literal(versionName).formatted(Formatting.BOLD, Formatting.GREEN);
        TextWidget label = new TextWidget(width / 2 - 155,
                10 - textRenderer.fontHeight / 2, textRenderer.getWidth(labelText) + 10, 20, labelText, textRenderer);
        slw.addRow(label);

        for (String change : changes) {
            MutableText changeText = Text.literal(" - ").append(change).formatted(Formatting.ITALIC);
            List<OrderedText> wrappedText = textRenderer.wrapLines(changeText, 350);

            for (OrderedText text : wrappedText) {
                StringBuilder builder = new StringBuilder();
                text.accept((index, style, c) -> {
                    builder.appendCodePoint(c);
                    return true;
                });
                Text text2 = Text.literal(builder.toString());

                TextWidget changeLabel = new TextWidget(width / 2 - 155,
                        10 - textRenderer.fontHeight / 2, 350, 20, text2, textRenderer);
                slw.addRow(changeLabel);
            }
        }
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(parent);
    }

}
