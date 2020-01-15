package Interface;

import Network.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Connect extends JFrame{
    private JPanel contentPane;
    private JButton connectButton;
    private JPanel connectButtonPane;
    private JPanel loginFieldPane;
    private JTextField loginField;
    private JLabel instructionLogin;
    private JTextField idField;
    private JLabel instructionId;

    public Connect(Controller controller, int mode) {
        init();
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action(controller,mode);
            }
        });

        idField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    action(controller,mode);
                }
            }
        });
    }

    public void init() {
        JFrame frame = new JFrame("Connect");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void action(Controller controller, int mode){
        String login = loginField.getText();
        String id = idField.getText();
        if ((!login.equals(""))&&(!id.equals(""))){
            if(controller.checkID(id,login)){
                controller.setUserLogin(login);
                if (mode==1) {
                    controller.launchWelcome();
                }
                else if (mode==2){
                    controller.sendChangeInitialLogin();
                }
            }
        }
    }
}
