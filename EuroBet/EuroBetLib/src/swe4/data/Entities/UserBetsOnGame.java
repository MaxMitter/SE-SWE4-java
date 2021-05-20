package swe4.data.Entities;

import java.time.LocalDateTime;

public class UserBetsOnGame {
    private final int gameId;
    private final int userId;
    private Bet bet;
    private LocalDateTime betTime;
    private int points;

    public UserBetsOnGame(int gameId, int userId, Bet bet) {
        this.gameId = gameId;
        this.userId = userId;
        this.bet = bet;
        betTime = LocalDateTime.now();
        points = 0;
    }

    public void UpdateBet(Bet bet) {
        this.bet = bet;
        betTime = LocalDateTime.now();
    }

    public void calcPoints(Bet result) {
        if (result == bet) {
            // TODO find better scoring method
            points = 10;
        }
    }

    public int getGameId() {
        return gameId;
    }

    public int getUserId() {
        return userId;
    }

    public Bet getBet() {
        return bet;
    }

    public LocalDateTime getBetTime() {
        return betTime;
    }

    public int getPoints() {
        return points;
    }
}
