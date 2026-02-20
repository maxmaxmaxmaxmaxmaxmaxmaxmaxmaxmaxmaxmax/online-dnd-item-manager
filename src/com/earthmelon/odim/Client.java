package com.earthmelon.odim;

import com.earthmelon.odim.item.CreateItemPanelAction;
import com.earthmelon.odim.item.Item;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.LinkedList;

import java.io.*;
import java.net.*;

public class Client {
    public static final JFrame MAIN_WINDOW = new JFrame("ODIM");
    private static final GraphicsEnvironment GRAPHICS = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static final GraphicsDevice DEVICE = GRAPHICS.getDefaultScreenDevice();

    public static JPanel ITEM_LIST_PANEL = new JPanel();
    public static LinkedList<Item> MY_KNOWN_ITEMS = new LinkedList<>();

    static int previousSize = 0;

    public static void main(String[] args) throws Exception {

        String host = "localhost";
        int port = 7999;
        Socket clientSocket = new Socket(host, port);
        assembleUI();


        ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
        while (!clientSocket.isClosed()) {
            if (MY_KNOWN_ITEMS.size() > previousSize) {
                System.out.println("Writing item: " + MY_KNOWN_ITEMS.getLast());

                os.writeObject(MY_KNOWN_ITEMS);
                previousSize = MY_KNOWN_ITEMS.size();
            }
            Thread.sleep(1000);
        }
        os.close();
    }

    private static void assembleUI() {
        MAIN_WINDOW.setSize(400, 400);

        // Ends the program when the window closes.
        MAIN_WINDOW.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.out.println(MY_KNOWN_ITEMS);
                System.exit(0);
            }
        });

        ITEM_LIST_PANEL.setLayout(new BoxLayout(ITEM_LIST_PANEL, BoxLayout.Y_AXIS));
        ITEM_LIST_PANEL.setSize(200,2000);
        ITEM_LIST_PANEL.setBorder(new BevelBorder(0, Color.BLACK, Color.BLACK));

        JButton addItem = new JButton("Add Item");
        addItem.addActionListener(new CreateItemPanelAction());
        addItem.setBounds(0,0,50,50);
        addItem.setActionCommand("add_item");

        MAIN_WINDOW.add(ITEM_LIST_PANEL);
        MAIN_WINDOW.add(addItem, BorderLayout.WEST);

        MAIN_WINDOW.setVisible(true);
    }
}
