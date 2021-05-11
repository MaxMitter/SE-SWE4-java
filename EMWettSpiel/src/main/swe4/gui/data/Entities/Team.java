package swe4.gui.data.Entities;

public class Team {

    private int id;
    private String name;

    public Team (int Id, String Name) {
        id = Id;
        name = Name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
