package main.swe4.data.Entities;

import java.io.Serializable;

public class User implements Serializable {

    private final int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private int score;

    public User(int id, String fname, String lname, String uname, String password) {
        this.id = id;
        firstName = fname;
        lastName = lname;
        userName = uname;
        this.password = password;
        score = 0;
    }

    public int getId() {
        return this.id;
    }

    public String getUserName() {
        return userName;
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

    public boolean isValidLogin(String userName, String password) {
        return (this.userName.equals(userName) && this.password.equals(password));
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
