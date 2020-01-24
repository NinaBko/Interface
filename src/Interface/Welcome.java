package Interface;

import Network.Controller;
import Network.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Welcome{

    private JPanel contentPane;
    private JLabel welcomeLabel;
    private JPanel mainPane;
    private JLabel userListLabel;
    private DefaultListModel<String> listModel=new DefaultListModel<>();
    public JList<String> userList;
    private JTextField loginField;
    private JButton buttonChange;
    private JFrame frame = new JFrame("Welcome to the chat");

    private List<ChatSession> listSession = new ArrayList<>();




    public Welcome(Controller controller, String mainUser) {
        init(frame,mainUser);
        welcomeLabel.setText("Welcome " + controller.getLogin());


        buttonChange.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    actionOnChange(controller);
        }
        });

        this.userList.addMouseListener((new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    int index = userList.locationToIndex(e.getPoint());
                    String user = listModel.get(index);
                    System.out.println("Clicked on "+user);
                    newSession(controller,user);
                }
            }
        }));

        this.frame.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Closing App");
                controller.disconnect();
            }
        });

    }


    private void init(JFrame frame, String mainUserLogin) {
        listModel.addElement(mainUserLogin);
        this.userList.setModel(listModel);
        this.userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    private void actionOnChange(Controller controller){
        String newLogin = loginField.getText();
        String oldLogin = controller.getLogin();
        if (!newLogin.equals("")) {
            controller.setUserLogin(newLogin);
            welcomeLabel.setText("Welcome " + controller.getLogin());
            loginField.setText("");
            controller.sendLoginChangeByUser();
            modifyUser(oldLogin,newLogin);
        }
    }

    public void visible(boolean b){
        this.frame.setVisible(b);
    }

    public void addUser(String newUser){
        this.listModel.addElement(newUser);
    }

    public void removeUser(String oldUser){
        this.listModel.removeElement(oldUser);
        ChatSession toRemove=null;
        for (ChatSession current:listSession){
            if (current.getDistUser().equals(oldUser)){
                toRemove=current;
            }
        }
        toRemove.close();
        this.listSession.remove(toRemove);
    }

    public void modifyUser(String oldLogin,String newLogin){
        int index =this.listModel.indexOf(oldLogin);
        this.listModel.setElementAt(newLogin,index);
        for (ChatSession current: listSession){
            if (current.getDistUser().equals(oldLogin)){
                current.changeDistUser(newLogin);
            }
        }
    }

    public void newSession(Controller c,String distUser){
        boolean found=false;
        for (ChatSession current : listSession){
            if (current.getDistUser().equals(distUser)){
                found=true;
                break;
            }
        }
        if (!found) {
            this.listSession.add(new ChatSession(this, c, distUser));
        }
    }
    public void sessionClosed(String distUser){
        this.listSession.removeIf(current -> current.getDistUser().equals(distUser));
    }

    public void displayMessage(String distUser,String message){
        for (ChatSession current:listSession){
            if (current.getDistUser().equals(distUser)){
                current.display(distUser,message);
            }
        }
    }
}
