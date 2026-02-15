import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static final JFrame MAIN_WINDOW = new JFrame("ODIM");
    private static final GraphicsEnvironment GRAPHICS = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static final GraphicsDevice DEVICE = GRAPHICS.getDefaultScreenDevice();

    public static void main(String[] args) {

//        DEVICE.setFullScreenWindow(MAIN_WINDOW);

        MAIN_WINDOW.setSize(400, 400);

        // Ends the program when the window closes.
        MAIN_WINDOW.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        JPanel itemList = new JPanel();
        itemList.setLayout(new BoxLayout(itemList, BoxLayout.Y_AXIS));
        itemList.setSize(200,2000);
        itemList.setBorder(new BevelBorder(0, Color.BLACK, Color.BLACK));
        JLabel itemName = new JLabel("Hello");
        JLabel itemNameTwo = new JLabel("Item");

        itemList.add(itemName);
        itemList.add(itemNameTwo);

        JButton addItem = new JButton("Add Item");
        addItem.setBounds(0,0,50,50);
//        addItem.setVerticalTextPosition(SwingConstants.TOP);
//        addItem.setHorizontalTextPosition(AbstractButton.LEADING);
        addItem.setActionCommand("add_item");

        MAIN_WINDOW.add(itemList);
        MAIN_WINDOW.add(addItem, BorderLayout.WEST);

        MAIN_WINDOW.setVisible(true);
    }
}
