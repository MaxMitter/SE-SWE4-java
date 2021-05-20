package swe4.data.Entities;

public class User {

    private final int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private Role role;
    private int score;

    public User(String name, String password) {
        this(0, name, password);
    }

    public User(int id, String name, String password) {
        this(id, "Max", "Mustermann", name, password, Role.USER);
    }

    public User(int id, String fname, String lname, String uname, String password, Role r) {
        this.id = id;
        firstName = fname;
        lastName = lname;
        userName = uname;
        this.password = password;
        role = r;
        score = 0;
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

    public void addScore(int s) {
        score += s;
    }

    public int getScore() {
        return score;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setUserName(String name) {
        userName = name;
    }

    public boolean checkPassword(String pw) {
        return password.equals(pw);
    }
}
