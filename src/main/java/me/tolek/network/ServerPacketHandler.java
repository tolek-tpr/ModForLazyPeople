package me.tolek.network;

import jakarta.websocket.Session;

import java.nio.ByteBuffer;

public class ServerPacketHandler implements PacketHandler {

    private final ServerPacketListener listener = new ServerPacketListener();

    @Override
    public Packet<?> onByteMessage(ByteBuffer buffer, Session session) {
        Packet<ServerPacketListener> packet = Packet.deserialize(buffer);

        if (packet.getPacketType() == PacketType.CLIENTBOUND) {
            return null;
        }
        packet.accept(listener, session);

        return packet;
    }

}