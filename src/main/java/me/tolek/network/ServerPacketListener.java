package me.tolek.network;

import me.tolek.util.LoggerUtils;

public class ServerPacketListener implements PacketListener {

    public void onPacket(Packet<ServerPacketListener> packet) {
        LoggerUtils.NETWORK.error("Received server bound packet on the client! Packet class: {}", packet.getClass().getName());
    }

}
