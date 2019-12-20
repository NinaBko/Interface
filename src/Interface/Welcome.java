package Interface;

import Network.Controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.List;

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
    private JTextArea textArea1;
    //private JFrame frame = new JFrame("Welcome to the chat");
    private static DefaultListModel<String> messageModel = new DefaultListModel<>();
    private static DefaultListModel<String> usersModel = new DefaultListModel<>();

    // Users and messages list
    //private String[] users = {"Pseudo1", "Pseudo2", "Pseudo3", "Pseudo4"};
    //private String[] messages = {"msg1", "msg2", "msg3", "msg4"};

    //create the model for users list

    private DefaultListModel<String> setListModel(List<String> list)  {
        DefaultListModel<String> model = new DefaultListModel<String>();
        for (String txt : list) {
            model.addElement(txt);
        }
        return model;
    }

    private DefaultListModel<String> sendMessage(String message, DefaultListModel<String> model) {
        model.addElement(message);
        return model;
    }

    public Welcome(Controller controller) {
        JFrame frame = new JFrame("Welcome to the chat");
        init(frame);
        List<String> users = controller.getUserList();
        users.forEach(System.out::println);
        welcomeLabel.setText("Welcome "+ controller.getLogin());
        System.out.println("Window Activated Event");
        userList.setModel(setListModel(users));

        frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {

            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //messagesList.setModel(sendMessage(messageField.getText(),messageModel));
                DefaultListModel messagesModelUpdated= (DefaultListModel) messagesList.getModel();
                messagesList.setModel(sendMessage(messageField.getText(),messagesModelUpdated));
            }


        });
        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("User List Selection Activated Event");
                if (!(e.getValueIsAdjusting())) {
                    String selected = userList.getSelectedValue().toString();
                    textArea1.append("Chat with  " + selected+"\n");
                }
            }
        });
    }

    //for the display of the chat https://stackoverflow.com/questions/18687607/what-component-should-be-used-to-display-messages-in-a-chat-application
    // https://www.codejava.net/java-se/swing/jlist-custom-renderer-example

    public void init(JFrame frame) {
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
