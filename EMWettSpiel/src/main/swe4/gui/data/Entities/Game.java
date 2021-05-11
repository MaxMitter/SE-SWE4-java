package swe4.gui.data.Entities;

import java.time.LocalDateTime;

public class Game {

    private int id;
    private String name;
    private Team t1;
    private Team t2;
    private LocalDateTime time;
    private int scoreT1;
    private int scoreT2;

    public Game(int Id, String Name, Team team1, Team team2, LocalDateTime dateTime) {
        id = Id;
        name = Name;
        t1 = team1;
        t2 = team2;
        time = dateTime;
        scoreT1 = 0;
        scoreT2 = 0;
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
}
