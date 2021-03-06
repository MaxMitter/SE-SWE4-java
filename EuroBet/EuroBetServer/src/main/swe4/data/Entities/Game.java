package main.swe4.data.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Game implements Serializable {

    private final int id;
    private String name;
    private Team t1;
    private Team t2;
    private LocalDateTime time;
    private int scoreT1;
    private int scoreT2;

    public Game(int Id, String Name, Team team1, Team team2, LocalDateTime dateTime) {
        this(Id, Name, team1, team2, dateTime, 0,0);
    }

    public Game(int Id, String Name, Team team1, Team team2, LocalDateTime dateTime, int score1, int score2) {
        id = Id;
        name = Name;
        t1 = team1;
        t2 = team2;
        time = dateTime.truncatedTo(ChronoUnit.MINUTES);
        scoreT1 = score1;
        scoreT2 = score2;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Team getT1() {
        return t1;
    }

    public Team getT2() {
        return t2;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public int getScoreT1() {
        return scoreT1;
    }

    public int getScoreT2() {
        return scoreT2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setT1(Team t1) {
        this.t1 = t1;
    }

    public void setT2(Team t2) {
        this.t2 = t2;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setScoreT1(int scoreT1) {
        this.scoreT1 = scoreT1;
    }

    public void setScoreT2(int scoreT2) {
        this.scoreT2 = scoreT2;
    }
}
