package main.swe4.data.Interfaces;

import main.swe4.data.Entities.Game;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Callback extends Remote {
    void deleteGame(Game g) throws RemoteException;
    void updateGame(Game g) throws RemoteException;
}
