package swe4.gui.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import swe4.gui.Exceptions.UserNotFoundException;
import swe4.gui.Startup;
import swe4.gui.data.Repository;

import java.io.IOException;

public class LoginController {

    public static Scene scene = null;
    public static Parent rootElement = null;
    public Button btnLogin;
    public Button btnBack;
    public PasswordField firstPassword;
    public PasswordField secondPassword;
    public TextField txt_UserName;
    public Label lbl_Error;


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
        lbl_Error.setText("");
        if (btnLogin.isVisible()) {
            try {
                boolean loginSuccess = Repository.Instance.isValidLogin(txt_UserName.getText(), firstPassword.getText());
                if (loginSuccess)
                    GameListController.LoadScene();
                else {
                    lbl_Error.setVisible(true);
                    lbl_Error.setText("Invalid Password");
                }
            } catch (UserNotFoundException ex) {
                lbl_Error.setVisible(true);
                lbl_Error.setText("User " + txt_UserName.getText() + " konnte nicht gefunden werden.");
            }
        }
    }

    @FXML
    protected void btnRegisterClick(MouseEvent event) {
        lbl_Error.setText("");
        if (btnLogin.isVisible()) {
            btnLogin.setVisible(false);
            btnBack.setVisible(true);
            secondPassword.setVisible(true);
            secondPassword.setStyle("-fx-border-width: 0px; -fx-border-color: black;");
        } else {
            if (!firstPassword.getText().isEmpty() && firstPassword.getText().equals(secondPassword.getText())) {
                GameListController.LoadScene();
            } else {
                secondPassword.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                lbl_Error.setText("Passwörter müssen ident sein.");
            }
        }
    }

    @FXML
    public void btnBackClick(MouseEvent mouseEvent) {
        if (!btnLogin.isVisible()) {
            btnLogin.setVisible(true);
            btnBack.setVisible(false);
            secondPassword.setVisible(false);
            lbl_Error.setText("");
        }
    }
}