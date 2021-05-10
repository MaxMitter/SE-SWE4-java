package swe4.gui.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import swe4.gui.Startup;

import java.io.*;

public class GameListEntryController {
    public static Scene scene = null;
    public static Parent rootElement = null;

    //FXML Properties
    public Label lbl_gameTime;
    public Circle crcl_live;
    public Label lbl_gameName;
    public Label lbl_team1;
    public Label lbl_team2;
    public Label lbl_team1Score;
    public Label lbl_team2Score;

    public static void SetFxml(Parent node) {
        rootElement = node;
    }

    public static Parent GetElement() throws IOException {
        Parent newElem = rootElement;
        rootElement = FXMLLoader.load(Startup.gameListEntryUrl);
        return newElem;
    }
}
