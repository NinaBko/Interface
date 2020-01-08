package Network;

import java.lang.*;

public class ReadUDPPacket extends Thread{

    private UDPPacket packet;
    private ManagerNetwork manager;

    public ReadUDPPacket(UDPPacket packet, ManagerNetwork man){
        this.packet=packet;
        this.manager=man;
        start();
    }


    public void run(){
        String data = this.packet.getData();
        System.out.println("Packet received");
        System.out.println("- "+data);
        if (data.startsWith("New User : ")){
            String pseudoUser = data.replaceFirst("New User : ", "");
            User newUser = new User(pseudoUser, packet.getInetAddress());
            this.manager.addUser(newUser);
        }
        else if (data.startsWith("User on network : ")){
            String pseudoUser = data.replaceFirst("User on network : ", "");
            User newUser = new User(pseudoUser, packet.getInetAddress());
            this.manager.addUser(newUser);
            System.out.println("User already on network " + pseudoUser);
            System.out.println("New userList :");
            this.manager.printUserList();
        }
        else if (data.startsWith("New login :")){
            String pseudoUser = data.replaceFirst("New login : ", "");
            this.manager.replaceUser(pseudoUser, packet.getInetAddress());
        }
        else if (data.startsWith("Login already taken")){
            this.manager.changeLoginUser();
        }
    }



}