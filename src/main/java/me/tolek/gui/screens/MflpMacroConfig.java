package me.tolek.gui.screens;

import me.tolek.gui.widgets.InputBoxWidget;
import me.tolek.modules.macro.Macro;
import me.tolek.modules.macro.MacroList;
import me.tolek.gui.widgets.macros.MacroContainerWidget;
import me.tolek.gui.widgets.MenuPickerWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class MflpMacroConfig extends Screen {

    public MflpMacroConfig(MinecraftClient client) {
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

        addDrawableChild(ButtonWidget.builder(Text.translatable("mflp.create"), (button -> {
            InputBoxWidget ibw = new InputBoxWidget(textRenderer, width / 2 - 75, height / 2 - 40, 150, 20, Text.translatable("mflp.name"));

            ibw.setKeyConsumer((keyCode) -> {
                if (keyCode == InputUtil.GLFW_KEY_ESCAPE) {
                    client.setScreen(new MflpMacroConfig(this.client));
                } else if (keyCode == InputUtil.GLFW_KEY_ENTER) {
                    KeyBinding kb = new KeyBinding("mflp.keybinding.undefined",
                            InputUtil.UNKNOWN_KEY.getCode(),
                            "mflp.keybindCategory.MFLP");
                    Macro m = new Macro(kb, new ArrayList<>(), ibw.getText(), 1);
                    m.setKey(InputUtil.UNKNOWN_KEY.getCode());
                    macroList.addMacro(m);
                    client.setScreen(new MflpMacroConfig(this.client));
                }
            });
            addDrawableChild(ibw);
        })).dimensions(236, 22, 70, 20).build());

        int scaledWidth = client.getWindow().getScaledWidth();
        int step = 2;
        for (Macro m : macroList.getMacros()) {
            MacroContainerWidget mcw = new MacroContainerWidget(scaledWidth / 2, 42 + step, this.client,
                    this.selectedKeyBinding, m, textRenderer);
            addDrawableChild(mcw);
            mcw.children().forEach(this::addDrawableChild);
            mcw.keyBindingConsumer = (kb) -> {this.selectedKeyBinding = kb;};

            step += 22;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        if (macroList.getMacros().isEmpty()) {
            context.drawTextWithShadow(textRenderer, Text.translatable("mflp.macro.noMacrosAdded"),
                    width / 2 - textRenderer.getWidth(Text.translatable("mflp.macro.noMacrosAdded")) / 2, 46 + textRenderer.fontHeight / 2, 0xffffff);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.selectedKeyBinding != null && keyCode == GLFW.GLFW_KEY_ESCAPE) {
            children().forEach((c) -> {
                if (c instanceof MacroContainerWidget) c.keyPressed(keyCode, scanCode, modifiers);
            });
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void close() {
        if (this.selectedKeyBinding != null) return;
        this.client.setScreen(null);
    }

}
