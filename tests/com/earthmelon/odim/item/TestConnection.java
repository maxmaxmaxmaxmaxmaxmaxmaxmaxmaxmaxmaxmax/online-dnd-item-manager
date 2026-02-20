package com.earthmelon.odim.item;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class TestConnection {

    public static final int LOCAL_PORT = 7999;

    @org.junit.Test
    public void testLocalConnection() {
        try (ServerSocket server = new ServerSocket(LOCAL_PORT)) {
            Socket clientSocket = new Socket("localhost", LOCAL_PORT);
            Socket client = server.accept();
            assertFalse(server.isClosed());
        } catch (IOException e) {
            fail("Server failed to open at the port: " + LOCAL_PORT);
        }

    }
}
