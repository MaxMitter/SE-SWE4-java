package main.swe4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.swe4.data.Entities.User;
import main.swe4.gui.controller.GameListController;
import main.swe4.gui.controller.GameListEntryController;
import main.swe4.gui.controller.LoginController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

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
        var path = new File("src/main/swe4/gui/fxml/login.fxml").toURI().toURL();
        Parent loginFxml = FXMLLoader.load(path);
        LoginController.SetFxml(loginFxml);
    }

    public static void LoadGameListEntryFxml() throws IOException {
        gameListEntryUrl = new File("src/main/swe4/gui/fxml/gameListEntry.fxml").toURI().toURL();
        System.out.println(gameListEntryUrl);
        Parent gameListEntryFxml = FXMLLoader.load(gameListEntryUrl);
        GameListEntryController.SetFxml(gameListEntryFxml);
    }

    public static void LoadGameListFxml() throws IOException {
        var path = new File("src/main/swe4/gui/fxml/gameList.fxml").toURI().toURL();
        System.out.println(path);
        Parent gameListFxml = FXMLLoader.load(path);
        GameListController.SetFxml(gameListFxml);
    }

    public static void SetScene(Scene newScene) {
        Startup.stage.setScene(newScene);
    }
}
