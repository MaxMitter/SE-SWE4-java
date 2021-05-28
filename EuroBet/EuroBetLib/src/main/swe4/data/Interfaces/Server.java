package main.swe4.data.Interfaces;

import main.swe4.data.Entities.Game;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    void registerCallback(Callback client) throws RemoteException;
    void deleteGame(Game g) throws RemoteException;
    void updateGame(Game g) throws RemoteException;
    BettingService GetBettingService() throws RemoteException;
    GameService GetGameService() throws RemoteException;
    TeamService GetTeamService() throws RemoteException;
    UserService GetUserService() throws RemoteException;
}
