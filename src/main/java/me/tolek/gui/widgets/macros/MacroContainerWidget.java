package me.tolek.gui.widgets.macros;

import me.tolek.modules.macro.Macro;
import me.tolek.modules.macro.MacroList;
import me.tolek.gui.screens.MflpConfig;
import me.tolek.gui.screens.MflpConfigureMacroScreen;
import me.tolek.util.MflpUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class MacroContainerWidget extends ContainerWidget {

    private List<ClickableWidget> children = new ArrayList<>();
    private MacroList macroList = MacroList.getInstance();
    @Nullable
    private KeyBinding selectedKeyBinding;

    @Nullable
    private ButtonWidget selectedButton;
    private Map<Macro, ButtonWidget> editButtons = new HashMap<>();
    private Macro m;
    private int x;
    private int y;
    private TextRenderer tx;

    public MacroContainerWidget(int x, int y, MinecraftClient client, KeyBinding selectedKeyBinding, Macro m, TextRenderer tx) {
        super(x, y, 150, 20, Text.literal("test"));
        this.selectedKeyBinding = selectedKeyBinding;
        this.m = m;
        this.x = x;
        this.y = y;
        this.tx = tx;

        Text keyName = m.getKeyBinding().getBoundKeyLocalizedText();

        ButtonWidget bw = ButtonWidget.builder(keyName, (button -> {
            this.selectedKeyBinding = m.getKeyBinding();
            selectedButton = editButtons.get(m);

            this.update(m.getKeyBinding(), m);
        })).dimensions(x, y, 80, 20).build();
        editButtons.put(m, bw);

        addChild(bw);

        Text toggleText = m.getTurnedOn() ? Text.translatable("mflp.true").formatted(Formatting.GREEN) :
                Text.translatable("mflp.false").formatted(Formatting.RED);
        ButtonWidget toggleButton = ButtonWidget.builder(toggleText, (button -> {
            m.setTurnedOn(!m.getTurnedOn());
            client.setScreen(new MflpConfig(client));
        })).dimensions(x + 82, y, 60, 20).build();
        addChild(toggleButton);

        Text removeText = Text.translatable("mflp.remove");
        if (!m.getUneditable()) {
            ButtonWidget removeButton = ButtonWidget.builder(removeText, (button -> {
                macroList.removeMacro(m);
                if (client != null) {
                    client.setScreen(new MflpConfig(client));
                }
            })).dimensions(x + 144, y, 70, 20).build();
            addChild(removeButton);

            Text editText = Text.translatable("mflp.configScreen.editButton");

            TextIconButtonWidget tibw = new TextIconButtonWidget.Builder(editText, (button) ->
                    client.setScreen(new MflpConfigureMacroScreen(new MflpConfig(client), m)), true)
                    .texture(MflpUtil.pencilIcon, 20, 20).dimension(20, 20).build();
            tibw.setPosition(x - 180, y);
            addChild(tibw);
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

    private void update(ButtonWidget editButton, KeyBinding keyBinding) {
        editButton.setMessage(keyBinding.getBoundKeyLocalizedText());

        if (this.selectedKeyBinding == keyBinding) {
            editButton.setMessage(Text.literal("> ").append(editButton.getMessage().copy().formatted(new Formatting[]{Formatting.WHITE, Formatting.UNDERLINE})).append(" <").formatted(Formatting.YELLOW));
        } else {
            editButton.setMessage(keyBinding.getBoundKeyLocalizedText());
        }
    }

    @Override
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

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        System.out.println("Key PRESS");
        if (this.selectedKeyBinding != null) {
            KeyBinding kb = null;
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                kb = macroList.setKeyBinding(this.selectedKeyBinding, InputUtil.UNKNOWN_KEY);
                System.out.println("escape");
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

    @Override
    public List<? extends ClickableWidget> children() {
        return children;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTextWithShadow(tx, m.getName(), x - 155, y + 10 - tx.fontHeight / 2, 0xffffff);
        children.forEach(c -> c.render(context, mouseX, mouseY, delta));
    }

    public void addChild(ClickableWidget child) {
        children.add(child);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
