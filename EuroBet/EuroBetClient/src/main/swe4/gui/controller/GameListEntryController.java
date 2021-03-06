package main.swe4.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import main.swe4.Startup;
import main.swe4.data.Entities.Bet;
import main.swe4.data.Entities.Game;
import main.swe4.gui.services.ServiceController;

import java.io.*;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameListEntryController {
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
    public Label lbl_draw;

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
        boolean isOver = false;

        var bets = ServiceController.bettingServiceInstance().getBetsByGameId(g.getId());
        Bet currentGameBet = Bet.NONE;
        for (var b : bets) {
            if (b.getUserId() == Startup.GetCurrentUser().getId()) {
                currentGameBet = b.getBet();
            }
        }

        VBox box = (VBox) newElem.getChildrenUnmodifiable().get(0);

        var gameInfo = (HBox) box.getChildren().get(0);
        // Info label for Time
        var time = (Label) gameInfo.getChildren().get(0);
        time.setText(g.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + g.getTime().format(DateTimeFormatter.ISO_LOCAL_TIME));
        // Red Button if the Game is happening right now
        if (g.getTime().isBefore(LocalDateTime.now())) {
            if (g.getTime().plusMinutes(90).isAfter(LocalDateTime.now())) {
                isLive = true;
                var pointLive = (Circle) gameInfo.getChildren().get(1);
                pointLive.setVisible(true);
            } else {
                isOver = true;
            }
        }
        // Name label for the current Game
        var gameName = (Label) gameInfo.getChildren().get(2);
        gameName.setText(g.getName());
        // Label for the points the user got for this game
        var pointsLabel = (Label) gameInfo.getChildren().get(3);
        if (!isLive) {
            if (isOver) {
                pointsLabel.setText("Punkte: " + ServiceController.bettingServiceInstance().getBetPoints(Startup.GetCurrentUser().getId(), g.getId()));
            } else {
                pointsLabel.setText("");
            }
        } else {
            pointsLabel.setText("");
        }

        var team1 = (HBox) box.getChildren().get(1);
        // Team 1 Name label
        var t1Name = (Label) team1.getChildren().get(0);
        t1Name.setText(g.getT1().getName());
        if (currentGameBet == Bet.TEAM1) t1Name.setStyle("-fx-background-color: green;");
        // Team 1 score if game is live
        if (isLive || isOver) {
            var t1Score = (Label) team1.getChildren().get(2);
            t1Score.setText(Integer.toString(g.getScoreT1()));
        }

        var lblDraw = (Label) ((HBox) box.getChildren().get(2)).getChildren().get(0);
        if (isOver) {
            lblDraw.setVisible(false);
        } else {
            if (currentGameBet == Bet.DRAW) lblDraw.setStyle("-fx-background-color: green;");
        }

        var team2 = (HBox) box.getChildren().get(3);
        // Team 2 Name label
        var t2Name = (Label) team2.getChildren().get(0);
        t2Name.setText(g.getT2().getName());
        if (currentGameBet == Bet.TEAM2) t2Name.setStyle("-fx-background-color: green;");
        // Team 2 score if game is live
        if (isLive || isOver) {
            var t2Score = (Label) team2.getChildren().get(2);
            t2Score.setText(Integer.toString(g.getScoreT2()));
        }

        var lbl = (Label) newElem.getChildrenUnmodifiable().get(1);
        lbl.setText(Integer.toString(g.getId()));

        return newElem;
    }

    public static int GetGameId (Parent gameListEntry) {
        var lbl = (Label) gameListEntry.getChildrenUnmodifiable().get(1);
        return Integer.parseInt(lbl.getText());
    }

    private boolean canBetOnGame(int gameId) {
        Game g = null;

        try {
            g = ServiceController.gameServiceInstance().getGameById(gameId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if (g == null)
            return false;

        if (g.getTime().isAfter(LocalDateTime.now())) {
            return true;
        } else {
            return g.getTime().plusMinutes(90).isAfter(LocalDateTime.now());
        }
    }

    private void updateLabels(Bet bet) {
        if (canBetOnGame(Integer.parseInt(lbl_gameId.getText()))) {
            switch (bet) {
                case TEAM1:
                    if (lbl_team1.getStyle().contains("green")) {
                        lbl_team1.setStyle("-fx-background-color: none;");
                    } else {
                        lbl_team1.setStyle("-fx-background-color: green;");
                        lbl_team2.setStyle("-fx-background-color: none;");
                        lbl_draw.setStyle("-fx-background-color: none;");
                    }
                    break;
                case TEAM2:
                    if (lbl_team2.getStyle().contains("green")) {
                        lbl_team2.setStyle("-fx-background-color: none;");
                    } else {
                        lbl_team2.setStyle("-fx-background-color: green;");
                        lbl_team1.setStyle("-fx-background-color: none;");
                        lbl_draw.setStyle("-fx-background-color: none;");
                    }
                    break;
                case DRAW:
                    if (lbl_draw.getStyle().contains("green")) {
                        lbl_draw.setStyle("-fx-background-color: none;");
                    } else {
                        lbl_draw.setStyle("-fx-background-color: green;");
                        lbl_team1.setStyle("-fx-background-color: none;");
                        lbl_team2.setStyle("-fx-background-color: none;");
                    }
                    break;
            }

            UpdateBets();
        }
    }

    @FXML
    public void lblTeam1Clicked(MouseEvent event) {
        updateLabels(Bet.TEAM1);
    }

    @FXML
    public void lblTeam2Clicked(MouseEvent event) {
        updateLabels(Bet.TEAM2);
    }

    @FXML
    public void lblDrawClicked(MouseEvent mouseEvent) {
        updateLabels(Bet.DRAW);
    }

    public void UpdateBets() {
        Bet b = Bet.NONE;
        if (lbl_team1.getStyle().contains("green")) b = Bet.TEAM1;
        else if (lbl_team2.getStyle().contains("green")) b = Bet.TEAM2;
        else if (lbl_draw.getStyle().contains("green")) b = Bet.DRAW;
        try {
            ServiceController.bettingServiceInstance().addBet(Startup.GetCurrentUser().getId(), Integer.parseInt(lbl_gameId.getText()), b);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
