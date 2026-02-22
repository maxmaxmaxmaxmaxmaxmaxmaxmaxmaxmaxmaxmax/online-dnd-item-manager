package com.earthmelon.odim.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadFromJSONAction implements ActionListener {
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Server.loadItemsFromJSON();
    }
}
