package com.earthmelon.odim;

import com.earthmelon.odim.item.Item;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

public class Server {

    public static Logger ODIMlog = LogManager.getLogger("ODIM");

    public static LinkedList<Item> ALL_ITEMS = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        Configurator.setLevel("ODIM", Level.INFO);
        ODIMlog.info("<SERVER> Logger online.");
        int port = 7999;
        ServerSocket s = new ServerSocket(port);
        ODIMlog.info("<SERVER> Server established, awaiting connection.");
        Socket client = s.accept();
        ODIMlog.info("<SERVER> Connection established at port {}", port);
        ObjectInputStream dataFromClientStream = new ObjectInputStream(client.getInputStream());

        while (true) {
//            ObjectOutputStream allItemsForClientSync = new ObjectOutputStream(client.getOutputStream());
            Object fromClient;
            try {
                fromClient = dataFromClientStream.readObject();
            } catch (EOFException | SocketException e) {
                ODIMlog.info("<SERVER> Client disconnected, waiting...");
                client = s.accept();
                ODIMlog.info("<SERVER> Client reconnected.");
                dataFromClientStream = new ObjectInputStream(client.getInputStream());
                fromClient = dataFromClientStream.readObject();
            }

            if (fromClient instanceof LinkedList<?> clientList) {
                ODIMlog.info("<SERVER> Item list received: {}.", clientList);
                for (Object object : clientList) {
                    if (object instanceof Item item) {
                        if (ALL_ITEMS.contains(item)) {
                            ODIMlog.info("<SERVER> Id {} already in list.", item.getId());
                            continue;
                        }
                        ALL_ITEMS.add(item);
                        ODIMlog.info("<SERVER> Item added: {}", item);
                    } else {
                        ODIMlog.error("<SERVER> Object {} is not of type Item, but instead {}", object, object.getClass());
                    }
                }
                ODIMlog.info("<SERVER> Server item list now: {}", ALL_ITEMS);
            }
            if (fromClient instanceof String str) {
                ODIMlog.info("<CLIENT> " + str);
            }
            Thread.sleep(1000);
        }
    }
}
