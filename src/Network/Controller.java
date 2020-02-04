package Network;

import Database.MessageHistory;
import Interface.ChangeLogin;
import Interface.Connect;
import Database.BDD;
import java.net.*;
import java.util.*;
import java.lang.*;
import Interface.Welcome;

public class Controller{

    private User user;
    private String addrBroadcast;
    private ManagerNetwork manager;
    private BDD BDDcon;
    private Welcome mainWindow;

    public Controller(){

        this.user=new User(null, findUserAddr());
        System.out.println("Starting Database connection");
        this.BDDcon=new BDD();
        System.out.println("Database connection established");

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
        assert inter != null;
        while ((result==null)&&(inter.hasMoreElements())){
            NetworkInterface currentInter = inter.nextElement();
            List<InterfaceAddress> listAddr = currentInter.getInterfaceAddresses();
            Iterator<InterfaceAddress> iteAddr = listAddr.iterator();
            while ((result==null) && iteAddr.hasNext()){
                InterfaceAddress currentAddr = iteAddr.next();
                if (currentAddr.getBroadcast()!=null){
                    result = currentAddr.getAddress();

                    this.addrBroadcast=currentAddr.getBroadcast().toString().replaceFirst("/","");
                    System.out.println("Broadcast address : "+this.addrBroadcast);
                    System.out.println("User address : "+result);
                }
            }
        }
        return result;
    }

    
    public void setUserLogin(String login){
        this.user.setLogin(login);
    }

    public void setUserId(String id){this.user.setId(id);}

    public void send(String userName, String msg){
        this.manager.sendMessage(userName, msg);
    }

    public void displayMessageReceived(User user, String message){
        System.out.println("Message received from "+user.getLogin()+" : "+message);
        this.BDDcon.addMessage(user.getId(),this.user.getId(),message);
        this.mainWindow.displayMessage(user.getLogin(),message);
    }


    public void launchWelcome(){
        this.manager=new ManagerNetwork(this,this.user);
        this.mainWindow=new Welcome(this);
    }

    public void changeUserLogin(){
        new ChangeLogin(this);
        this.mainWindow.visible(false);
    }

    public void sendChangeInitialLogin(){
        this.mainWindow.visible(true);
        this.BDDcon.updateLogin(this.user.getId(),this.user.getLogin());
        this.mainWindow.changeLogin(this.user.getLogin());
        this.manager.sendUDPFirst();
    }

    public void sendLoginChangeByUser(){
        this.BDDcon.updateLogin(this.user.getId(),this.user.getLogin());
        this.manager.sendUDPLoginChanged();
    }

    public boolean checkID(String id,String login){
        return this.BDDcon.checkID(id,login);
    }

    public String findId(String login){
        return this.BDDcon.findId(login);
    }

    public void disconnect(){
        this.BDDcon.removeLogin(this.user.getId());
        this.manager.close();
        this.BDDcon.closeDatabase();
    }

    public String getLogin() {
        return this.user.getLogin();
   }

   public void addUser(String login){
        this.mainWindow.addUser(login);
   }

   public void removeUser(String login){
        this.mainWindow.removeUser(login);
   }

   public void changeLogin(String oldLogin,String newLogin){
        this.mainWindow.modifyUser(oldLogin,newLogin);
   }

   public List<MessageHistory> getHistory(String user){
        return this.BDDcon.getHistory(this.BDDcon.findId(this.user.getLogin()),this.BDDcon.findId(user));
   }

   public String getAddrBroadcast(){
        return this.addrBroadcast;
   }

}