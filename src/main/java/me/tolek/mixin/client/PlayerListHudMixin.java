package me.tolek.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.mojang.blaze3d.systems.RenderSystem;
import me.tolek.network.MflpPlayersWorker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.Identifier;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {

    private final MflpPlayersWorker worker = MflpPlayersWorker.getInstance();
    private final Identifier logo = new Identifier("modforlazypeople", "mflp/user_logo");

    @Inject(method = "render", at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/PlayerListHud;renderLatencyIcon(Lnet/minecraft/client/gui/DrawContext;IIILnet/minecraft/client/network/PlayerListEntry;)V"))
    private void onRenderHud(DrawContext context, int scaledWindowWidth, Scoreboard scoreboard, ScoreboardObjective objective,
                            CallbackInfo ci, @Local(ordinal = 8) int m, @Local(ordinal = 16) int y,
                             @Local(ordinal = 0, index = 13) boolean bl,
                             @Local(ordinal = 17) int z, @Local(ordinal = 0, index = 27) PlayerListEntry playerListEntry2) {
        renderMflpTag(context,  m, y - (bl ? 9 : 0), z, playerListEntry2);
    }

    public void renderMflpTag(DrawContext context, int width, int x, int y, PlayerListEntry entry) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.world.getPlayerByUuid(entry.getProfile().getId());
        String returnMessage = worker.data;

        if (entry.getProfile().getId() == null ||
                player == null || player.getName() == null) return;

        if (returnMessage != null && returnMessage.contains(client.world.getPlayerByUuid(entry.getProfile().getId()).getName().getString())) {
            RenderSystem.enableBlend();
            context.getMatrices().push();
            context.getMatrices().translate(0.0f, 0.0f, 100.0f);
            context.drawGuiTexture(logo, x - 2, y + 5, 4, 4);
            context.getMatrices().pop();
            RenderSystem.disableBlend();
        }
    }

}
