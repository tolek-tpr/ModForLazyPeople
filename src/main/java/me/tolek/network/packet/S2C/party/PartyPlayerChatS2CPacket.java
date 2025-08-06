package me.tolek.network.packet.S2C.party;

import jakarta.websocket.Session;
import me.tolek.network.ClientPacketListener;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;

public class PartyPlayerChatS2CPacket implements Packet<ClientPacketListener> {

    public final String message, username;

    public PartyPlayerChatS2CPacket(String message, String username) {
        this.message = message;
        this.username = username;
    }

    @Override
    public void accept(ClientPacketListener listener, Session session) {
        listener.onPlayerChat(this);
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.CLIENTBOUND;
    }

}
