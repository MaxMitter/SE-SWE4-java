package swe4.gui.data;

import swe4.gui.Exceptions.UserAlreadyExistsException;
import swe4.gui.Exceptions.UserNotFoundException;
import swe4.gui.data.Entities.Game;
import swe4.gui.data.Entities.Role;
import swe4.gui.data.Entities.Team;
import swe4.gui.data.Entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Repository {

    public static Repository Instance = new Repository();
    private ArrayList<User> mockUsers = null;
    private ArrayList<Team> mockTeams = null;
    private ArrayList<Game> mockGames = null;

    private Repository() {
        mockUsers = new ArrayList<>();
        mockTeams = new ArrayList<>();
        mockGames = new ArrayList<>();
        InitMockUsers();
        InitMockTeams();
        InitMockGames();
    }

    private static Repository instance = null;

    public Repository Instance() { if (instance == null) { instance = new Repository(); } return instance;}

    private void InitMockTeams() {
        mockTeams.add(new Team(0, "CBT"));
        mockTeams.add(new Team(1, "DGP"));
        mockTeams.add(new Team(2, "LOW"));
        mockTeams.add(new Team(3, "BIP"));
    }

    private void InitMockGames() {
        mockGames.add(new Game(0, "Group Phase A", getTeamById(0), getTeamById(1), LocalDateTime.now()));
        mockGames.add(new Game(0, "Group Phase A", getTeamById(0), getTeamById(2), LocalDateTime.now()));
        mockGames.add(new Game(0, "Group Phase A", getTeamById(0), getTeamById(3), LocalDateTime.now()));
        mockGames.add(new Game(0, "Group Phase B", getTeamById(1), getTeamById(2), LocalDateTime.now()));
        mockGames.add(new Game(0, "Group Phase B", getTeamById(1), getTeamById(3), LocalDateTime.now()));
        mockGames.add(new Game(0, "Group Phase B", getTeamById(2), getTeamById(3), LocalDateTime.now()));
    }

    private void InitMockUsers() {
        mockUsers.add(new User(0, "", "", Role.ADMIN));
        mockUsers.add(new User(0, "admin", "admin", Role.ADMIN));
        mockUsers.add(new User("Test1", "test"));
        mockUsers.add(new User("Test2", "test"));
    }

    public Team getTeamById(int id) {
        for (Team t: mockTeams) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public boolean isValidLogin(String userName, String password) throws UserNotFoundException {
        for (User user : mockUsers) {
            if (user.getUserName().equals(userName)) {
                return user.checkPassword(password);
            }
        }
        throw new UserNotFoundException();
    }

    public Role getRoleByUserName(String userName) {
        for (User u : mockUsers) {
            if (u.getUserName().equals(userName))
                return u.getRole();
        }
        return null;
    }

    public void createNewUser(String name, String pw) throws UserAlreadyExistsException {
        for (User user : mockUsers) {
            if (user.getUserName().equals(name)) {
                throw new UserAlreadyExistsException();
            }
        }

        mockUsers.add(new User(name, pw));
    }

    public ArrayList<Game> getAllGames() {
        return mockGames;
    }

    public ArrayList<Team> getAllTeams() {
        return mockTeams;
    }

    public ArrayList<User> getAllUsers() { return mockUsers; }
}
