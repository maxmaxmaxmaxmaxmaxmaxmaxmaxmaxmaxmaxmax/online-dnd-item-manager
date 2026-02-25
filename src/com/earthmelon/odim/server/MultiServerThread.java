package com.earthmelon.odim.server;

import com.earthmelon.odim.item.Item;

import java.net.*;
import java.io.*;
import java.util.LinkedList;

import static com.earthmelon.odim.server.Server.*;

public class MultiServerThread extends Thread {
    public ObjectInputStream dataFromClientStream = null;
    public ObjectOutputStream dataToClientStream = null;

    public MultiServerThread(Socket socket) {
        super("MultiServerThread");
        try {
            dataToClientStream = new ObjectOutputStream(socket.getOutputStream());
            LOGGER.info("<THREAD> Server output stream created.");
            dataToClientStream.flush();
            dataFromClientStream = new ObjectInputStream(socket.getInputStream());
            LOGGER.info("<THREAD> Server input stream created.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        Server.flushToClients(ALL_ITEMS);
        while (true) {
            Object fromClient = null;
            try {
                fromClient = dataFromClientStream.readObject();
            } catch (SocketException e) {
                LOGGER.info("<THREAD> Client disconnected, stopping thread.");
                ALL_THREADS.remove(this);
                CLIENT_CONNECTION_COUNT--;
                return;
            } catch (EOFException e) {
                continue;
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
                        ALL_ITEMS.add(item); // TODO: Item list not being sent to server with updated item list.
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