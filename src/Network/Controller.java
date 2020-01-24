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
    private ManagerNetwork manager;
    private BDD BDDcon;
    private Welcome mainWindow;

    public Controller(){

        InetAddress addr = findUserAddr();
        this.user=new User(null, addr);

        this.BDDcon=new BDD();
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
                if (currentAddr.getNetworkPrefixLength()==16){
                    result = currentAddr.getAddress();
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
        System.out.println("test : "+userName);
        this.manager.sendMessage(userName, msg);
    }

    public void displayMessageReceived(User user, String message){
        System.out.println("Message received from "+user.getLogin()+" : "+message);
        this.BDDcon.addMessage(user.getId(),this.user.getId(),message);
        this.mainWindow.displayMessage(user.getLogin(),message);
    }


    public void launchWelcome(){
        this.manager=new ManagerNetwork(this,this.user);
        this.mainWindow=new Welcome(this, this.user.getLogin());
    }

    public void changeUserLogin(){
        new ChangeLogin(this);
    }

    public void sendChangeInitialLogin(){
        this.BDDcon.updateLogin(this.user.getId(),this.user.getLogin());
        this.mainWindow.visible(true);
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

}