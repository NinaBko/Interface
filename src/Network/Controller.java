package Network;

import Interface.Connect;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.lang.*;
import Interface.Welcome;

public class Controller{

    private User user;
    private ManagerNetwork manager;
    private Scanner sc;

    public Controller(){

        InetAddress addr = findUserAddr();
        this.user=new User(null, addr);

        new Connect(this);

        
    }



    private InetAddress findUserAddr(){
        InetAddress result =null;
        Enumeration<NetworkInterface> inter = null;
        try{
        inter = NetworkInterface.getNetworkInterfaces();
        }
        catch(SocketException e){
            System.out.println("SocketException in findUserAddr");
        }
        while ((result==null)&&(inter.hasMoreElements())){
            NetworkInterface currentInter = inter.nextElement();
            List<InterfaceAddress> listAddr = currentInter.getInterfaceAddresses();
            Iterator<InterfaceAddress> iteAddr = listAddr.iterator();
            while ((result==null) && iteAddr.hasNext()){
                InterfaceAddress currentAddr = iteAddr.next();
                if (currentAddr.getNetworkPrefixLength()==16){
                    result = currentAddr.getAddress();
                }
            }
        }
        return result;
    }


    public void printUserList(){
        manager.printUserList();
    }
    
    public void setUserLogin(String login){
        this.user.setLogin(login);
    }

    public void send(String userName, String msg){
        System.out.println("test : "+userName);
        this.manager.sendMessage(userName, msg);
    }

    public void displayMessageReceived(User user, String message){
        System.out.println("Message received from "+user.getLogin()+" : "+message);
    }

    public List<String> getUserList(){
        List<String> userNameList=new ArrayList<>();
        List<User> userList=this.manager.getUserList();
        for (User u:userList){
            userNameList.add(u.getLogin());
        }
        return userNameList;
    }

    public void launchWelcome(){
        this.manager=new ManagerNetwork(this,this.user);
        new Welcome(this);
    }

    public String getLogin() {
        return this.user.getLogin();
   }

}