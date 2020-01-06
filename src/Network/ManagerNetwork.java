package Network;

import java.io.*;
import java.net.*;
import java.util.*;

public class ManagerNetwork{

    private UDPSender udpSend;
    private User user;
    private String userLogin;
    private List<User> userList;
    private Controller control;

    public ManagerNetwork(Controller c,User user){
        this.userLogin = user.getLogin();
        this.udpSend = new UDPSender(this.userLogin);
        new UDPListener(this);

        this.user=user;
        this.control=c;

        this.userList= new ArrayList<>();
        userList.add(user);
        new Server(this);
    }



    //Reply to a broadcast
    public void sendUDPConnectionReply(InetAddress address){
        this.udpSend.sendReply(this.userLogin,address);
    }
    
  

    public void sendMessage(String userName, String msg){
        Boolean stop = false;
        User destUser= new User(null, null);
        int i=0;
        while (i<this.userList.size() && !stop){
            destUser = this.userList.get(i);
            System.out.println(destUser.getLogin());
            if (destUser.getLogin().equals(userName)){
                System.out.println("found");
                stop=true;
            }
            i++;
        }
        if (!stop){System.out.println("User Not found in sendMessage");}
        else{
            System.out.println(destUser.getLogin());
            new Client(destUser, 3600, msg);
        }
    }

    public void MessageReceived(InetAddress destAddr,String msg){
        Boolean found=false;
        User destUser =null;
        System.out.println(destAddr);
        for (User u : this.userList){
            System.out.println("- "+u.getInetAddress());
            if (u.getInetAddress().equals(destAddr)){
                found=true;
                destUser=u;
            }
        }
        if (found){
            this.control.displayMessageReceived(destUser, msg);
        }else{
            System.out.println("Message received but no user exist at this addr");
        }
    }

    synchronized public void addUser(User user){
        this.userList.add(user);
    }


    public void printUserList(){
        Iterator<User> iteUser = userList.iterator();
        while (iteUser.hasNext()){
            User currentUser = iteUser.next();
            System.out.println(currentUser.getLogin()+" "+currentUser.getInetAddress());

        }        
    }
    /*
    Get Methods
    */
    public List<User> getUserList(){
        return userList;
    }



    




}