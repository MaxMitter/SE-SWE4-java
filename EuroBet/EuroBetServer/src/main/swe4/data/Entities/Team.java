package main.swe4.data.Entities;

import java.io.Serializable;

public class Team implements Serializable {

    private final int id;
    private final String name;

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
