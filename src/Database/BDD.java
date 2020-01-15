package Database;

import java.sql.*;
import java.io.*;

public class BDD {

    private Connection con;
    private Statement stmt;

    public BDD(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.con=DriverManager.getConnection("jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tpservlet_04","tpservlet_04","ua1laeW1");
            this.stmt=con.createStatement();


        }catch(Exception e){ System.out.println(e);}
    }


    public void printTableUser(){
        try {
            ResultSet rs = stmt.executeQuery("select * from user");
            while (rs.next())
                System.out.println(rs.getString(1));
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void closeDatabase(){
        try{
            this.con.close();
        }
        catch(SQLException e){
            System.out.println("Error close database");
        }
    }


}
