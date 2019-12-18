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


    //Read the packet received and do something depending on the message
    public void readUDPPacket(UDPPacket packet) {
        String data = packet.getData();
        System.out.println("Packet received");
        System.out.println("- "+data);
        if (data.startsWith("New User : ")){
            String pseudoUser = data.replaceFirst("New User : ", "");
            User newUser = new User(pseudoUser, packet.getInetAddress());
            this.userList.add(newUser);
            sendUDPConnectionReply(newUser.getInetAddress());
            System.out.println("New user on network " + pseudoUser);
            System.out.println("New userList :");
            printUserList();
            System.out.println("Size of userList : "+this.userList.size());
        }
        else if (data.startsWith("User on network : ")){
            String pseudoUser = data.replaceFirst("User on network : ", "");
            User newUser = new User(pseudoUser, packet.getInetAddress());
            userList.add(newUser);
            System.out.println("User already on network " + pseudoUser);
            System.out.println("New userList :");
            printUserList();
            System.out.println("Size of userList : " + this.userList.size());
        }
        
    } 

    

    //Reply to a broadcast
    public void sendUDPConnectionReply(InetAddress address){
        System.out.println("test");
        System.out.println(this.userLogin +" "+ address);
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


    public void printUserList(){
        Iterator<User> iteUser = userList.iterator();
        while (iteUser.hasNext()){
            User currentUser = iteUser.next();
            if (currentUser.getLogin().equals(this.userLogin)){
                System.out.println("(You) "+currentUser.getLogin()+" "+currentUser.getInetAddress());
            }else{
            System.out.println(currentUser.getLogin()+" "+currentUser.getInetAddress());
            }
        }        
    }
    /*
    Get Methods
    */
    public List<User> getUserList(){
        return userList;
    }



    




}