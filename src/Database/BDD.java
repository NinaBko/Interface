package Database;

import java.sql.*;
import java.io.*;

public class BDD {

    public BDD(){
        action();
    }

    public void action(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tpservlet_04","tpservlet_04","ua1laeW1");

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from user");
            while(rs.next())
                System.out.println(rs.getString(1));
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }


}
