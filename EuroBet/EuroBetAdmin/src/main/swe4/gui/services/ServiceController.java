package main.swe4.gui.services;

import main.swe4.data.Interfaces.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ServiceController {
    private static Server server = null;

    private ServiceController() { }

    private static void GetServer() {
        if (server == null) {
            try {
                server = (Server) Naming.lookup("EuroBetServer");
            } catch (MalformedURLException | RemoteException | NotBoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static BettingService bettingServiceInstance() {
        GetServer();

        try {
            return server.GetBettingService();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GameService gameServiceInstance() {
        GetServer();

        try {
            return server.GetGameService();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TeamService teamServiceInstance() {
        GetServer();

        try {
            return server.GetTeamService();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UserService userServiceInstance() {
        GetServer();

        try {
            return server.GetUserService();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
