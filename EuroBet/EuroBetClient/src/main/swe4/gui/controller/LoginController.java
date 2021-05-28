package main.swe4.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import main.swe4.Exceptions.UserNotFoundException;
import main.swe4.Startup;
import main.swe4.data.Repository;
import main.swe4.gui.services.ServiceController;

import java.io.IOException;
import java.rmi.RemoteException;

public class LoginController {

    public static Scene scene = null;
    public static Parent rootElement = null;
    public Button btnLogin;
    public PasswordField password;
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
                var login = ServiceController.userServiceInstance().login(txt_UserName.getText(), password.getText());
                if (login != null) {
                    Startup.Login(login);
                    Startup.LoadGameListEntryFxml();
                    Startup.LoadGameListFxml();

                    GameListController.LoadScene();
                } else {
                    lbl_Error.setVisible(true);
                    lbl_Error.setText("User oder Passwort stimmen nicht überein");
                }
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }
}
