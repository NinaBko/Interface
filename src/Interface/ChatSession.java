package Interface;

import Database.MessageHistory;
import Network.Controller;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ChatSession {
    private JTextField textField1;
    private JButton sendButton;
    private JTextArea textArea1;
    private JPanel panel1;
    private JLabel titleLabel;
    private JFrame frame=new JFrame("Chat Session");

    private String distUser;


    public ChatSession(Welcome w,Controller c, String user){
        init();
        this.distUser=user;
        this.titleLabel.setText("Talking with "+user);
        loadHistory(c);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action(c);
            }
        });

        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    action(c);
                }
            }
        });

        this.frame.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Close Session with "+distUser);
                w.sessionClosed(distUser);
            }
        });
    }

    public void init(){

        this.frame.setContentPane(panel1);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);
    }


    public void action(Controller c){
        String message = this.textField1.getText();
        if (!message.equals("")){
            c.send(this.distUser,message);
            this.textField1.setText("");
            display(c.getLogin(),message);
        }
    }

    public void changeDistUser(String login){
        this.distUser=login;
        this.titleLabel.setText("Talking with "+this.distUser);
    }

    public String getDistUser(){
        return this.distUser;
    }

    public void display(String user,String message){
        String date = LocalDate.now().toString();
        String time = LocalTime.now().toString();
        String datetime  = date + " " + time.substring(0, time.length()-7);
        this.textArea1.append("~~ "+user+" ~~"+datetime+"\n");
        this.textArea1.append(message+"\n");
    }
    public void display2(String user,String message,LocalDate date,LocalTime time){
        String d = date.toString();
        String t = time.toString();
        String datetime  = d + " " + t;
        this.textArea1.append("~~ "+user+" ~~"+datetime+"\n");
        this.textArea1.append(message+"\n");
    }

    public void loadHistory(Controller c){
        List<MessageHistory> previousMessage = c.getHistory(distUser);
        for (MessageHistory current : previousMessage){
            display2(current.getSender(),current.getMessage(),current.getDate(),current.getTime());
        }
    }

    public void close(){
        this.frame.dispose();
    }
}
