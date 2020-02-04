package Network;

import java.net.*;
import java.io.*;
import java.lang.*;


public class Client {


    public Client () {
		
    }

    public void send(User destUser, String msg) {

		InetAddress destAddr=destUser.getInetAddress();
    	System.out.println("[TCP send] Sending \""+msg+"\" to "+destAddr);
		Socket link=null;
		
		try{
			link = new Socket(destAddr,3600);
		}catch(Exception e){
			System.out.println("Error create socket Client"+e);
		}
		
		try {

			assert link != null;
			PrintWriter out = new PrintWriter(link.getOutputStream(),true);
			out.println(msg);
			link.close();
		}
		catch (Exception IOException){
			System.out.println("IO");
		}

			
		
	}
		
		
    

}