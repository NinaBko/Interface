package Network;

import java.net.*;
import java.io.*;
import java.lang.*;

public class Server extends Thread {

    private ManagerNetwork manager;

    public Server(ManagerNetwork man) {
        this.manager=man;
        start();
        
    }

    public void run() {
        System.out.println("TCP Server launched. Wait for connection");
        ServerSocket servSocket=null;
        try{
            servSocket= new ServerSocket(3600);
            boolean stop=false;
            while(!stop){
            new TCPListenerThread(servSocket.accept(),this.manager);
            }  
            
        }
        catch (IOException e){
            System.out.println("IO2");
        }
         

        try{
            servSocket.close();
            
        }catch (IOException e){System.out.println("IO3");}
        
    }

}