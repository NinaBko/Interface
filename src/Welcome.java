import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class Welcome extends JFrame {
    private JPanel contentPane;
    private JLabel welcomeLabel;
    private JPanel mainPane;
    private JPanel userListPane;
    private JPanel chatPane;
    private JLabel userListLabel;
    private JList userList;
    private JLabel chatLabel;
    private JTextArea textArea1;
    private static JFrame frame = new JFrame("Welcome to the chat");


    public Welcome() {
        frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                welcomeLabel.setText("Welcome "+Connect.getLogin());
                System.out.println("Window Activated Event");
            }
        });
    }

    static void mainCaller() {
        System.out.println("mainCaller!");
            // Calling the main() method
            Welcome.main(null);
    }

    public static void main(String[] args) {
        frame.setContentPane(new Welcome().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
