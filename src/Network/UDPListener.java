package Network;

import java.io.IOException;
import java.net.*;
import java.lang.*;

public class UDPListener extends Thread{

    private ManagerNetwork manager;
    private InetAddress ourAddr;

    public UDPListener(ManagerNetwork man, InetAddress addr){
        this.manager=man;
        this.ourAddr=addr;
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
                System.out.println(packet.getInetAddress()+" -- "+ this.ourAddr);
                if (!packet.getInetAddress().equals(this.ourAddr)) {
                    new ReadUDPPacket(packet, this.manager);
                }
            }catch(IOException e){
                System.out.println("Error IO udplist");
            }
            dgramSocket.close();
        }
    }

}