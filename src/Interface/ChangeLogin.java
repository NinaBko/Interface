package Interface;

import javax.swing.*;
import Network.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeLogin {
    private JPanel panel1;
    private JTextField loginField;
    private JButton OKButton;

    private Controller controller;
    private JFrame frame;

    public ChangeLogin(Controller controller){
        this.controller=controller;
        this.frame=new JFrame("ChangeLogin");
        init();


        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newLogin = loginField.getText();
                if (!newLogin.equals("")){
                    controller.setUserLogin(newLogin);
                    controller.sendChangeInitialLogin();
                    frame.dispose();
                }
            }
        });
    }


    public void init(){
        this.frame.setContentPane(panel1);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);

    }
}
