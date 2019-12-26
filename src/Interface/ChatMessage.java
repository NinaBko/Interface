package Interface;

import java.time.LocalDateTime;

public class ChatMessage {
    private ChatUser user;
    //check compatibility sql
    private LocalDateTime date;
    private String message;

    public ChatMessage(ChatUser user, LocalDateTime date, String message) {
        this.user = user;
        this.date = date;
        this.message = message;
    }

    public ChatUser getUser() {
        return user;
    }

    public void setUser(ChatUser user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "user=" + user +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }
}
