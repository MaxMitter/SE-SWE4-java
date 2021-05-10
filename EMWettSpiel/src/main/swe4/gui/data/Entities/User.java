package swe4.gui.data.Entities;

public class User {

    private String userName;
    private String password;

    public User(String name, String password) {
        userName = name;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public boolean checkPassword(String pw) {
        return password.equals(pw);
    }
}
