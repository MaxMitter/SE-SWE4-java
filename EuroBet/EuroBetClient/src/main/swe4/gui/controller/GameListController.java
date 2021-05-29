package main.swe4.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import main.swe4.Startup;
import main.swe4.data.Entities.Game;
import main.swe4.data.Entities.Pair;
import main.swe4.gui.services.ServiceController;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GameListController {
    public static Scene scene = null;
    public static Parent rootElement = null;
    public static ObservableList<Parent> gameList = null;

    public ListView list_Games;
    public ListView<String> list_Highscore;

    public static void SetFxml(Parent node) {
        rootElement = node;
    }

    public static void LoadScene() {
        scene = new Scene(rootElement);

        Startup.SetScene(scene);
    }

    @FXML
    public void initialize() {
        try {
            InitGameList();
            InitHighScoreList();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void InitHighScoreList() {
        try {
            ServiceController.bettingServiceInstance().finalizeScores();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Collection<Pair> l = null;
        try {
            l = ServiceController.bettingServiceInstance().getAllScores();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        var list = FXCollections.observableArrayList(l);

        list.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o1.getScore(), o2.getScore());
            }
        });
        Collections.reverse(list);

        ObservableList<String> textList = FXCollections.observableArrayList();
        for (Pair p : list) {
            textList.add(p.getText());
        }

        list_Highscore.setItems(textList);
    }

    private void InitGameList() throws IOException {
        gameList = FXCollections.observableArrayList();

        var games = ServiceController.gameServiceInstance().getAllGames();

        int earliestLiveGame = 0;
        for (Game g : games) {
            var element = GameListEntryController.GetElement(g);
            if (g.getTime().isBefore(LocalDateTime.now()) &&
                    g.getTime().plusMinutes(90).isBefore(LocalDateTime.now())) {
                earliestLiveGame++;
            }
            gameList.add(element);
        }

        list_Games.setItems(gameList);
        list_Games.scrollTo(earliestLiveGame);
    }

    @FXML
    public void btnLogoutClick() {
        Startup.Logout();
        Startup.SetScene(LoginController.scene);
    }

    public void RemoveGame(Game g) {
        gameList.removeIf(p -> g.getId() == GameListEntryController.GetGameId(p));
    }

    public void UpdateGame(Game g) throws RemoteException {
        System.out.println("Updating game...");
        int current = 0;
        var currElement = gameList.get(current);

        while (currElement != null && g.getId() != GameListEntryController.GetGameId(currElement)) {
            current++;
            currElement = gameList.get(current);
        }

        if (currElement != null) {
            System.out.println("Found game!");
            try {
                gameList.set(current, GameListEntryController.GetElement(g));
                System.out.println("Updated game!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            list_Games.refresh();
        } else {
            System.out.println("Game not found in list.");
        }
    }

    public void btnRefreshClick(MouseEvent mouseEvent) {
        try {
            InitGameList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
