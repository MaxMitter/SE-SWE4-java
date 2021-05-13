package swe4.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import swe4.gui.controller.AdminViewController;
import swe4.gui.controller.GameListController;
import swe4.gui.controller.GameListEntryController;
import swe4.gui.controller.LoginController;

import java.io.IOException;
import java.net.URL;

import static javafx.application.Application.launch;

public class Startup extends Application {

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Stage stage = null;
    public static URL gameListEntryUrl = null;
    private static int currentUser = -1;

    @Override
    public void start(Stage startupStage) throws Exception {
        LoadFxmlFiles();
        Startup.stage = startupStage;
        Startup.stage.setTitle("EURO-BET");
        LoginController.LoadScene();
        Startup.stage.show();
    }

    public static void Login(int userId) {
        currentUser = userId;
    }

    public static void Logout() {
        currentUser = -1;
    }

    public static int GetCurrentUser() {
        return currentUser;
    }

    private void LoadFxmlFiles() throws IOException {
        Parent loginFxml = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        LoginController.SetFxml(loginFxml);
        Parent gameListFxml = FXMLLoader.load(getClass().getResource("fxml/gameList.fxml"));
        GameListController.SetFxml(gameListFxml);
        Parent adminViewFxml = FXMLLoader.load(getClass().getResource("fxml/adminView.fxml"));
        AdminViewController.SetFxml(adminViewFxml);

        gameListEntryUrl = getClass().getResource("fxml/gameListEntry.fxml");
        Parent gameListEntryFxml = FXMLLoader.load(gameListEntryUrl);
        GameListEntryController.SetFxml(gameListEntryFxml);
    }

    public static void SetScene (Scene newScene) {
        Startup.stage.setScene(newScene);
    }
}
