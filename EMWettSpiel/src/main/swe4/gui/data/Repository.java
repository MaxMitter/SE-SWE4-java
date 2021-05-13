package swe4.gui.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import swe4.gui.Exceptions.*;
import swe4.gui.data.Entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public class Repository {

    public static Repository Instance = new Repository();
    private ArrayList<User> mockUsers = null;
    private ArrayList<Team> mockTeams = null;
    private ArrayList<Game> mockGames = null;
    private ArrayList<UserBetsOnGame> mockBets = null;

    private Repository() {
        mockUsers = new ArrayList<>();
        mockTeams = new ArrayList<>();
        mockGames = new ArrayList<>();
        mockBets = new ArrayList<>();
        InitMockUsers();
        InitMockTeams();
        InitMockGames();
        InitBets();
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
        mockGames.add(new Game(1, "Group Phase A", getTeamById(0), getTeamById(2), LocalDateTime.now()));
        mockGames.add(new Game(2, "Group Phase A", getTeamById(0), getTeamById(3), LocalDateTime.now()));
        mockGames.add(new Game(3, "Group Phase B", getTeamById(1), getTeamById(2), LocalDateTime.now()));
        mockGames.add(new Game(4, "Group Phase B", getTeamById(1), getTeamById(3), LocalDateTime.now()));
        mockGames.add(new Game(5, "Group Phase B", getTeamById(2), getTeamById(3), LocalDateTime.now()));
        mockGames.add(new Game(6, "Group Phase C", getTeamById(2), getTeamById(3), LocalDateTime.parse("2021-05-01T15:00:00")));
        mockGames.add(new Game(7, "Group Phase C", getTeamById(2), getTeamById(3), LocalDateTime.parse("2021-05-01T15:00:00")));
        mockGames.add(new Game(8, "Group Phase C", getTeamById(2), getTeamById(3), LocalDateTime.parse("2021-05-01T15:00:00")));
        mockGames.sort(new Comparator<Game>() {
            @Override
            public int compare(Game o1, Game o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });
    }

    private void InitMockUsers() {
        mockUsers.add(new User(0, "", "", Role.USER));
        mockUsers.add(new User(1, "admin", "admin", Role.ADMIN));
        mockUsers.add(new User(2, "Test1", "test"));
        mockUsers.add(new User(3, "Test2", "test"));
    }

    private void InitBets() {
        mockBets.add(new UserBetsOnGame(0, 0, Bet.TEAM1));
        mockBets.add(new UserBetsOnGame(1, 0, Bet.TEAM2));
        mockBets.add(new UserBetsOnGame(2, 0, Bet.DRAW));
        mockBets.add(new UserBetsOnGame(3, 2, Bet.TEAM1));
        mockBets.add(new UserBetsOnGame(4, 2, Bet.TEAM2));
        mockBets.add(new UserBetsOnGame(5, 2, Bet.DRAW));
    }

    public void AddBet(int userId, int gameId, Bet bet) {
        if (!UpdateBet(userId, gameId, bet)) {
            mockBets.add(new UserBetsOnGame(gameId, userId, bet));
        }
    }

    public boolean UpdateBet(int userId, int gameId, Bet bet) {
        for (UserBetsOnGame ub : mockBets) {
            if (ub.getUserId() == userId && ub.getGameId() == gameId) {
                ub.UpdateBet(bet);
                return true;
            }
        }
        return false;
    }

    public int GetBetPoints(int userId, int gameId) {
        for (UserBetsOnGame ub : mockBets) {
            if (ub.getUserId() == userId && ub.getGameId() == gameId) {
                return ub.getPoints();
            }
        }

        return 0;
    }

    public Team getTeamById(int id) {
        for (Team t: mockTeams) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public int Login(String userName, String password) throws UserNotFoundException {
        for (User user : mockUsers) {
            if (user.getUserName().equals(userName)) {
                if (user.checkPassword(password))
                    return user.getId();
            }
        }
        throw new UserNotFoundException();
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

    public ObservableList<Role> getAllRoles() {
        ObservableList<Role> list = FXCollections.observableArrayList();
        list.add(Role.USER);
        list.add(Role.ADMIN);
        return list;
    }
}
