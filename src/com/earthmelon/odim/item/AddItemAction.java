package com.earthmelon.odim.item;

import com.earthmelon.odim.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddItemAction implements ActionListener {


    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Item newItem = new Item("Test Item", "Test description", 0,0,0);
        Main.ALL_ITEMS.add(newItem);
        Main.ITEM_LIST_PANEL.add(new JLabel(newItem.name));
        Main.ITEM_LIST_PANEL.revalidate();
        Main.ITEM_LIST_PANEL.repaint();
        CreateItemPanelAction.ITEM_WINDOW.dispose();
    }
}
