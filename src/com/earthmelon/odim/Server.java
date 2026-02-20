package com.earthmelon.odim;

import com.earthmelon.odim.item.Item;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {

    public static Logger ODIMlog = LogManager.getLogger("ODIM");

    public static LinkedList<Item> ALL_ITEMS = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        ODIMlog.info("Logger online.");
        int port = 7999;
        ServerSocket s = new ServerSocket(port);
        Socket client = s.accept();
        ODIMlog.info("Server online at port {}", port);
        ObjectInputStream dataFromClientStream = new ObjectInputStream(client.getInputStream());

        while (!client.isClosed()) {
//            ObjectOutputStream allItemsForClientSync = new ObjectOutputStream(client.getOutputStream());
            Object fromClient;
            try {
                fromClient = dataFromClientStream.readObject();
            } catch (EOFException e) {
                continue;
            }

            if (fromClient instanceof LinkedList<?> clientList && !ALL_ITEMS.equals(clientList)) {
                ALL_ITEMS.clear();
                for (Object object : clientList) {
                    if (object instanceof Item item) {
                        ALL_ITEMS.add(item);
                        System.out.println(ALL_ITEMS);
                        ODIMlog.info("Item received: {}", clientList);
                    } else {
                        ODIMlog.error("Object " + object + " is not of type Item, but instead " + object.getClass());
                    }
                }
            }
            Thread.sleep(1000);
        }
        dataFromClientStream.close();
        System.out.println(ALL_ITEMS);
        System.exit(0);
    }
}
