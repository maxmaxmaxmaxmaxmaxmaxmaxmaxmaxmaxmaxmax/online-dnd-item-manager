package com.earthmelon.odim;

import com.earthmelon.odim.item.CreateItemPanelAction;
import com.earthmelon.odim.item.Item;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class Main {
    public static final JFrame MAIN_WINDOW = new JFrame("ODIM");
    private static final GraphicsEnvironment GRAPHICS = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static final GraphicsDevice DEVICE = GRAPHICS.getDefaultScreenDevice();

    // TODO: Move to server class when it exists.
    public static LinkedList<Item> ALL_ITEMS = new LinkedList<>();

    // TODO: Move to client class when it exists.
    public static JPanel ITEM_LIST_PANEL = new JPanel();

    public static void main(String[] args) {

//        DEVICE.setFullScreenWindow(MAIN_WINDOW);

        MAIN_WINDOW.setSize(400, 400);

        // Ends the program when the window closes.
        MAIN_WINDOW.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.out.println(ALL_ITEMS);
                System.exit(0);
            }
        });

        ITEM_LIST_PANEL.setLayout(new BoxLayout(ITEM_LIST_PANEL, BoxLayout.Y_AXIS));
        ITEM_LIST_PANEL.setSize(200,2000);
        ITEM_LIST_PANEL.setBorder(new BevelBorder(0, Color.BLACK, Color.BLACK));

        JButton addItem = new JButton("Add Item");
        addItem.addActionListener(new CreateItemPanelAction());
        addItem.setBounds(0,0,50,50);
//        addItem.setVerticalTextPosition(SwingConstants.TOP);
//        addItem.setHorizontalTextPosition(AbstractButton.LEADING);
        addItem.setActionCommand("add_item");

        MAIN_WINDOW.add(ITEM_LIST_PANEL);
        MAIN_WINDOW.add(addItem, BorderLayout.WEST);

        MAIN_WINDOW.setVisible(true);
    }
}
