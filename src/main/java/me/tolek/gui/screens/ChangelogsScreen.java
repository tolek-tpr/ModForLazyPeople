package me.tolek.gui.screens;

import me.tolek.gui.widgets.ScrollableListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

// This will NOT be translated. (for now ;))
public class ChangelogsScreen extends Screen {
    public ChangelogsScreen(Screen parent) {
        super(Text.translatable("mflp.changelogsScreen.title"));
    }

    private ScrollableListWidget slw;
    private Screen parent;

    @Override
    protected void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> close()).dimensions(width / 2 - 75, height - 29, 150, 20).build());

        slw = new ScrollableListWidget(this.client, width, height - 84, 44, 22);
        slw.setRenderBackground(false);

        // -- CHANGELOGS START -- \\

        addVersion("v3.0.0 - THE PARTY UPDATE", "ADDED: Party system (/party commands)", "ADDED: This menu", "IMPROVED: MFLP Network (new /mflpnet command)");

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
            TextWidget changeLabel = new TextWidget(width / 2 - 155,
                    10 - textRenderer.fontHeight / 2, textRenderer.getWidth(changeText) + 10, 20, changeText, textRenderer);
            slw.addRow(changeLabel);
        }
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(parent);
    }
}
