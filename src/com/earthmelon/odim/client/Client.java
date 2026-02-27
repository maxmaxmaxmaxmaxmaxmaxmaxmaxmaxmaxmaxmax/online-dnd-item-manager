package com.earthmelon.odim.client;

import com.earthmelon.odim.item.Item;
import com.earthmelon.odim.server.Server;
import static com.earthmelon.odim.server.Server.LOGGER;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.LinkedList;

import java.io.*;
import java.net.*;

// Do the client object input stream, make sure to repaint and shit after to see.

public class Client {
    public static final JFrame MAIN_WINDOW = new JFrame("ODIM");

    public static JPanel ITEM_LIST_PANEL = new JPanel();
    public LinkedList<Item> MY_KNOWN_ITEMS = new LinkedList<>();

    public static ObjectOutputStream dataFromServerStream = null;
    public ObjectInputStream dataToServerStream = null;

    static int previousSize = 0;

    public Client() {
        String host = "localhost";
        try (Socket clientSocket = new Socket(host, Server.PORT)) {
            LOGGER.info("<CLIENT> Connection established.");
            dataFromServerStream = new ObjectOutputStream(clientSocket.getOutputStream());
            LOGGER.info("<CLIENT> Server input stream established.");
            dataFromServerStream.flush();

            assembleUI();
            while (MAIN_WINDOW.isVisible()) {
                readFromServer(clientSocket);
            }
        } catch (IOException e) {
            LOGGER.error("<CLIENT> Failed to open client socket: {}", String.valueOf(e));
            System.exit(1);
        }

    }

    public static void main(String[] args) {
       new Client();
    }

    void assembleUI() {
        MAIN_WINDOW.setSize(400, 400);

        // Ends the program when the window closes.
        MAIN_WINDOW.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        ITEM_LIST_PANEL.setLayout(new BoxLayout(ITEM_LIST_PANEL, BoxLayout.Y_AXIS));
        ITEM_LIST_PANEL.setSize(200,2000);
        ITEM_LIST_PANEL.setBorder(new BevelBorder(0, Color.BLACK, Color.BLACK));

        MAIN_WINDOW.add(ITEM_LIST_PANEL);

        MAIN_WINDOW.setVisible(true);
    }

    void readFromServer(Socket clientSocket) {
        Object fromServer = null;
        try {
            ObjectInputStream dataToServerStream = new ObjectInputStream(clientSocket.getInputStream());
            LOGGER.info("<CLIENT> Server output stream established.");
            fromServer = dataToServerStream.readObject();
            if (fromServer instanceof LinkedList<?> list) {
                if (list.getFirst() instanceof Item) {
                    MY_KNOWN_ITEMS = (LinkedList<Item>) list;
                    LOGGER.info("<CLIENT> Item list successfully received.");
                    ITEM_LIST_PANEL.removeAll();
                    addToItemPanel(MY_KNOWN_ITEMS);
                } else {
                    LOGGER.error("<CLIENT> List of unknown contents received.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addToItemPanel(Item item) {
        ITEM_LIST_PANEL.add(new JLabel(item.getName()));
        ITEM_LIST_PANEL.revalidate();
        ITEM_LIST_PANEL.repaint();
    }

    public static void addToItemPanel(LinkedList<Item> items) {
        for (Item item : items) {
            ITEM_LIST_PANEL.add(new JLabel(item.getName()));
        }
        ITEM_LIST_PANEL.revalidate();
        ITEM_LIST_PANEL.repaint();
    }
}
