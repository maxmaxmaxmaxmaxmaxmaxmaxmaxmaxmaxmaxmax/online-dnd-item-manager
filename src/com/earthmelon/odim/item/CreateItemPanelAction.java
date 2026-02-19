package com.earthmelon.odim.item;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateItemPanelAction implements ActionListener {
    public static JFrame ITEM_WINDOW;

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ITEM_WINDOW = new JFrame("AddItemStart");

        ITEM_WINDOW.setSize(400,200);
        JButton DONE_BUTTON = new JButton("AddItemDone");
        DONE_BUTTON.addActionListener(new AddItemAction());
        ITEM_WINDOW.add(DONE_BUTTON);

        ITEM_WINDOW.setVisible(true);
    }
}
