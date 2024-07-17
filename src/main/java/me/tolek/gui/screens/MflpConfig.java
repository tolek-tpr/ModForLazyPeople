package me.tolek.gui.screens;

import me.tolek.gui.widgets.InputBoxWidget;
import me.tolek.gui.widgets.ScrollableListWidget;
import me.tolek.gui.widgets.macros.HotkeyButton;
import me.tolek.input.Hotkey;
import me.tolek.modules.Macro.Macro;
import me.tolek.modules.Macro.MacroList;
import me.tolek.gui.widgets.macros.MacroContainerWidget;
import me.tolek.gui.widgets.MenuPickerWidget;
import me.tolek.util.MflpUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class MflpConfig extends Screen {

    public MflpConfig(MinecraftClient client) {
        super(Text.translatable("mflp.configScreen.title"));
        this.client = client;
    }

    private MacroList macroList = MacroList.getInstance();
    private MinecraftClient client;

    @Nullable
    private KeyBinding selectedKeyBinding;

    @Override
    public void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            client.setScreen((Screen) null);
        }).dimensions(width / 2 - 75, height - 29, 150, 20).build());

        MenuPickerWidget mpw = new MenuPickerWidget(10, 22, client);
        mpw.children().forEach(this::addDrawableChild);

        addDrawableChild(ButtonWidget.builder(Text.literal("Create"), (button -> {
            InputBoxWidget ibw = new InputBoxWidget(textRenderer, width / 2 - 75, height / 2 - 40, 150, 20, Text.literal("Name"));

            ibw.setKeyConsumer((keyCode) -> {
                if (keyCode == InputUtil.GLFW_KEY_ESCAPE) {
                    client.setScreen(new MflpConfig(this.client));
                } else if (keyCode == InputUtil.GLFW_KEY_ENTER) {
                    Hotkey e = new Hotkey(MflpUtil.asArray(InputUtil.UNKNOWN_KEY.getCode()));
                    Macro m = new Macro(e, new ArrayList<>(), ibw.getText(), 1);
                    macroList.addMacro(m);
                    client.setScreen(new MflpConfig(this.client));
                }
            });
            addDrawableChild(ibw);
        })).dimensions(236, 22, 70, 20).build());

        ScrollableListWidget slw = new ScrollableListWidget(this.client, width, height - 84, 44, 22);
        slw.setRenderBackground(false);

        for (Macro m : macroList.getMacros()) {
            TextWidget label = new TextWidget(width / 2 - 155,
                    10 - textRenderer.fontHeight / 2,
                    textRenderer.getWidth(m.getName()) + 10, 20,
                    Text.literal(m.getName()), textRenderer);

            Text toggleText = m.getTurnedOn() ? Text.literal("True").formatted(Formatting.GREEN) :
                    Text.literal("False").formatted(Formatting.RED);
            HotkeyButton hotkeyButton = new HotkeyButton(width / 2, 0, m);

            ButtonWidget toggleButton = ButtonWidget.builder(toggleText, (button -> {
                m.setTurnedOn(!m.getTurnedOn());
                client.setScreen(new MflpConfig(client));
            })).dimensions(width / 2 + 82, 0, 60, 20).build();



            Text removeText = Text.translatable("mflp.configScreen.removeButton");
            if (!m.getUneditable()) {
                ButtonWidget removeButton = ButtonWidget.builder(removeText, (button -> {
                    macroList.removeMacro(m);
                    if (client != null) {
                        client.setScreen(new MflpConfig(client));
                    }
                })).dimensions(width / 2 + 144, 0, 70, 20).build();

                Text editText = Text.translatable("mflp.configScreen.editButton");

                TextIconButtonWidget tibw = new TextIconButtonWidget.Builder(editText, (button) -> {
                    client.setScreen(new MflpConfigureMacroScreen(new MflpConfig(client), m));
                }, true).texture(MflpUtil.pencilIcon, 20, 20).dimension(20, 20).build();
                tibw.setPosition(width / 2 - 180, 0);

                
                slw.addRow(label, toggleButton, hotkeyButton, removeButton, tibw);
            } else {
                slw.addRow(label, toggleButton, hotkeyButton);
            }
        }
        addDrawableChild(slw);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        if (macroList.getMacros().isEmpty()) {
            context.drawTextWithShadow(textRenderer, Text.literal("No macros added!"),
                    width / 2 - textRenderer.getWidth("No macros added!") / 2, 46 + textRenderer.fontHeight / 2, 0xffffff);
        }
    }

}
