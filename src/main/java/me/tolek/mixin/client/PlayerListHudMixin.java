package me.tolek.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.network.WebSocketServerHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {

    @Unique
    //private final MflpPlayersWorker worker = MflpPlayersWorker.getInstance();
    private final WebSocketServerHandler serverHandler = WebSocketServerHandler.getInstance();
    @Unique
    private final Identifier logo = new Identifier("modforlazypeople", "mflp/user_logo");
    private final MflpSettingsList settingsList = MflpSettingsList.getInstance();

    @Inject(method = "render", at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/PlayerListHud;renderLatencyIcon(Lnet/minecraft/client/gui/DrawContext;IIILnet/minecraft/client/network/PlayerListEntry;)V"))
    private void onRenderHud(DrawContext context, int scaledWindowWidth, Scoreboard scoreboard, ScoreboardObjective objective,
                            CallbackInfo ci, @Local(ordinal = 8) int m, @Local(ordinal = 16) int y,
                             @Local(ordinal = 0, index = 13) boolean bl,
                             @Local(ordinal = 17) int z, @Local(ordinal = 0, index = 27) PlayerListEntry playerListEntry2) {
        renderMflpTag(context,  m, y - (bl ? 9 : 0), z, playerListEntry2);
    }

    public void renderMflpTag(DrawContext context, int width, int x, int y, PlayerListEntry entry) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || !settingsList.TAB_ICON_TOGGLE.getState()) return;

        ArrayList<String> returnMessage = serverHandler.mflpUsers;

        if (entry.getProfile() == null || entry.getProfile().getId() == null) return;
        if (returnMessage != null && returnMessage.contains(entry.getProfile().getName())) {
            RenderSystem.enableBlend();
            context.getMatrices().push();
            context.getMatrices().translate(0.0f, 0.0f, 100.0f);
            context.drawGuiTexture(logo, x - 2, y + 5, 4, 4);
            context.getMatrices().pop();
            RenderSystem.disableBlend();
        }
    }

}
