package main.swe4.data.Entities;

import main.swe4.data.Repository;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class UserBetsOnGame implements Serializable {
    private final int gameId;
    private final int userId;
    private Bet bet;
    private LocalDateTime betTime;
    private int points;

    public UserBetsOnGame(int gameId, int userId, Bet bet) {
        this(gameId, userId, bet, LocalDateTime.now());
    }

    public UserBetsOnGame(int gameId, int userId, Bet bet, LocalDateTime time) {
        this.gameId = gameId;
        this.userId = userId;
        this.bet = bet;
        betTime = time;
        points = 0;
    }

    public void UpdateBet(Bet bet) {
        this.bet = bet;
        betTime = LocalDateTime.now();
    }

    public void calcPoints(Bet result) {
        LocalDateTime gameStart = Repository.Instance().GetGameById(gameId).getTime();
        LocalDateTime betEnd = gameStart.plusMinutes(80).plusMinutes(15);
        LocalDateTime gameEnd = gameStart.plusMinutes(90).plusMinutes(15);

        if (result == bet) {
            if (betTime.isBefore(betEnd)) {
                var t = ChronoUnit.MINUTES.between(gameStart, betTime);
                if (betTime.isBefore(gameStart))
                    points = 10;
                else
                    points = 10 - Math.floorDiv((int)t, 10);
            }
        } else {
            points = 0;
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

    public boolean IsClosed() {
        var gameTime = Repository.Instance().GetGameById(gameId).getTime();
        var betEndTime = gameTime.plusMinutes(80).plusMinutes(15);

        return betTime.isBefore(betEndTime);
    }
}
