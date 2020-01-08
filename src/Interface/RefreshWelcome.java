package Interface;

import Network.Controller;

import java.util.List;

public class RefreshWelcome implements Runnable {
    private String name;
    private Thread t;
    private Welcome window;
    private Controller controller;

    RefreshWelcome (String threadName, Welcome welcome, Controller c){
        name = threadName;
        t = new Thread(this, name);
        window = welcome;
        controller = c;
        System.out.println("New thread: " + t);
        t.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                System.out.println("Refresh");
                window.userList.setModel(window.setListModelUsers(window.toChatUserList(controller.getUserList(), controller)));
            }
        } catch (InterruptedException e) {
            System.out.println(name + "Interrupted");
        }
    }
}
