package main.swe4.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.swe4.Exceptions.*;
import main.swe4.data.Entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class Repository {

    private ArrayList<User> mockUsers = null;
    private ArrayList<Team> mockTeams = null;
    private ArrayList<Game> mockGames = null;
    private ArrayList<UserBetsOnGame> mockBets = null;
    private static int nextId = 0;

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

    public static Repository Instance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    private void InitMockTeams() {
        mockTeams.add(new Team(0, "AUT"));
        mockTeams.add(new Team(1, "GER"));
        mockTeams.add(new Team(2, "ITA"));
        mockTeams.add(new Team(3, "FRA"));
    }

    private void InitMockGames() {
        mockGames.add(new Game(0, "Group Phase A", GetTeamById(0), GetTeamById(1), LocalDateTime.now()));
        mockGames.add(new Game(1, "Group Phase A", GetTeamById(0), GetTeamById(2), LocalDateTime.now()));
        mockGames.add(new Game(2, "Group Phase A", GetTeamById(0), GetTeamById(3), LocalDateTime.now()));
        mockGames.add(new Game(3, "Group Phase B", GetTeamById(1), GetTeamById(2), LocalDateTime.now()));
        mockGames.add(new Game(4, "Group Phase B", GetTeamById(1), GetTeamById(3), LocalDateTime.now()));
        mockGames.add(new Game(5, "Group Phase B", GetTeamById(2), GetTeamById(3), LocalDateTime.now()));
        mockGames.add(new Game(6, "Group Phase C", GetTeamById(0), GetTeamById(3), LocalDateTime.parse("2021-05-01T15:00:00")));
        mockGames.add(new Game(7, "Group Phase C", GetTeamById(1), GetTeamById(3), LocalDateTime.parse("2021-05-01T15:00:00")));
        mockGames.add(new Game(8, "Group Phase C", GetTeamById(2), GetTeamById(3), LocalDateTime.parse("2021-05-01T15:00:00")));
        mockGames.add(new Game(9, "Group Phase D", GetTeamById(1), GetTeamById(3), LocalDateTime.parse("2021-07-01T15:00:00")));
        mockGames.add(new Game(10, "Group Phase D", GetTeamById(2), GetTeamById(3), LocalDateTime.parse("2021-07-01T15:00:00")));
        mockGames.add(new Game(11, "Group Phase D", GetTeamById(0), GetTeamById(3), LocalDateTime.parse("2021-07-01T15:00:00")));
        mockGames.add(new Game(999, "Test Bet", GetTeamById(0), GetTeamById(3), LocalDateTime.parse("2021-07-01T15:00:00")));
        mockGames.sort(new Comparator<Game>() {
            @Override
            public int compare(Game o1, Game o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });
    }

    private void InitMockUsers() {
        mockUsers.add(new User(nextId++, "", "", "", ""));
        mockUsers.add(new User(nextId++, "Max", "Mustermann", "Test1", "test"));
        mockUsers.add(new User(nextId++, "Maria", "Musterfrau", "Test2", "test"));
    }

    private void InitBets() {
        mockBets.add(new UserBetsOnGame(6, 0, Bet.TEAM1));
        mockBets.add(new UserBetsOnGame(6, 0, Bet.TEAM2));
        mockBets.add(new UserBetsOnGame(7, 0, Bet.DRAW));
        mockBets.add(new UserBetsOnGame(7, 2, Bet.TEAM1));
        mockBets.add(new UserBetsOnGame(7, 2, Bet.TEAM2));
        mockBets.add(new UserBetsOnGame(7, 2, Bet.DRAW));
        mockBets.add(new UserBetsOnGame(999, 2, Bet.DRAW, LocalDateTime.parse("2021-07-01T15:00:00")));
        mockBets.add(new UserBetsOnGame(999, 2, Bet.DRAW, LocalDateTime.parse("2021-07-01T15:50:00")));
        mockBets.add(new UserBetsOnGame(999, 2, Bet.DRAW, LocalDateTime.parse("2021-07-01T16:34:00")));
        mockBets.add(new UserBetsOnGame(999, 2, Bet.DRAW, LocalDateTime.parse("2021-07-01T16:37:00")));
        mockBets.add(new UserBetsOnGame(999, 2, Bet.DRAW, LocalDateTime.parse("2021-07-01T16:50:00")));
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

    public void FinalizeBets(int gameId, Bet b) {
        for (UserBetsOnGame ub : mockBets) {
            if (ub.getGameId() == gameId) {
                ub.calcPoints(b);
                AddPointsToUser(ub.getUserId(), ub.getPoints());
            }
        }
    }

    public void FinalizeAllBets() {
        for (Game g : mockGames) {
            if (g.getTime().plusMinutes(90).isBefore(LocalDateTime.now())) {
                Bet b = Bet.NONE;
                if (g.getScoreT1() == g.getScoreT2())
                    b = Bet.DRAW;
                else if (g.getScoreT1() > g.getScoreT2())
                    b = Bet.TEAM1;
                else
                    b = Bet.TEAM2;
                FinalizeBets(g.getId(), b);
            }
        }
    }

    public void AddPointsToUser(int userId, int points) {
        for (User u : mockUsers) {
            if (u.getId() == userId) {
                u.addScore(points);
                break;
            }
        }
    }

    public ObservableList<Pair> GetAllPoints() {
        ObservableList<Pair> highscore = FXCollections.observableArrayList();
        for (User u : mockUsers) {
            String s = u.getFirstName() + " " + u.getLastName() + ": " + u.getScore() + " Punkte";
            highscore.add(new Pair(s, u.getScore()));
        }
        return highscore;
    }

    public Team GetTeamById(int id) {
        for (Team t : mockTeams) {
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

    public boolean IsValidLogin(String userName, String password) throws UserNotFoundException {
        for (User user : mockUsers) {
            if (user.getUserName().equals(userName)) {
                return user.checkPassword(password);
            }
        }
        throw new UserNotFoundException();
    }

    public void CreateNewUser(String firstName, String lastName, String name, String pw) throws UserAlreadyExistsException {
        for (User user : mockUsers) {
            if (user.getUserName().equals(name)) {
                throw new UserAlreadyExistsException();
            }
        }

        mockUsers.add(new User(nextId++, firstName, lastName, name, pw));
    }

    public ArrayList<Game> getAllGames() {
        return mockGames;
    }

    public Game GetGameById(int gameId) {
        for (Game g : mockGames) {
            if (g.getId() == gameId)
                return g;
        }
        return null;
    }

    public ArrayList<Team> GetAllTeams() {
        return mockTeams;
    }

    public ArrayList<User> GetAllUsers() {
        return mockUsers;
    }

    public ArrayList<UserBetsOnGame> GetAllBets() {
        return mockBets;
    }

    public void DeleteBet(UserBetsOnGame bet) {
        mockBets.remove(bet);
    }

    public void AddGame(Game game) {
        mockGames.add(game);
    }

    public void UpdateGame(Game game) {
        for (var g : mockGames) {
            if (g.getId() == game.getId()) {
                g.setName(game.getName());
                g.setScoreT1(game.getScoreT1());
                g.setScoreT2(game.getScoreT2());
                break;
            }
        }
    }

    public void DeleteGame(Game game) {
        mockGames.remove(game);
    }

    public void CreateUser(User user) {
        mockUsers.add(user);
    }

    public void UpdateUser(User user) {
        for (var u : mockUsers) {
            if (u.getId() == user.getId()) {
                u.setUserName(user.getUserName());
                u.setFirstName(user.getFirstName());
                u.setLastName(user.getLastName());
                u.setScore(user.getScore());
                break;
            }
        }
    }

    public void UpdateUserPassword(int userId, String password) {
        for (var u : mockUsers) {
            if (u.getId() == userId)
                u.updatePassword(password);
        }
    }

    public void DeleteUser(User user) {
        mockUsers.remove(user);
    }
}
