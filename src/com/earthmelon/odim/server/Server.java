package com.earthmelon.odim.server;

import com.earthmelon.odim.item.Item;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import javax.swing.*;

import org.json.*;

import java.awt.*;
import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.nio.file.Files;
import java.util.LinkedList;

public class Server {

    public static Logger LOGGER = LogManager.getLogger("ODIM");
    public static final JFrame SERVER_WINDOW = new JFrame("ODIMServer");
    public static File itemSaveLocation = new File("src/com/earthmelon/odim/server/item_list.json");

    public static LinkedList<Item> ALL_ITEMS = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        Configurator.setLevel("ODIM", Level.INFO);
        LOGGER.info("<SERVER> Logger online.");
        int port = 7999;

        // Window layout
        SERVER_WINDOW.setSize(500, 200);
        SERVER_WINDOW.setLayout(new FlowLayout());
        JButton saveToJSONFile = new JButton("Save to file");
        saveToJSONFile.addActionListener(new SaveToJSONAction());
        JButton loadFromJSONFile = new JButton("Load from file");
        loadFromJSONFile.addActionListener(new LoadFromJSONAction());
        SERVER_WINDOW.add(saveToJSONFile);
        SERVER_WINDOW.add(loadFromJSONFile);
        SERVER_WINDOW.setVisible(true);

        // Make main not throw Exception.
        ServerSocket serverSocket = new ServerSocket(port);
        LOGGER.info("<SERVER> Server established, awaiting connection.");
        Socket client = serverSocket.accept(); // Waits until a connection to a client is established.
        LOGGER.info("<SERVER> Connection established at port {}", port);
        ObjectInputStream dataFromClientStream = new ObjectInputStream(client.getInputStream());
        ObjectOutputStream dataToClientsStream = new ObjectOutputStream(client.getOutputStream()); // To be used for sending loaded items to the clients.

        while (true) {
//            ObjectOutputStream allItemsForClientSync = new ObjectOutputStream(client.getOutputStream());
            Object fromClient;
            try {
                fromClient = dataFromClientStream.readObject();
            } catch (EOFException | SocketException e) {
                LOGGER.info("<SERVER> Client disconnected, waiting...");
                client = serverSocket.accept();
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

    /**
     * Saves an item to a JSON named item_list.json
     * Items are indexed by their ids, with other fields listed as plaintext
     * or JSONArrays.
     * @throws IOException while creating a new file.
     */
    public static void saveToJSONFile() throws IOException {
        if (itemSaveLocation.exists()) {
            LOGGER.info("<SERVER> Item list file detected.");
        } else {
            LOGGER.info("<SERVER> Item list file not detected, creating a new one.");
            itemSaveLocation.createNewFile();
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
        Files.writeString(itemSaveLocation.toPath(), allItems.toString());
//                .replace("{", "{\n").replace("}", "\n}"));
        LOGGER.info("<SERVER> Items successfully added.");
    }

    /**
     * Reads in the JSON data from saveToJSONFile().
     * Does not support anything other than maintaining id, name, and description.
     */
    public static void loadItemsFromJSON() {
        ALL_ITEMS.clear();
        Item.ID_COUNT = 0;

        String jsonString = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(itemSaveLocation))) {
            jsonString = reader.readLine();
        } catch (FileNotFoundException e) {
            LOGGER.error("Item save file not detected!");
        } catch (IOException e) {
            LOGGER.error(e);
        }

        assert jsonString != null; // What happens when this fails?
        JSONObject obj = new JSONObject(jsonString);
        for (int i = 0; i < obj.length(); i++) {
            JSONObject singleItem = obj.getJSONObject(String.valueOf(i));
            String itemNameJSON = singleItem.getString("name");
            String itemDescriptionJSON = singleItem.getString("description");
//            LinkedList<Integer> itemSizeJSON = singleItem.getJSONArray("size");
            Item reconstructedItem = new Item(itemNameJSON, itemDescriptionJSON, 0, 0, 0);
            ALL_ITEMS.add(reconstructedItem);
        }
        System.out.println(ALL_ITEMS);
    }
}
