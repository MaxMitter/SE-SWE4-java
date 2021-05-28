package main.swe4;

import main.swe4.data.Entities.Game;
import main.swe4.data.Interfaces.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class Server implements main.swe4.data.Interfaces.Server {
    private static final int RMI_PORT = 1099;
    private static final Server instance = new Server();
    private Vector<Callback> clientList = null;

    private BettingService bettingService = null;
    private GameService gameService = null;
    private TeamService teamService = null;
    private UserService userService = null;

    public static Server GetInstance() {
        return instance;
    }

    private Server() {
        clientList = new Vector<>();
        bettingService = new BettingServiceImplementation();
        gameService = new GameServiceImplementation();
        teamService = new TeamServiceImplementation();
        userService = new UserServiceImplementation();
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(RMI_PORT);
            Server server = GetInstance();
            Naming.rebind("EuroBetServer", UnicastRemoteObject.exportObject(server, 0));
            System.out.println("Server running on port " + RMI_PORT);
        } catch (RemoteException | MalformedURLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void registerCallback(Callback client) throws RemoteException {
        System.out.println("Register client with id " + clientList.size());
        clientList.add(client);
    }

    @Override
    public void deleteGame(Game g) throws RemoteException {
        for (var c : clientList) {
            c.deleteGame(g);
        }
    }

    @Override
    public void updateGame(Game g) throws RemoteException {
        System.out.println("Updating " + clientList.size() + " clients");
        for (var c : clientList) {
            System.out.println("Updating client with id " + clientList.indexOf(c));
            c.updateGame(g);
        }
    }

    @Override
    public BettingService GetBettingService() throws RemoteException {
        return bettingService;
    }

    @Override
    public GameService GetGameService() throws RemoteException {
        return gameService;
    }

    @Override
    public TeamService GetTeamService() throws RemoteException {
        return teamService;
    }

    @Override
    public UserService GetUserService() throws RemoteException {
        return userService;
    }
}
