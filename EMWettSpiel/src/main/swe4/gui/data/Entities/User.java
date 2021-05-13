package swe4.gui.data.Entities;

public class User {

    private int id;
    private String userName;
    private String password;
    private Role role;

    public User(String name, String password) {
        this(0, name, password);
    }

    public User(int id, String name, String password) {
        this(id, name, password, Role.USER);
    }

    public User(int id, String name, String password, Role r) {
        this.id = id;
        userName = name;
        this.password = password;
        role = r;
    }

    public int getId() {
        return this.id;
    }

    public String getUserName() {
        return userName;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        var len = password.length();
        return "*".repeat(len);
    }

    public boolean checkPassword(String pw) {
        return password.equals(pw);
    }
}
