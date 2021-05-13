package swe4.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import swe4.gui.Startup;
import swe4.gui.data.Entities.Bet;
import swe4.gui.data.Entities.Game;
import swe4.gui.data.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Lock;

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
    public Label lbl_gameId;
    public Label lbl_userPoints;

    public static void SetFxml(Parent node) {
        rootElement = node;
    }

    public static Parent GetElement() throws IOException {
        Parent newElem = rootElement;
        rootElement = FXMLLoader.load(Startup.gameListEntryUrl);
        return newElem;
    }

    public static Parent GetElement(Game g) throws IOException {
        Parent newElem = rootElement;
        rootElement = FXMLLoader.load(Startup.gameListEntryUrl);
        boolean isLive = false;

        VBox box = (VBox) newElem.getChildrenUnmodifiable().get(0);

        var gameInfo = (HBox) box.getChildren().get(0);
        // Info label for Time
        var time = (Label) gameInfo.getChildren().get(0);
        time.setText(g.getTime().format(DateTimeFormatter.ISO_LOCAL_TIME));
        // Red Button if the Game is happening right now
        if (g.getTime().isBefore(LocalDateTime.now()) &&
            g.getTime().plusMinutes(90).isAfter(LocalDateTime.now())) {
            isLive = true;
            var pointLive = (Circle) gameInfo.getChildren().get(1);
            pointLive.setVisible(true);
        }
        // Name label for the current Game
        var gameName = (Label) gameInfo.getChildren().get(2);
        gameName.setText(g.getName());
        // Label for the points the user got for this game
        var pointsLabel = (Label)gameInfo.getChildren().get(3);
        if (!isLive) {
            if (g.getTime().isBefore(LocalDateTime.now())) {
                pointsLabel.setText("Punkte: " + Repository.Instance.GetBetPoints(Startup.GetCurrentUser(), g.getId()));
            }
        } else {
            pointsLabel.setText("");
        }

        var team1 = (HBox) box.getChildren().get(1);
        // Team 1 Name label
        var t1Name = (Label)team1.getChildren().get(0);
        t1Name.setText(g.getT1().getName());
        // Team 1 score if game is live
        if (isLive) {
            var t1Score = (Label)team1.getChildren().get(2);
            t1Score.setText(Integer.toString(g.getScoreT1()));
        }
        var team2 = (HBox) box.getChildren().get(2);
        // Team 2 Name label
        var t2Name = (Label)team2.getChildren().get(0);
        t2Name.setText(g.getT2().getName());
        // Team 2 score if game is live
        if (isLive) {
            var t2Score = (Label)team2.getChildren().get(2);
            t2Score.setText(Integer.toString(g.getScoreT2()));
        }

        var lbl = (Label)rootElement.getChildrenUnmodifiable().get(1);
        lbl.setText(Integer.toString(g.getId()));

        return newElem;
    }

    @FXML
    public void lblTeam1Clicked(MouseEvent event) {
        if (crcl_live.isVisible()) {
            if (lbl_team1.getStyle().contains("green")) {
                lbl_team1.setStyle("-fx-background-color: none;");
            } else {
                lbl_team1.setStyle("-fx-background-color: green;");
            }
        }
        UpdateBets();
    }

    @FXML
    public void lblTeam2Clicked(MouseEvent event) {
        if (crcl_live.isVisible()) {
            if (lbl_team2.getStyle().contains("green")) {
                lbl_team2.setStyle("-fx-background-color: none;");
            } else {
                lbl_team2.setStyle("-fx-background-color: green;");
            }
        }
        UpdateBets();
    }

    public void UpdateBets() {
        int bet = 0;
        if (lbl_team1.getStyle().contains("green")) bet = 1;
        if (lbl_team2.getStyle().contains("green")) bet += 2;

        Bet b;
        switch (bet) {
            case 1:
                b = Bet.TEAM1;
                break;
            case 2:
                b = Bet.TEAM2;
                break;
            case 3:
                b = Bet.DRAW;
                break;
            default:
                b = Bet.NONE;
                break;
        }

        Repository.Instance.AddBet(Startup.GetCurrentUser(), Integer.parseInt(lbl_gameId.getText()), b);
    }
}
