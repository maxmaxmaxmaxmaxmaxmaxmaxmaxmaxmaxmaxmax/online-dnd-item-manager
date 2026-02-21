package com.earthmelon.odim.server;

import com.earthmelon.odim.item.Item;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import javax.swing.*;

import org.json.*;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.nio.file.Files;
import java.util.LinkedList;

public class Server {

    public static Logger LOGGER = LogManager.getLogger("ODIM");
    public static final JFrame SERVER_WINDOW = new JFrame("ODIMServer");

    public static LinkedList<Item> ALL_ITEMS = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        Configurator.setLevel("ODIM", Level.INFO);
        LOGGER.info("<SERVER> Logger online.");
        int port = 7999;
        JButton saveToJSONFile = new JButton("Save to file");
        saveToJSONFile.addActionListener(new SaveToJSONAction());
        SERVER_WINDOW.add(saveToJSONFile);
        SERVER_WINDOW.setVisible(true);


        ServerSocket s = new ServerSocket(port);
        LOGGER.info("<SERVER> Server established, awaiting connection.");
        Socket client = s.accept();
        LOGGER.info("<SERVER> Connection established at port {}", port);
        ObjectInputStream dataFromClientStream = new ObjectInputStream(client.getInputStream());

        while (true) {
//            ObjectOutputStream allItemsForClientSync = new ObjectOutputStream(client.getOutputStream());
            Object fromClient;
            try {
                fromClient = dataFromClientStream.readObject();
            } catch (EOFException | SocketException e) {
                LOGGER.info("<SERVER> Client disconnected, waiting...");
                client = s.accept();
                LOGGER.info("<SERVER> Client reconnected.");
                dataFromClientStream = new ObjectInputStream(client.getInputStream());
                fromClient = dataFromClientStream.readObject();
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

    public static void saveToJSONFile() throws IOException {
        File saveLocation = new File("src/com/earthmelon/odim/server/item_list.json");

        if (saveLocation.exists()) {
            LOGGER.info("<SERVER> Item list file detected.");
        } else {
            LOGGER.info("<SERVER> Item list file not detected, creating a new one.");
            saveLocation.createNewFile();
        }
        JSONObject allItems = new JSONObject();
        for (Item item : ALL_ITEMS) {
            JSONObject itemObject = new JSONObject();

            itemObject.put("name", item.getName());
            itemObject.put("description", item.getDescription());
            itemObject.put("size", item.getSize());
            itemObject.put("image", "NOT YET IMPLEMENTED");
            itemObject.put("value", item.getValue());

            allItems.put(String.valueOf(item.getId()), itemObject);

        }
        Files.writeString(saveLocation.toPath(), allItems.toString()
                .replace("{", "{\n").replace("}", "\n}"));
    }
}
