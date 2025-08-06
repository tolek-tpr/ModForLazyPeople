package me.tolek.network;

import jakarta.websocket.Session;

import java.io.*;
import java.nio.ByteBuffer;

public interface Packet<E extends PacketListener> extends Serializable {

    String hash = "0eb4022fa1f4e06b7e31cb8839d23a289f411afb4fa9ae24b9a352afab39b970";
    @Serial
    long serialVersionUID = 1L;

    void accept(E listener, Session session);
    PacketType getPacketType();

    default ByteBuffer serialize() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeUTF(this.getClass().getName());
            oos.writeObject(this);

            return ByteBuffer.wrap(bos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Serialization failed", e);
        }
    }

    @SuppressWarnings("unchecked")
    static <T> T deserialize(ByteBuffer buffer) {
        try {
            buffer.rewind();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);

            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);

            String className = ois.readUTF();
            Class<?> clazz = Class.forName(className);

            return (T) clazz.cast(ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Deserialization failed", e);
        }
    }

}
