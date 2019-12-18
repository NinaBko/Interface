import java.io.IOException;
import java.net.*;
import java.util.*;
import java.lang.*;

public class Controller{

    private User user;
    private ManagerNetwork manager;
    private Scanner sc;

    public Controller(){
        //this.sc=new Scanner(System.in);
        //String name= askPseudo(sc);

        InetAddress addr = findUserAddr();
        this.user=new User(null, addr);



        this.manager=new ManagerNetwork(this,this.user);
        //Thread tc =new TerminalCommand(this,this.sc);
        //try{tc.join();}catch(InterruptedException e){}
        //this.sc.close();
        
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

    private String askPseudo(Scanner sc){
        System.out.println("Enter your pseudo :");
        String data = sc.nextLine();
        return data;
    }

    public void printUserList(){
        manager.printUserList();
    }
    
    public void setUserLogin(String login){
        this.user.setLogin(login);
    }

    public void send(String userName,String msg){
        System.out.println("test : "+userName);
        this.manager.sendMessage(userName, msg);
    }

    public void displayMessageReceived(User user, String message){
        System.out.println("Message received from "+user.getLogin()+" : "+message);
    }

    public List<User> getUserList(){
        return this.manager.getUserList();
    }


}