package com.earthmelon.odim;

import com.earthmelon.odim.server.Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.earthmelon.odim.server.Server.LOGGER;

public class HostClient extends Client {

    HostClient() {}

    public static void main(String[] args) {

        String host = "localhost";
        Socket clientSocket = null;
        ObjectOutputStream os = null;

        try {
            clientSocket = new Socket(host, Server.PORT);
            os = new ObjectOutputStream(clientSocket.getOutputStream());
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
        } catch (IOException e) {
            LOGGER.error("Failed to open client socket: {}{}", String.valueOf(e));
            System.exit(1);
        }


    }
}
