package Network;

import java.io.IOException;
import java.net.*;
import java.lang.*;

public class UDPListener extends Thread{

    private ManagerNetwork manager;

    public UDPListener(ManagerNetwork man){
        this.manager=man;
        start();
    }

    public void run() {
        while(true){
            DatagramSocket dgramSocket = null;
            try{
                dgramSocket = new DatagramSocket(3700);
            }
            catch(SocketException e){
                System.out.println("Error create DatagramSocket");
            }

            byte[] buffer = new byte[256];
            DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
            try{
                dgramSocket.receive(inPacket);            
                UDPPacket packet = new UDPPacket(inPacket);
                System.out.println("tt2");
                new ReadUDPPacket(packet,this.manager);
                System.out.println("tt3");
            }catch(IOException e){
                System.out.println("Error IO udplist");
            }
            dgramSocket.close();
        }
    }

}