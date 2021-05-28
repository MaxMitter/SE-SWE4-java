package main.swe4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.swe4.data.Entities.Game;
import main.swe4.data.Entities.User;
import main.swe4.gui.controller.GameListController;
import main.swe4.gui.controller.GameListEntryController;
import main.swe4.gui.controller.LoginController;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;

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
    private static User currentUser = null;

    @Override
    public void start(Stage startupStage) throws Exception {
        LoadLoginFxml();
        Startup.stage = startupStage;
        Startup.stage.setTitle("EURO-BET");
        LoginController.LoadScene();
        Startup.stage.show();
    }

    public static void Login(User user) {
        currentUser = user;
    }

    public static void Logout() {
        currentUser = null;
    }

    public static User GetCurrentUser() {
        return currentUser;
    }

    private void LoadLoginFxml() throws IOException {
        Parent loginFxml = FXMLLoader.load(getClass().getResource("gui/fxml/login.fxml"));
        LoginController.SetFxml(loginFxml);
    }

    public static void LoadGameListEntryFxml() throws IOException {
        gameListEntryUrl = Startup.class.getResource("gui/fxml/gameListEntry.fxml");
        Parent gameListEntryFxml = FXMLLoader.load(gameListEntryUrl);
        GameListEntryController.SetFxml(gameListEntryFxml);
    }

    public static void LoadGameListFxml() throws IOException {
        Parent gameListFxml = FXMLLoader.load(Startup.class.getResource("gui/fxml/gameList.fxml"));
        GameListController.SetFxml(gameListFxml);
    }

    public static void SetScene(Scene newScene) {
        Startup.stage.setScene(newScene);
    }
}
