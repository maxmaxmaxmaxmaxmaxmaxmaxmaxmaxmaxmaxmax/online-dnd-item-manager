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
            } catch (EOFException | SocketException e) {
//                Server.LOGGER.info("<SERVER> Client disconnected, waiting...");
//                client = serverSocket.accept();
//                LOGGER.info("<SERVER> Client reconnected.");
//                dataFromClientStream = new ObjectInputStream(client.getInputStream());
//                fromClient = dataFromClientStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            if (fromClient instanceof LinkedList<?> clientList) {
                LOGGER.info("<SERVER> Item list received: {}.", clientList);
                for (Object object : clientList) {
                    if (object instanceof Item item) {
                        if (ALL_ITEMS.contains(item)) {
                            LOGGER.info("<SERVER> Id {} already in list.", item.getId());
                            continue;
                        }
                        ALL_ITEMS.add(item);
                        LOGGER.info("<SERVER> Item added: {}", item);
                    } else {
                        LOGGER.error("<SERVER> Object {} is not of type Item, but instead {}", object, object.getClass());
                    }
                }
                LOGGER.info("<SERVER> Server item list now: {}", ALL_ITEMS);
            }
            if (fromClient instanceof String str) {
                LOGGER.info("<CLIENT> {}", str);
            }
        }
    }
}