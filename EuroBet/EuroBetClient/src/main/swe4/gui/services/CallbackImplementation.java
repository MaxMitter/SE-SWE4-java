package main.swe4.gui.services;

import main.swe4.data.Entities.Game;
import main.swe4.data.Interfaces.Callback;
import main.swe4.gui.controller.GameListController;

import java.rmi.RemoteException;

public class CallbackImplementation implements Callback {

    private GameListController controller = null;

    public CallbackImplementation(GameListController contr) {
        controller = contr;
    }

    @Override
    public void deleteGame(Game g) throws RemoteException {
        controller.RemoveGame(g);
    }

    @Override
    public void updateGame(Game g) throws RemoteException {
        System.out.println("Client received update notice");
        controller.UpdateGame(g);
    }
}
