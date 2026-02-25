package com.earthmelon.odim;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class HostClient extends Client {

    public static void main(String[] args) throws Exception {

        String host = "localhost";
        int port = 7999;
        Socket clientSocket = new Socket(host, port);
        ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
        os.writeObject("Connection established.");

        new HostClient().assembleUI();

        while (!clientSocket.isClosed()) {
            if (MY_KNOWN_ITEMS.size() > previousSize) {
                os.reset();
                os.writeObject("Writing list: " + MY_KNOWN_ITEMS);
                // Added item gets sent here but not when added via AddItemAction.
//                MY_KNOWN_ITEMS.add(new Item("Test Item", "Test Description", 0,0,0));
                os.writeObject(MY_KNOWN_ITEMS);
                previousSize = MY_KNOWN_ITEMS.size();
            }
        }
        os.close();
    }
}
