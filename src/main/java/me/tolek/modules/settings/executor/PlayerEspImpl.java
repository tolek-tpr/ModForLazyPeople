package me.tolek.modules.settings.executor;

import com.mojang.blaze3d.systems.RenderSystem;
import me.tolek.event.Event;
import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.interfaces.RenderListener;
import me.tolek.interfaces.UpdateListener;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.util.MflpUtil;
import me.tolek.util.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayerEspImpl extends EventImpl implements RenderListener, UpdateListener {

    private final ArrayList<PlayerEntity> players = new ArrayList<>();

    @Override
    public void onEnable() {
        EventManager.getInstance().add(RenderListener.class, this);
        EventManager.getInstance().add(UpdateListener.class, this);
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(RenderListener.class, this);
        EventManager.getInstance().remove(UpdateListener.class, this);
    }

    @Override
    public void onRender(MatrixStack matrixStack, float tickDelta) {
        boolean a = false;
        if (/*MflpSettingsList.getInstance().PLAYER_ESP.getState()*/ a) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_DEPTH_TEST);

            matrixStack.push();

            for (PlayerEntity p : players) {
                matrixStack.push();

                Identifier texture = ((OtherClientPlayerEntity) p).getSkinTextures().texture();
                RenderUtils.drawTexture(texture, matrixStack,
                        p.getBlockX(), p.getBlockY(), p.getBlockZ(), 100, 100, 100, 100, 100, 100);

                matrixStack.pop();
            }
            RenderUtils.drawOutlinedBox(new Box(-0.5, -0.5, 0, 0.5, 0.5, 0), matrixStack);

            Identifier texture = MinecraftClient.getInstance().player.getSkinTextures().texture();
            RenderUtils.drawTexture(texture, matrixStack,
                    100, 100, 100, 500, 500, 500, 500, 500, 500);


            matrixStack.pop();

            // GL resets
            RenderSystem.setShaderColor(1, 1, 1, 1);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }

    @Override
    public void onUpdate() {
        MinecraftClient client = MinecraftClient.getInstance();

        players.clear();
        PlayerEntity player = client.player;
        ClientWorld world = client.world;
        if (world == null) return;

        players.clear();
        Stream<AbstractClientPlayerEntity> stream = world.getPlayers()
                .parallelStream().filter(e -> !e.isRemoved() && e.getHealth() > 0)
                .filter(e -> e != player)
                //.filter(e -> !(e instanceof FakePlayerEntity))
                .filter(e -> Math.abs(e.getY() - client.player.getY()) <= 1e6);

        //stream = entityFilters.applyTo(stream);

        players.addAll(stream.collect(Collectors.toList()));
    }

}
