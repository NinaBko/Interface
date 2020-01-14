package Interface;

import Network.Controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Welcome extends JFrame {
    private JPanel contentPane;
    private JLabel welcomeLabel;
    private JPanel mainPane;
    private JPanel userListPane;
    private JLabel userListLabel;
    public JList<ChatUser> userList;
    private JButton sendButton;
    private JTextField messageField;
    private JPanel sendPane;
    private JPanel chatPane;
    private JLabel chatLabel;
    private JScrollPane scrollChatPane;
    private JList<ChatMessage> messagesList;
    private JTextArea textArea1;
    private JTabbedPane tabbedPane;
    private JTextField loginField;
    private JButton buttonChange;
    private static String currentChatUser;
    private JFrame frame = new JFrame("Welcome to the chat");

    //private static DefaultListModel<String> messageModel = new DefaultListModel<>();
    //private static DefaultListModel<String> usersModel = new DefaultListModel<>();

    // Users and messages list
    //private String[] users = {"Pseudo1", "Pseudo2", "Pseudo3", "Pseudo4"};
    //private String[] messages = {"msg1", "msg2", "msg3", "msg4"};

    //Faire des tests de liste de user vide et d'historique de messages vide sinon risque exception !

    // History for tests
    private ChatUser user1 = new ChatUser("user1");
    private ChatUser user2 = new ChatUser("user2");
    private List<ChatMessage> chatMsgListTest = new ArrayList<ChatMessage>();

    private List<ChatMessage> addMessagesToList() {
       ChatMessage message1 = new ChatMessage(user2,LocalDateTime.now(),"Salut !");
       ChatMessage message2 = new ChatMessage(user1,LocalDateTime.now(),"Cc !");
       ChatMessage message3 = new ChatMessage(user1,LocalDateTime.now(),"Premier test envoi messages ...");
       chatMsgListTest.add(message1);
       chatMsgListTest.add(message2);
       chatMsgListTest.add(message3);
       return chatMsgListTest;
    }

    //create the model for users list

    public DefaultListModel<ChatUser> setListModelUsers(List<ChatUser> list) {
        DefaultListModel<ChatUser> model = new DefaultListModel<>();
        for (ChatUser user : list) {
            model.addElement(user);
        }
        return model;
    }

    //Update users list and create a Panel for each user
    private void updateUsers(Controller c)  {
        new RefreshWelcome("refresh", this, c);
    }

    private DefaultListModel<ChatMessage> setListModelMessages(ChatMessageList listMessages) {
        DefaultListModel<ChatMessage> model = new DefaultListModel<>();
        List<ChatMessage> list = listMessages.getMessages();
        LocalDateTime now = LocalDateTime.now();
        ChatUser destUser = listMessages.getSecondUser();
        for (ChatMessage message : list) {
            ChatMessage messageConstructed = new ChatMessage(destUser, now, message.getMessage());
            model.addElement(messageConstructed);
        }
        return model;
    }

    private void sendMessage(String message, DefaultListModel<ChatMessage> model) {
        model.addElement(new ChatMessage(new ChatUser(currentChatUser), LocalDateTime.now(), message));
        messagesList.setModel(model);
    }

    private void actionOnSend(DefaultListModel<ChatMessage> messageModel, Controller controller) {
        String message = messageField.getText();
        String user = currentChatUser;
        sendMessage(message,messageModel);
        if (!currentChatUser.equals("")) {
            controller.send(user,message);
            System.out.println(message+" to "+user);
        }
        messageField.setText("");
    }

    private void actionOnChange(Controller controller){
        String newLogin = loginField.getText();
        if (!newLogin.equals("")) {
            controller.setUserLogin(newLogin);
            welcomeLabel.setText("Welcome " + controller.getLogin());
            loginField.setText("");
            controller.sendLoginChangeByUser();
        }
    }

    public List<ChatUser> toChatUserList (List<String> list, Controller c) {
        List<ChatUser> chatList = new ArrayList<ChatUser>();
        for (String txt : list) {
            chatList.add(new ChatUser(txt));
        }
        return chatList;
    }

    public Welcome(Controller controller) {
        init(frame);
        updateUsers(controller);
        List<ChatUser> users = toChatUserList(controller.getUserList(), controller);
        List<ChatMessage> messages = Collections.emptyList();
        users.forEach(System.out::println);
        welcomeLabel.setText("Welcome "+ controller.getLogin());
        System.out.println("Window Activated Event");
        userList.setModel(setListModelUsers(users));
        ChatMessageList messageListTest = new ChatMessageList(user1, user2, addMessagesToList());
        messagesList.setModel(setListModelMessages(messageListTest));
        DefaultListModel<ChatMessage> messageModel = setListModelMessages(messageListTest);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionOnSend(messageModel, controller);
            }
        });

        buttonChange.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    actionOnChange(controller);
        }
        });

        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("User List Selection Activated Event");
                if (!(e.getValueIsAdjusting())) {
                    currentChatUser = userList.getSelectedValue().toString();
                    textArea1.append("Chat with  " + currentChatUser+"\n");
                }
            }
        });

        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    actionOnSend(messageModel, controller);
                }
            }
        });
    }

    //for the display of the chat https://stackoverflow.com/questions/18687607/what-component-should-be-used-to-display-messages-in-a-chat-application
    // https://www.codejava.net/java-se/swing/jlist-custom-renderer-example

    private void init(JFrame frame) {
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
