package Database;

import java.time.LocalDate;
import java.time.LocalTime;

public class MessageHistory {
    private String sender;
    private String receiver;
    private LocalDate date;
    private LocalTime time;
    private String message;

    public MessageHistory(String s, String r, LocalDate d, LocalTime t , String m){
        this.sender=s;
        this.receiver=r;
        this.date=d;
        this.time=t;
        this.message=m;
    }

    public String getSender(){
        return this.sender;
    }
    public String getReceiver(){
        return this.receiver;
    }
    public LocalDate getDate(){
        return this.date;
    }
    public LocalTime getTime(){
        return this.time;
    }
    public String getMessage(){
        return this.message;
    }
}
