package me.tolek.network;

import jakarta.websocket.Session;

import java.nio.ByteBuffer;

public interface PacketHandler {

    Packet<?> onByteMessage(ByteBuffer buffer, Session session);

}
