package swe4.data.Entities;

public class Pair {
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
