package Interface;

import java.util.List;

public class ChatMessageList {
    private ChatUser firstUser;
    private ChatUser secondUser;
    private List<ChatMessage> messages;

    /** Getters and Setters */
    public ChatUser getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(ChatUser firstUser) {
        this.firstUser = firstUser;
    }

    public ChatUser getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(ChatUser secondUser) {
        this.secondUser = secondUser;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    /**Constructor*/
    public ChatMessageList(ChatUser firstUser, ChatUser secondUser, List<ChatMessage> messages) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.messages = messages;
    }
}
