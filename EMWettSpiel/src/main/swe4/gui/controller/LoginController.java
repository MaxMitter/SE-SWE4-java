package swe4.gui.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import swe4.gui.Startup;

import java.io.IOException;

public class LoginController {

    public static Scene scene = null;
    public static Parent rootElement = null;

    public static void SetFxml(Parent node) {
        rootElement = node;
    }

    public static void LoadScene() {
        if (scene == null)
            scene = new Scene(rootElement);

        Startup.SetScene(scene);
    }

    @FXML
    protected void btnLoginClick(MouseEvent event) throws IOException {
        GameListController.LoadScene();
    }

    @FXML
    protected void btnRegisterClick(MouseEvent event) {

    }
}
