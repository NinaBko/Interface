package Network;

import java.net.*;
import java.io.*;
import java.lang.*;

public class Server {

    private ManagerNetwork manager;

    public Server(ManagerNetwork man) {
        this.manager=man;
        //listen();
        
    }

    public void main(String[] args) {
        ServerSocket servSocket=null;
        try{
            servSocket= new ServerSocket(3600);
            Boolean stop=false;
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