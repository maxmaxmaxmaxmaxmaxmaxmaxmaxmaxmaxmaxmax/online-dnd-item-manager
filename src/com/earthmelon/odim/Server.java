package com.earthmelon.odim;

import com.earthmelon.odim.item.Item;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

    public static Logger ODIMlog = LogManager.getLogger("ODIM");

    public static LinkedList<Item> ALL_ITEMS = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        ODIMlog.info("Logger online.");
        int port = 7999;
        ServerSocket s = new ServerSocket(port);
        Socket client = s.accept();
        ODIMlog.info("Server online at port {}", port);

        while (true) {
            ObjectInputStream is = new ObjectInputStream(client.getInputStream());
            Object fromClient = is.readObject();

            if (fromClient instanceof Item clientItem && !ALL_ITEMS.contains(fromClient)) {
                ALL_ITEMS.add(clientItem);
                ODIMlog.info("Item received: {}", clientItem);
                System.out.println("Item received: " + clientItem + " " + ALL_ITEMS);
                is.close();
            }
            is.close();
            Thread.sleep(1000);
        }
    }
}
