package Interface;

public class ChatUser {
    private String Login;

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public ChatUser(String login) {
        Login = login;
    }

    @Override
    public String toString() {
        return Login;

    }
}
