package com.earthmelon.odim.server;

import com.earthmelon.odim.item.Item;

import java.net.*;
import java.io.*;
import java.util.LinkedList;

import static com.earthmelon.odim.server.Server.*;

public class MultiServerThread extends Thread {
    private Socket socket = null;

    public MultiServerThread(Socket socket) {
        super("MultiServerThread");
        this.socket = socket;
    }

    public void run() {

        ObjectInputStream dataFromClientStream = null;
        try {
            dataFromClientStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
//            ObjectOutputStream allItemsForClientSync = new ObjectOutputStream(client.getOutputStream());
            Object fromClient = null;
            try {
                fromClient = dataFromClientStream.readObject();
            } catch (SocketException e) {
                LOGGER.info("<THREAD> Client disconnected, stopping thread.");
                CLIENT_CONNECTION_COUNT--;
                return;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            if (fromClient instanceof LinkedList<?> clientList) {
                LOGGER.info("<THREAD> Item list received: {}.", clientList);
                for (Object object : clientList) {
                    if (object instanceof Item item) {
                        if (ALL_ITEMS.contains(item)) {
                            LOGGER.info("<THREAD> Id {} already in list.", item.getId());
                            continue;
                        }
                        ALL_ITEMS.add(item);
                        LOGGER.info("<THREAD> Item added: {}", item);
                    } else {
                        LOGGER.error("<THREAD> Object {} is not of type Item, but instead {}", object, object.getClass());
                    }
                }
                LOGGER.info("<THREAD> Server item list now: {}", ALL_ITEMS);
            }
        }
    }
}