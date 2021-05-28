package main.swe4.data.Interfaces;

import main.swe4.data.Entities.Game;

import java.rmi.RemoteException;

public interface UpdateClientService {
    void deleteGame(Callback client, Game g) throws RemoteException;
    void updateGame(Callback client, Game g) throws RemoteException;
}
