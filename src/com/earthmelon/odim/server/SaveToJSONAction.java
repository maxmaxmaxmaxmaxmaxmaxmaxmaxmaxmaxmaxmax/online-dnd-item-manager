package com.earthmelon.odim.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SaveToJSONAction implements ActionListener {
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Server.saveToJSONFile();
        } catch (IOException ex) {
            Server.LOGGER.error("<SERVER> Unable to save the file!");
        }
    }
}
