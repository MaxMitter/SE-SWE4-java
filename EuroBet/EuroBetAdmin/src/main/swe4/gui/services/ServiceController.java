package main.swe4.gui.services;

import main.swe4.data.Entities.User;
import main.swe4.data.Interfaces.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ServiceController {
    private static BettingService bettingService = null;
    private static GameService gameService = null;
    private static TeamService teamService = null;
    private static UserService userService = null;

    private ServiceController() { }

    public static BettingService bettingServiceInstance() {
        if (bettingService == null) {
            try{
                bettingService = (BettingService) Naming.lookup("BettingService");
            } catch (MalformedURLException | RemoteException | NotBoundException ex) {
                ex.printStackTrace();
            }
        }
        return bettingService;
    }

    public static GameService gameServiceInstance() {
        if (gameService == null) {
            try{
                gameService = (GameService) Naming.lookup("GameService");
            } catch (MalformedURLException | RemoteException | NotBoundException ex) {
                ex.printStackTrace();
            }
        }
        return gameService;
    }

    public static TeamService teamServiceInstance() {
        if (teamService == null) {
            try{
                teamService = (TeamService) Naming.lookup("TeamService");
            } catch (MalformedURLException | RemoteException | NotBoundException ex) {
                ex.printStackTrace();
            }
        }
        return teamService;
    }

    public static UserService userServiceInstance() {
        if (userService == null) {
            try{
                userService = (UserService) Naming.lookup("UserService");
            } catch (MalformedURLException | RemoteException | NotBoundException ex) {
                ex.printStackTrace();
            }
        }
        return userService;
    }
}
