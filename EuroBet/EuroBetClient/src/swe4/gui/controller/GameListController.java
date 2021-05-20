package swe4.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import swe4.Startup;
import swe4.data.Entities.Game;
import swe4.data.Entities.Pair;
import swe4.data.Repository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;

public class GameListController {
    public static Scene scene = null;
    public static Parent rootElement = null;
    public static ObservableList<Node> gameList = null;

    public ListView list_Games;
    public ListView<String> list_Highscore;

    public static void SetFxml(Parent node) {
        rootElement = node;
    }

    public static void LoadScene() {
        if (scene == null)
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
        Repository.Instance().FinalizeAllBets();
        var list = Repository.Instance().GetAllPoints();

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
        if (gameList == null) {
            gameList = FXCollections.observableArrayList();
        }

        var games = Repository.Instance().getAllGames();

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
}
