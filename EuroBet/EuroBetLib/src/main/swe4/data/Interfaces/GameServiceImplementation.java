package main.swe4.data.Interfaces;

import main.swe4.Server;
import main.swe4.data.Entities.Game;
import main.swe4.data.Entities.Team;
import main.swe4.data.Repository;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

public class GameServiceImplementation implements GameService {
    @Override
    public void createGame(Game game) throws RemoteException {
        Repository.Instance().AddGame(game);
    }

    @Override
    public void updateGame(Game game) throws RemoteException {
        System.out.println("Server got update notice.");
        Repository.Instance().UpdateGame(game);
        Server.GetInstance().updateGame(game);
    }

    @Override
    public void deleteGame(Game game) throws RemoteException {
        Repository.Instance().DeleteGame(game);
        Server.GetInstance().deleteGame(game);
    }

    @Override
    public Collection<Game> getAllGames() throws RemoteException {
        return Repository.Instance().getAllGames();
    }

    @Override
    public Collection<Game> getGamesByTeam(Team team) throws RemoteException {
        var list = Repository.Instance().getAllGames();
        ArrayList<Game> retList = new ArrayList<>();
        for (var g : list) {
            if (g.getT1().equals(team) || g.getT2().equals(team))
                retList.add(g);
        }

        return retList;
    }

    @Override
    public Game getGameById(int id) throws RemoteException {
        return Repository.Instance().GetGameById(id);
    }
}
