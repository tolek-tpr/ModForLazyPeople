package me.tolek.gui.screens;

import me.tolek.Macro.Macro;
import me.tolek.Macro.MacroList;
import me.tolek.util.MflpUtil;
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
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class MflpConfig extends Screen {

    public MflpConfig(MinecraftClient client) {
        super(Text.translatable("mflp.configScreen.title"));
        this.client = client;
    }

    private MacroList macroList = MacroList.getInstance();
    private Map<Macro, ButtonWidget> editButtons = new HashMap<>();
    private MinecraftClient client;
    private MflpUtil util = new MflpUtil();

    @Nullable
    private KeyBinding selectedKeyBinding;

    @Nullable
    private ButtonWidget selectedButton;

    private ButtonWidget resetAllButton;

    @Override
    public void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            client.setScreen((Screen) null);
        }).dimensions(width / 2 - 75/*+ 160*/, height - 29, 150, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Macros"), (button) -> {
            client.setScreen(new MflpConfig(this.client));
        }).dimensions(width - width + 10, height - height + 22, 70, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Settings"), (button) -> {
            client.setScreen(new MflpSettingsScreen());
        }).dimensions(width - width + 85, height - height + 22, 70, 20).build());

        int step = 2;
        for (Macro m : macroList.getMacros()) {
            Text keyName = m.getKeyBinding().getBoundKeyLocalizedText();

            ButtonWidget bw = ButtonWidget.builder(keyName, (button -> {
                selectedKeyBinding = m.getKeyBinding();
                selectedButton = editButtons.get(m);

                this.update(m.getKeyBinding(), m);
            })).dimensions(width / 2 - 155 + 160, 20 + step, 90, 20).build();
            editButtons.put(m, bw);

            addDrawableChild(bw);

            Text toggleText = m.getTurnedOn() ? Text.literal("True").formatted(Formatting.GREEN) :
                    Text.literal("False").formatted(Formatting.RED);
            ButtonWidget toggleButton = ButtonWidget.builder(toggleText, (button -> {
                m.setTurnedOn(!m.getTurnedOn());
                client.setScreen(new MflpConfig(this.client));
            })).dimensions(width / 2 - 155 + 252, 20 + step, 60, 20).build();
            addDrawableChild(toggleButton);

            Text removeText = Text.translatable("mflp.configScreen.removeButton");
            if (!m.getUneditable()) {
                ButtonWidget removeButton = ButtonWidget.builder(removeText, (button -> {
                    macroList.removeMacro(m);
                    if (client != null) {
                        client.setScreen(new MflpConfig(client));
                    }
                })).dimensions(width / 2 - 155 + 314, 20 + step, 70, 20).build();
                addDrawableChild(removeButton);

                Text editText = Text.translatable("mflp.configScreen.editButton");
                ButtonWidget editButton = ButtonWidget.builder(editText, (button -> {
                    if (client != null) {
                        client.setScreen(new MflpConfigureMacroScreen(this, m));
                    }
                })).dimensions(width / 2 - 155 + 386, 20 + step, 60, 20).build();
                addDrawableChild(editButton);
            }


            step += 22;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        int step = 0;
        for (Macro m : macroList.getMacros()) {
            Text macroName = Text.of(m.getName());
            context.drawTextWithShadow(textRenderer, macroName, width / 2 - 155, 24 + step, 0xffffff);
            step += 22.5;
        }
    }

    private void update(KeyBinding keyBinding, Macro m) {
        ButtonWidget editButton = editButtons.get(m);
        Text keyName = m.getKeyBinding().getBoundKeyLocalizedText();

        editButton.setMessage(keyName);

        if (this.selectedKeyBinding == keyBinding) {
            editButton.setMessage(Text.literal("> ").append(editButton.getMessage().copy().formatted(new Formatting[]{Formatting.WHITE, Formatting.UNDERLINE})).append(" <").formatted(Formatting.YELLOW));
        } else {
            editButton.setMessage(keyName);
        }
    }

    private void update(ButtonWidget editButton, KeyBinding keyBinding/*, InputUtil.Key key*/) {
        editButton.setMessage(keyBinding.getBoundKeyLocalizedText());

        if (this.selectedKeyBinding == keyBinding) {
            editButton.setMessage(Text.literal("> ").append(editButton.getMessage().copy().formatted(new Formatting[]{Formatting.WHITE, Formatting.UNDERLINE})).append(" <").formatted(Formatting.YELLOW));
        } else {
            editButton.setMessage(keyBinding.getBoundKeyLocalizedText());
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.selectedKeyBinding != null) {
            KeyBinding kb = macroList.setKeyBinding(this.selectedKeyBinding, InputUtil.Type.MOUSE.createFromCode(button));
            if (kb != null) this.update(selectedButton, kb);
            this.selectedKeyBinding = null;
            this.selectedButton = null;

            return true;
        } else {
            return super.mouseClicked(mouseX, mouseY, button);
        }
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.selectedKeyBinding != null) {
            KeyBinding kb = null;
            if (keyCode == 256) {
                kb = macroList.setKeyBinding(this.selectedKeyBinding, InputUtil.UNKNOWN_KEY);
            } else {
                kb = macroList.setKeyBinding(this.selectedKeyBinding, InputUtil.fromKeyCode(keyCode, scanCode));
            }

            if (kb != null) this.update(selectedButton, kb);
            this.selectedKeyBinding = null;
            this.selectedButton = null;
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

}
