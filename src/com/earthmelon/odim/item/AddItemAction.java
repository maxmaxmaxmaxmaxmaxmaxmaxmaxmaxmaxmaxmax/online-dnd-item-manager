package com.earthmelon.odim.item;

import com.earthmelon.odim.Client;
import com.earthmelon.odim.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;

public class AddItemAction implements ActionListener {


    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Item newItem = new Item("Test Item", "Test description", 0,0,0);
        Client.MY_KNOWN_ITEMS.add(newItem);
        Client.ITEM_LIST_PANEL.add(new JLabel(newItem.getName()));
        Client.ITEM_LIST_PANEL.revalidate();
        Client.ITEM_LIST_PANEL.repaint();
        CreateItemPanelAction.ITEM_WINDOW.dispose();
    }
}
