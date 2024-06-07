package me.tolek.mixin.client;

import me.tolek.gui.MflpConfig;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(GameMenuScreen.class)
public class MenuMixin extends Screen {

    protected MenuMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method="initWidgets")
    public void addMFLPButton(CallbackInfo ci) {
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().margin(4, 4, 4, 0);
        GridWidget.Adder adder = gridWidget.createAdder(2);
        adder.add(createButton(Text.literal("MFLP settings"), () -> {
            return new MflpConfig(client);
        }));

        gridWidget.refreshPositions();
        SimplePositioningWidget.setPos(gridWidget, 0, this.height / 6, this.width, this.height, 0.5F, 0.0F);
        gridWidget.forEachChild(this::addDrawableChild);
    }

    private ButtonWidget createButton(Text text, Supplier<Screen> screenSupplier) {
        return ButtonWidget.builder(text, (button) -> {
            this.client.setScreen((Screen)screenSupplier.get());
        }).width(98).build();
    }

}
