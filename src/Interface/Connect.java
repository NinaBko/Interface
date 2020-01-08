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

    public Connect(Controller controller, int mode) {
        init();
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText();
                controller.setUserLogin(login);
                controller.launchWelcome();
            }
        });

        loginField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    String login = loginField.getText();
                    controller.setUserLogin(login);
                    if (mode==1) {
                        controller.launchWelcome();

                    }
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
}
