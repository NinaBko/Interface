package Network;

import java.net.*;
import java.util.*;
import java.io.*;

public class User{
    private String userLogin;
    private String id;
    private InetAddress addr;

    public User(String login, InetAddress addr){
        this.userLogin=login;
        this.addr=addr;
    }
        

    /*
    Get Methods
    */
    public String getLogin(){
        return this.userLogin;
    }

    public String getId(){return this.id;}

    public InetAddress getInetAddress(){
        return this.addr;
    }

    /*
    Set Methods
    */

    public void setLogin(String login){
        this.userLogin=login;
    }

    public void setId(String id){this.id=id;}

    public String toString(){
        return this.userLogin;
    }

    

}