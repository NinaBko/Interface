import java.net.*;
import java.io.*;
import java.lang.*;


public class Client extends Thread{

	private InetAddress destAddr;
	private int port;
	private String msg;

    public Client (User user, int port,String msg) {
		this.destAddr=user.getInetAddress();
		this.port=port;
		this.msg=msg;
		this.start();
		
    }

    public void run() {
		Socket link=null;
		
		try{
			link = new Socket(this.destAddr,this.port);
		}catch(Exception e){
			System.out.println("Error create socket Client"+e);
		}
		
		try {
			
			PrintWriter out = new PrintWriter(link.getOutputStream(),true);
			out.println(this.msg);
			link.close();
		}
		catch (Exception IOException){
			System.out.println("IO");
		}

			
		
	}
		
		
    

}