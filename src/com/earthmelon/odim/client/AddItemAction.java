package com.earthmelon.odim.client;

import com.earthmelon.odim.item.Item;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Random;

public class AddItemAction extends ClientAction implements ActionListener {


    AddItemAction(Client client) {
        super(client);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Random random = new Random();
        Item newItem = new Item("Test Item", "Test description", 0,0, random.nextInt(100));
        client.MY_KNOWN_ITEMS.add(newItem);
        Client.addToItemPanel(newItem);

        CreateItemPanelAction.ITEM_WINDOW.dispose();
    }
}
