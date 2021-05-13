package swe4.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import swe4.gui.Startup;
import swe4.gui.data.Entities.Game;
import swe4.gui.data.Repository;

import java.io.IOException;
import java.time.LocalDateTime;

public class GameListController {
    public static Scene scene = null;
    public static Parent rootElement = null;
    public static ObservableList<Node> gameList = null;

    public static void SetFxml(Parent node) {
        rootElement = node;
    }

    public static void LoadScene() {
        if (scene == null) {
            try {
                var contr = new GameListController();
                contr.LoadGameList(rootElement);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            scene = new Scene(rootElement);
        }
        Startup.SetScene(scene);
    }

    private void LoadGameList(Parent fxml) throws IOException {
        var root = (AnchorPane) fxml;
        var list = (ListView<Node>) root.getChildren().get(1);

        if (gameList == null) {
            gameList = FXCollections.observableArrayList();
        }

        var games = Repository.Instance.getAllGames();

        int earliestLiveGame = 0;
        for (Game g : games) {
            var element = GameListEntryController.GetElement(g);
            if (g.getTime().isBefore(LocalDateTime.now()) &&
                    g.getTime().plusMinutes(90).isBefore(LocalDateTime.now())) {
                earliestLiveGame++;
            }
            gameList.add(element);
        }

        list.setItems(gameList);
        list.scrollTo(earliestLiveGame);
    }

    @FXML
    public void btnLogoutClick() {
        Startup.Logout();
        Startup.SetScene(LoginController.scene);
    }
}
