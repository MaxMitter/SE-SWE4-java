package main.swe4.data.Interfaces;

import main.swe4.data.Entities.Game;

import java.rmi.RemoteException;

public class UpdateClientImplementation implements UpdateClientService {
    @Override
    public void deleteGame(Callback client, Game g) throws RemoteException {
        new Thread(() -> {
            try {
                client.deleteGame(g);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    @Override
    public void updateGame(Callback client, Game g) throws RemoteException {
        new Thread(() -> {
            try {
                client.updateGame(g);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}
