package com.earthmelon.odim.client;

import com.earthmelon.odim.server.Server;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.earthmelon.odim.server.Server.LOGGER;

public class HostClient extends Client {

    HostClient() {
        String host = "localhost";
        Socket clientSocket = null;

        try {
            clientSocket = new Socket(host, Server.PORT);
            LOGGER.info("<CLIENT> Connection established.");
            dataFromServerStream = new ObjectOutputStream(clientSocket.getOutputStream());
            LOGGER.info("<CLIENT> Server input stream established.");
            dataFromServerStream.flush();

            assembleUI();

            while (!clientSocket.isClosed()) {
                if (MY_KNOWN_ITEMS.size() > previousSize) {
                    dataFromServerStream.flush();
                    dataFromServerStream.writeObject(MY_KNOWN_ITEMS);
                    LOGGER.info("<CLIENT> Item list {} successfully sent to server.", MY_KNOWN_ITEMS);
                    previousSize = MY_KNOWN_ITEMS.size();
                }
                readFromServer(clientSocket);
            }
            dataFromServerStream.close();
        } catch (IOException e) {
            LOGGER.error("<CLIENT> Failed to open client socket: {}", String.valueOf(e));
            System.exit(1);
        }

    }

    public static void main(String[] args) {
        new HostClient();
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

        JButton addItem = new JButton("Add Item");
        addItem.addActionListener(new CreateItemPanelAction(this));
        addItem.setBounds(0, 0, 50, 50);
        addItem.setActionCommand("add_item");
        MAIN_WINDOW.add(addItem, BorderLayout.WEST);

        MAIN_WINDOW.add(ITEM_LIST_PANEL);
        MAIN_WINDOW.setVisible(true);
    }
}
