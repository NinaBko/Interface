package Network;

import java.io.IOException;
import java.net.*;
import java.lang.*;

public class UDPListener{

    private ManagerNetwork manager;

    public UDPListener(ManagerNetwork man){
        this.manager=man;
        //listen();
    }

    public void main(String[] args) {
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
                new ReadUDPPacket(packet,this.manager);
            }catch(IOException e){
                System.out.println("Error IO udplist");
            }
            dgramSocket.close();
        }
    }

}