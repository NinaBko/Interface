import java.net.*;

public class UDPPacket{

    private InetAddress addr;
    private String data;
    private int port;

    public UDPPacket(DatagramPacket dp){
        this.addr=dp.getAddress();
        this.data= new String(dp.getData(),0,dp.getLength());
        this.port=dp.getPort();
    }

    public InetAddress getInetAddress(){
        return this.addr;
    }

    public String getData(){
        return this.data;
    }

    public int getPort(){
        return this.port;
    }

}