package swe4.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import swe4.gui.Startup;

import java.io.IOException;

public class GameListController {
    public static Scene scene = null;
    public static Parent rootElement = null;
    public static ObservableList<Node> gameList = null;
    public ListView gameListView;

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

        for (int i = 0; i < 10; i++) {
            gameList.add(GameListEntryController.GetElement());
        }

        list.setItems(gameList);
        list.scrollTo(5);
    }
}
