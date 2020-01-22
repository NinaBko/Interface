package Database;

import java.sql.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class BDD {

    private Connection con;

    public BDD(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.con=DriverManager.getConnection("jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tpservlet_04","tpservlet_04","ua1laeW1");



        }catch(Exception e){ System.out.println(e);}
    }


    public void printTableUser(){
        try {
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from user");
            while (rs.next())
                System.out.println(rs.getString(1));
            rs.close();
            stmt.close();
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

    public boolean checkID(String id,String login){
        boolean inTable=false;
        try {
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from user");
            while (rs.next()&&!inTable){
                if (rs.getString(1).equals(id)){
                    inTable=true;
                    updateLogin(id,login);
                }
            }
            rs.close();
            stmt.close();
        }catch(Exception e){
            System.out.println(e);
        }

        return inTable;
    }

    public void updateLogin(String id, String login){
        try {
            Statement stmt = this.con.createStatement();
            int t = stmt.executeUpdate("update user set login='" + login + "' where id='" + id + "'");
            stmt.close();
            System.out.println(t + " row changed");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void removeLogin(String id){
        try {
            Statement stmt = this.con.createStatement();
            int t = stmt.executeUpdate("update user set login=NULL where id='" + id + "'");
            stmt.close();
            System.out.println(t + " row changed");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public String findId(String login){
        String id="";
        try{
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("select id from user where login='"+login+"'");
            rs.next();
            id =rs.getString(1);
            rs.close();
            stmt.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return id;
    }

    public void addMessage(String sender, String receiver, String message){
        String Date = LocalDate.now().toString();
        String Time = LocalTime.now().toString();
        String DateSql = Date + " " + Time.substring(0, Time.length()-4);
        int id=0;
        try{
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from message");
            rs.next();
            id=rs.getInt(1);
            rs.close();
            System.out.println("test");
            stmt.executeUpdate("INSERT INTO message (id,sender,receiver,date,data)" +
                    "               VALUES ('"+id+"','"+sender+"','"+receiver+"','"+DateSql+"','"+message+"')");
            stmt.close();
        }
        catch(Exception e){System.out.println(e);}

    }



}
