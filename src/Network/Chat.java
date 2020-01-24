package Network;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Chat{
    public static void main(String[] args){

        Scanner sc =new Scanner(System.in);
        System.out.println("Entre your broadcast address");
        String addr= sc.nextLine();
        sc.close();


        new Controller(addr);
    }
}