package me.tolek.modules.autoReply;

import me.tolek.event.ChatListener;
import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.event.UpdateListener;
import me.tolek.util.InstancedValues;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class AutoReplyExecutor extends EventImpl implements ChatListener {

    private MinecraftClient client;
    private InstancedValues iv;
    private AutoRepliesList autoReplies;

    @Override
    public void onEnable() {
        EventManager.getInstance().add(ChatListener.class, this);
        this.client = MinecraftClient.getInstance();
        this.iv = InstancedValues.getInstance();
        this.autoReplies = AutoRepliesList.getInstance();
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(ChatListener.class, this);
    }

    @Override
    public void onMessageAdd(Text a) {
        String message = a.getString();

        autoReplies.getAutoReplies().forEach((ar) -> {
            ar.getKeywords().forEach((String s) -> {
                if (message.contains(s) && ar.isTurnedOn()) {
                    int index = (int) Math.round(Math.random() * ar.getReplies().size()) - 1;
                    String reply = ar.getReplies().get(index == -1 ? 0 : index);

                    if (reply.startsWith("/") && reply != null) {
                        MinecraftClient.getInstance().player.networkHandler.sendCommand(reply.substring(1));
                    } else {
                        MinecraftClient.getInstance().player.networkHandler.sendChatMessage(reply);
                    }
                }

            });
        });
    }

}
