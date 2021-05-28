package main.swe4.gui.services;

import main.swe4.data.Interfaces.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ServiceController {
    private static Server server = null;

    private ServiceController() {
        try {
            server = (Server) Naming.lookup("EuroBetServer");
        } catch (MalformedURLException | RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }
    }

    public static BettingService bettingServiceInstance() {
        try {
            return server.GetBettingService();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GameService gameServiceInstance() {
        try {
            return server.GetGameService();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TeamService teamServiceInstance() {
        try {
            return server.GetTeamService();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UserService userServiceInstance() {
        try {
            return server.GetUserService();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void registerCallback(Callback client) {
        try {
            server.registerCallback(client);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
