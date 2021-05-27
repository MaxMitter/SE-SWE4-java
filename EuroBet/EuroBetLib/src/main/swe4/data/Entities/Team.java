package main.swe4.data.Entities;

public class Team {

    private int id;
    private String name;

    public Team(int Id, String Name) {
        id = Id;
        name = Name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
