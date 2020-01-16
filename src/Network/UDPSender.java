package Network;

import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class UDPSender{

    private DatagramSocket datagramSocket;

    public UDPSender(String login){
        try{
        this.datagramSocket= new DatagramSocket();
        }catch(SocketException e){
            System.out.println("Error create diagram socket sender");
        }
        sendFirstMessage(login);
    }

    public void sendFirstMessage(String login) {
        System.out.println("SEND First message");
        String msg = "New User : "+login;

        
        DatagramSocket dgramSocket = null;
        DatagramPacket outPacket = null;
        try{
            dgramSocket = new DatagramSocket();
        }catch(SocketException e){
            System.out.println("Error create diagram socket sender");
        }
        try{
            outPacket = new DatagramPacket(msg.getBytes(), msg.length(),InetAddress.getByName("10.1.255.255"),3700);
        }catch(UnknownHostException e){
            System.out.println("Error create dgram packet");
        }
        try{
            dgramSocket.send(outPacket);
        }
        catch(IOException e){
            System.out.println("Error dgram send");
        }
        dgramSocket.close();
    }



    public void sendReply(String login, InetAddress address) {
        System.out.println("test reply :"+address);
        String msg = "User on network : "+login;

        DatagramPacket outPacket = null;

        System.out.println("-- "+msg);
        outPacket = new DatagramPacket(msg.getBytes(), msg.length(),address,3700);
        try{
            this.datagramSocket.send(outPacket);
        }
        catch(IOException e){
            System.out.println("Error send dgram packet");
        }
        System.out.println("test3");
    }


    public void sendWrongLogin(InetAddress address) {
        System.out.println("test wrongL :"+address);
        String msg = "Login already taken";

        DatagramPacket outPacket = null;

        System.out.println("-- "+msg);
        outPacket = new DatagramPacket(msg.getBytes(), msg.length(),address,3700);
        try{
            this.datagramSocket.send(outPacket);
        }
        catch(IOException e){
            System.out.println("Error send dgram packet");
        }
        System.out.println("test3");
    }

    public void sendLoginChanged(String login){
        String msg = "New login : "+login;

        DatagramPacket outPacket = null;

        System.out.println("-- "+msg);
        try {
            outPacket = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName("10.1.255.255"), 3700);
        }
        catch(UnknownHostException e){
            System.out.println("Error outpacket send LoginChanged");
        }

        try{
            this.datagramSocket.send(outPacket);
        }
        catch(IOException e){
            System.out.println("Error send dgram packet");
        }
    }

    public void sendDisconnection(){
        String msg = "User disconnected";

        DatagramPacket outPacket = null;

        System.out.println("-- "+msg);
        try {
            outPacket = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName("10.1.255.255"), 3700);
        }
        catch(UnknownHostException e){
            System.out.println("Error outpacket send LoginChanged");
        }

        try{
            this.datagramSocket.send(outPacket);
        }
        catch(IOException e){
            System.out.println("Error send dgram packet");
        }
    }
}