package Network;

import java.lang.*;
import java.net.*;
import java.io.*;


public class TCPListenerThread extends Thread{

    private Socket link;
    private ManagerNetwork manager;

    public TCPListenerThread(Socket s, ManagerNetwork man){
        this.link=s;
        this.manager=man;
        start();
    }

    public void run(){
        String input;
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            input = in.readLine();
            InetAddress destAddr = this.link.getInetAddress();
            System.out.println("[TCP reception] Received \""+input+"\" from "+destAddr);
            this.manager.messageReceived(destAddr,input);
            this.link.close();
        }
        catch (Exception IOException){
            System.out.println("IO1");
        }
    }


}