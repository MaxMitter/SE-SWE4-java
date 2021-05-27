package main.swe4.data.Entities;

import java.io.Serializable;

public class Pair implements Serializable {
    private final String text;
    private final Integer score;

    public Pair(String text, Integer score) {
        this.text = text;
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public Integer getScore() {
        return score;
    }
}
