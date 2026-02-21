package com.earthmelon.odim.item;

import com.earthmelon.odim.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Random;

public class AddItemAction implements ActionListener {


    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Random random = new Random();
        Item newItem = new Item("Test Item", "Test description", 0,0, random.nextInt(100));
        Client.MY_KNOWN_ITEMS.add(newItem);
        Client.ITEM_LIST_PANEL.add(new JLabel(newItem.getName()));
        Client.ITEM_LIST_PANEL.revalidate();
        Client.ITEM_LIST_PANEL.repaint();
        CreateItemPanelAction.ITEM_WINDOW.dispose();
    }
}
