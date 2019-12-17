import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Welcome extends JFrame {
    private JPanel contentPane;
    private JLabel welcomeLabel;
    private JPanel mainPane;
    private JPanel userListPane;
    private JLabel userListLabel;
    private JList<String> userList;
    private JButton sendButton;
    private JTextField messageField;
    private JPanel sendPane;
    private JPanel chatPane;
    private JLabel chatLabel;
    private JScrollPane scrollChatPane;
    private JList<String> messagesList;
    private static JFrame frame = new JFrame("Welcome to the chat");
    private static DefaultListModel<String> messageModel = new DefaultListModel<>();
    private static DefaultListModel<String> usersModel = new DefaultListModel<>();

    // Users and messages list
    private String[] users = {"Pseudo1", "Pseudo2", "Pseudo3", "Pseudo4"};
    private String[] messages = {"msg1", "msg2", "msg3", "msg4"};

    //create the model for users list

    private DefaultListModel<String> getListModel(String[] arrayOfStrings, DefaultListModel<String> model) {
        for (String txt : arrayOfStrings) {
            model.addElement(txt);
        }
        return model;
    }

    private DefaultListModel<String> sendMessage(String message, DefaultListModel<String> model) {
        model.addElement(message);
        return model;
    }

    Welcome() {
        frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                welcomeLabel.setText("Welcome "+Connect.getLogin());
                System.out.println("Window Activated Event");
                messagesList.setModel(getListModel(messages, messageModel));
                userList.setModel(getListModel(users, usersModel));
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messagesList.setModel(sendMessage(messageField.getText(),messageModel));
            }
        });
    }

    //for the display of the chat https://stackoverflow.com/questions/18687607/what-component-should-be-used-to-display-messages-in-a-chat-application
    // https://www.codejava.net/java-se/swing/jlist-custom-renderer-example

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
