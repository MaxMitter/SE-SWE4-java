package swe4.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Startup extends Application {
    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        Scene scene = new Scene(root, 300, 250);
        stage.setTitle("EURO-BET");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    protected void handleSubmitButtonAction(MouseEvent event) {
        System.out.println("pressed");
    }
}
