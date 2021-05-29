package main.swe4;

import main.swe4.data.Entities.Game;
import main.swe4.data.Interfaces.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    private static final int RMI_PORT = 1099;

    public static void main(String[] args) {
        BettingService bettingService = new BettingServiceImplementation();
        GameService gameService = new GameServiceImplementation();
        TeamService teamService = new TeamServiceImplementation();
        UserService userService = new UserServiceImplementation();

        try {
            LocateRegistry.createRegistry(RMI_PORT);
            Naming.rebind("BettingService", UnicastRemoteObject.exportObject(bettingService, 0));
            Naming.rebind("GameService", UnicastRemoteObject.exportObject(gameService, 0));
            Naming.rebind("TeamService", UnicastRemoteObject.exportObject(teamService, 0));
            Naming.rebind("UserService", UnicastRemoteObject.exportObject(userService, 0));
            System.out.println("Server running...");
        } catch (RemoteException | MalformedURLException ex) {
            ex.printStackTrace();
        }
    }
}
