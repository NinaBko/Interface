package Interface;

import Network.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Connect {
    private JFrame frame;
    private JPanel contentPane;
    private JButton connectButton;
    private JPanel connectButtonPane;
    private JPanel loginFieldPane;
    private JTextField loginField;
    private JLabel instructionLogin;
    private JTextField idField;
    private JLabel instructionId;

    public Connect(Controller controller) {
        this.frame=new JFrame("Connect");
        init();
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action(controller);
            }
        });

        idField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    action(controller);
                }
            }
        });
    }

    public void init() {
        this.frame.setContentPane(contentPane);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    public void action(Controller controller){
        String login = loginField.getText();
        String id = idField.getText();
        if ((!login.equals(""))&&(!id.equals(""))){
            if(controller.checkID(id,login)){
                controller.setUserLogin(login);
                controller.setUserId(id);
                controller.launchWelcome();
                this.frame.setVisible(false);
                this.frame.dispose();
            }
        }
    }

}
