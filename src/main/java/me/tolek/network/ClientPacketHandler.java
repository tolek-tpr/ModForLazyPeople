package me.tolek.network;

import jakarta.websocket.Session;

import java.nio.ByteBuffer;

public class ClientPacketHandler implements PacketHandler {

    private final ClientPacketListener listener = new ClientPacketListener();

    @Override
    public Packet<?> onByteMessage(ByteBuffer buffer, Session session) {
        Packet<ClientPacketListener> packet = Packet.deserialize(buffer);

        if (packet.getPacketType() == PacketType.SERVERBOUND) {
            return null;
        }
        packet.accept(listener, session);

        return packet;
    }

}
