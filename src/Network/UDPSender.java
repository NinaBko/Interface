package Network;

import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class UDPSender{

    private DatagramSocket datagramSocket;
    private String addr;

    public UDPSender(String login,String addrBroadcast){
        this.addr=addrBroadcast;
        try{
        this.datagramSocket= new DatagramSocket();
        }catch(SocketException e){
            System.out.println("Error create diagram socket sender");
        }
        sendFirstMessage(login);
    }

    public void sendFirstMessage(String login) {
        System.out.println("[Broadcast UDP] First message");
        String msg = "New User : "+login;

        
        DatagramSocket dgramSocket = null;
        DatagramPacket outPacket = null;
        try{
            dgramSocket = new DatagramSocket();
        }catch(SocketException e){
            System.out.println("Error create diagram socket sender");
        }
        try{
            outPacket = new DatagramPacket(msg.getBytes(), msg.length(),InetAddress.getByName(this.addr),3700);
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
        System.out.println("[Reply UDP] To "+address);
        String msg = "User on network : "+login;
        byte[] sendData = new byte[256];

        DatagramPacket outPacket = null;

        sendData=msg.getBytes();
        outPacket = new DatagramPacket(sendData, sendData.length,address,3700);
        try{
            this.datagramSocket.send(outPacket);
        }
        catch(IOException e){
            System.out.println("Error send dgram packet");
        }
    }


    public void sendWrongLogin(InetAddress address) {
        System.out.println("[UDP] User at "+address+" has a login already taken");
        String msg = "Login already taken";

        DatagramPacket outPacket = null;

        outPacket = new DatagramPacket(msg.getBytes(), msg.length(),address,3700);
        try{
            this.datagramSocket.send(outPacket);
        }
        catch(IOException e){
            System.out.println("Error send dgram packet");
        }
    }

    public void sendLoginChanged(String login){
        System.out.println("[Broadcast UDP] My new login : "+login);
        String msg = "New login : "+login;

        DatagramPacket outPacket = null;

        try {
            outPacket = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName(this.addr), 3700);
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
        System.out.println("[Broadcast UDP] Leaving network");
        String msg = "User disconnected";

        DatagramPacket outPacket = null;

        try {
            outPacket = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName(this.addr), 3700);
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