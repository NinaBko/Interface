package Interface;

import Network.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Connect extends JFrame{
    private JPanel contentPane;
    private JButton connectButton;
    private JPanel connectButtonPane;
    private JPanel loginFieldPane;
    private JTextField loginField;
    private JLabel instructionLogin;

    public Connect(Controller controller) {
        init();
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText();
                controller.setUserLogin(login);
                controller.launchWelcome();
            }
        });
    }

    //public static String getLogin(){return login;}

    public void init() {
        JFrame frame = new JFrame("Connect");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
