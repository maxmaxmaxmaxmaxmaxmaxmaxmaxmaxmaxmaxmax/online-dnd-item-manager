package com.earthmelon.odim.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.earthmelon.odim.server.Server.ALL_ITEMS;

public class LoadFromJSONAction implements ActionListener {
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Server.loadItemsFromJSON();
        Server.flushToClients(ALL_ITEMS);
    }
}
