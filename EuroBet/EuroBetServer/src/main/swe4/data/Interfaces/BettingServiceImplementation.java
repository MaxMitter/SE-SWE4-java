package main.swe4.data.Interfaces;

import main.swe4.data.Entities.Bet;
import main.swe4.data.Entities.Pair;
import main.swe4.data.Entities.UserBetsOnGame;
import main.swe4.data.Repository;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class BettingServiceImplementation implements BettingService {
    @Override
    public Collection<UserBetsOnGame> getBetsByGameId(int gameId) throws RemoteException {
        var list = Repository.Instance().GetAllBets();
        ArrayList<UserBetsOnGame> retList = new ArrayList<>();
        for (var bet : list) {
            if (bet.getGameId() == gameId)
                retList.add(bet);
        }

        return retList;
    }

    @Override
    public Collection<UserBetsOnGame> getBetsByUserId(int userId) throws RemoteException {
        var list = Repository.Instance().GetAllBets();
        ArrayList<UserBetsOnGame> retList = new ArrayList<>();
        for (var bet : list) {
            if (bet.getUserId() == userId)
                retList.add(bet);
        }

        return retList;
    }

    @Override
    public Collection<UserBetsOnGame> getBetsBeforeTime(LocalDateTime time) throws RemoteException {
        var list = Repository.Instance().GetAllBets();
        ArrayList<UserBetsOnGame> retList = new ArrayList<>();
        for (var bet : list) {
            if (bet.getBetTime().isBefore(time))
                retList.add(bet);
        }

        return retList;
    }

    @Override
    public Collection<UserBetsOnGame> getClosedBets() throws RemoteException {
        var list = Repository.Instance().GetAllBets();
        ArrayList<UserBetsOnGame> retList = new ArrayList<>();
        for (var bet : list) {
            if (bet.IsClosed())
                retList.add(bet);
        }

        return retList;
    }

    @Override
    public void addBet(int gameId, int userId, Bet bet) throws RemoteException {
        Repository.Instance().AddBet(userId, gameId, bet);
    }

    @Override
    public void updateBet(UserBetsOnGame bet) throws RemoteException {
        Repository.Instance().UpdateBet(bet.getUserId(), bet.getGameId(), bet.getBet());
    }

    @Override
    public void deleteBet(UserBetsOnGame bet) throws RemoteException {
        Repository.Instance().DeleteBet(bet);
    }

    @Override
    public int getBetPoints(int userId, int gameId) throws RemoteException {
        return Repository.Instance().GetBetPoints(userId, gameId);
    }

    @Override
    public void finalizeScores() throws RemoteException {
        Repository.Instance().RecalculateBets();
    }

    @Override
    public Collection<Pair> getAllScores() throws RemoteException {
        return Repository.Instance().GetAllPoints();
    }
}
