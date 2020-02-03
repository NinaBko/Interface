package Database;

import java.sql.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BDD {

    private Connection con;

    public BDD(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.con=DriverManager.getConnection("jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tpservlet_04","tpservlet_04","ua1laeW1");



        }catch(Exception e){ System.out.println(e);}
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
            System.out.println("[BDD] "+t + " row changed in table user");
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
            System.out.println("[BDD] "+t + " row changed in table user");
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
    public String findLogin(String id){
        String login="";
        try{
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("select login from user where id='"+id+"'");
            rs.next();
            login =rs.getString(1);
            rs.close();
            stmt.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return login;
    }

    public void addMessage(String sender, String receiver, String message){
        String Date = LocalDate.now().toString();
        String Time = LocalTime.now().toString();
        String DateSql = Date + " " + Time.substring(0, Time.length()-7);
        int id=0;
        try{
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from message");
            rs.next();
            id=rs.getInt(1);
            rs.close();
            System.out.println("[BDD] Add message : "+message+" in table message");
            stmt.executeUpdate("INSERT INTO message (id,sender,receiver,date,data)" +
                    "               VALUES ('"+id+"','"+sender+"','"+receiver+"','"+DateSql+"','"+message+"')");
            stmt.close();
        }
        catch(Exception e){System.out.println(e);}

    }

    public List<MessageHistory> getHistory(String user1,String user2){
        List<MessageHistory> history = new ArrayList<>();
        try {
            Statement stmt = this.con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from message where (receiver='"+user1+"' AND sender='"+user2+"')OR(receiver='"+user2+"' AND sender='"+user1+"') order by date");
            while(rs.next()){
                String sender=findLogin(rs.getString(2));
                String receiver=findLogin(rs.getString(3));
                String message=rs.getString(5);
                java.sql.Time dbSqlTime = rs.getTime(4);
                java.sql.Date dbSqlDate = rs.getDate(4);
                LocalTime time = dbSqlTime.toLocalTime();
                LocalDate date = dbSqlDate.toLocalDate();
                MessageHistory newMessage = new MessageHistory(sender,receiver,date,time,message);
                history.add(newMessage);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return history;
    }

}
