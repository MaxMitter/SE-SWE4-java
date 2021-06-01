package main.swe4.data.Interfaces;

import main.swe4.data.Entities.Game;
import main.swe4.data.Entities.Team;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface GameService extends Remote, Serializable {
    void createGame(Game game) throws RemoteException;
    void updateGame(Game game) throws RemoteException;
    void deleteGame(Game game) throws RemoteException;
    Collection<Game> getAllGames() throws RemoteException;
    Collection<Game> getGamesByTeam(Team team) throws RemoteException;
    Game getGameById(int id) throws RemoteException;
}
