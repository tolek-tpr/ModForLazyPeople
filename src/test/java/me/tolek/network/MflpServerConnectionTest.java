package me.tolek.network;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class MflpServerConnectionTest {

    @Test
    public void sendPost() {
        MflpServerConnection server = new MflpServerConnection();
        try {
            System.out.println(server.sendGetRequest("/api/mflp"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println(server.sendPostRequest("/api/mflp", "{ \"username\":\"player\"}"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println(server.sendDeleteRequest("/api/mflp", "{ \"username\":\"player\"}"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}