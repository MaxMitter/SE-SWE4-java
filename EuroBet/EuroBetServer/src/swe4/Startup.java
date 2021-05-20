package swe4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import swe4.gui.controller.AdminViewController;

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
        AdminViewController.LoadScene();
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
        Parent adminViewFxml = FXMLLoader.load(getClass().getResource("gui/fxml/adminView.fxml"));
        AdminViewController.SetFxml(adminViewFxml);
    }

    public static void SetScene(Scene newScene) {
        Startup.stage.setScene(newScene);
    }
}
