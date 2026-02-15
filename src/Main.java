import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static final JFrame MAIN_WINDOW = new JFrame("ODIM");
    private static final GraphicsEnvironment GRAPHICS = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static final GraphicsDevice DEVICE = GRAPHICS.getDefaultScreenDevice();

    public static void main(String[] args) {

        DEVICE.setFullScreenWindow(MAIN_WINDOW);

        // Ends the program when the window closes.
        MAIN_WINDOW.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        JPanel panel = new JPanel();

        JButton addItem = new JButton("Add Item");
        addItem.setVerticalTextPosition(AbstractButton.CENTER);
        addItem.setHorizontalTextPosition(AbstractButton.LEADING);
        MAIN_WINDOW.add(addItem);

        MAIN_WINDOW.setVisible(true);
    }
}
