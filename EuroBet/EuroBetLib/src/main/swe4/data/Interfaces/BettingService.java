package main.swe4.data.Interfaces;

import main.swe4.data.Entities.Bet;
import main.swe4.data.Entities.Pair;
import main.swe4.data.Entities.UserBetsOnGame;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Collection;

public interface BettingService extends Remote, Serializable {
    Collection<UserBetsOnGame> getBetsByGameId(int gameId) throws RemoteException;
    Collection<UserBetsOnGame> getBetsByUserId(int userId) throws RemoteException;
    Collection<UserBetsOnGame> getBetsBeforeTime(LocalDateTime time) throws RemoteException;
    Collection<UserBetsOnGame> getClosedBets() throws RemoteException;
    int getBetPoints(int userId, int gameId) throws RemoteException;
    void addBet(int gameId, int userId, Bet bet) throws RemoteException;
    void updateBet(UserBetsOnGame bet) throws RemoteException;
    void deleteBet(UserBetsOnGame bet) throws RemoteException;
    void finalizeScores() throws RemoteException;
    Collection<Pair> getAllScores() throws RemoteException;
}
