package main.swe4.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.HBox;
import javafx.util.*;
import javafx.util.converter.*;
import main.swe4.Startup;
import main.swe4.data.Entities.*;
import main.swe4.gui.services.ServiceController;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class AdminViewController {

    private static class TeamStringConverter extends StringConverter {
        @Override
        public String toString(Object o) {
            var t = (Team) o;
            return t.toString();
        }

        @Override
        public Object fromString(String s) {
            throw new UnsupportedOperationException();
        }
    }

    public static Scene scene = null;
    public static Parent rootElement = null;

    public TabPane tabPane;

    public HBox box_AddGame;
    public HBox box_AddUser;

    public TextField txt_GameName;
    public TextField txt_GameTime;
    public ComboBox<Team> cbox_T1;
    public ComboBox<Team> cbox_T2;

    public TextField txt_UserName;
    public TextField txt_FirstName;
    public TextField txt_LastName;
    public PasswordField txt_UserPassword;

    public TableView<Team> tbl_Teams;
    public TableView<Game> tbl_Games;
    public TableView<User> tbl_Users;

    private ObservableList<Team> list_Teams;
    private ObservableList<Game> list_Games;
    private ObservableList<User> list_Users;

    public TableColumn<Game, Integer> col_Games_id;
    public TableColumn<Game, String> col_Games_name;
    public TableColumn<Game, Team> col_Games_t1;
    public TableColumn<Game, Team> col_Games_t2;
    public TableColumn<Game, LocalDateTime> col_Games_time;
    public TableColumn<Game, Integer> col_Games_scoreT1;
    public TableColumn<Game, Integer> col_Games_scoreT2;
    public TableColumn<Game, Void> col_Games_delete;

    public TableColumn<Team, Integer> col_Teams_id;
    public TableColumn<Team, String> col_Teams_name;

    public TableColumn<User, Integer> col_Users_id;
    public TableColumn<User, String> col_Users_fName;
    public TableColumn<User, String> col_Users_lName;
    public TableColumn<User, String> col_Users_name;
    public TableColumn<User, String> col_Users_password;

    public static void SetFxml(Parent node) {
        rootElement = node;
    }

    public static void LoadScene() {
        if (scene == null)
            scene = new Scene(rootElement);

        Startup.SetScene(scene);
    }

    @FXML
    public void initialize() {
        initTeams();
        initGames();
        initUsers();

        tabPane.getSelectionModel().selectedItemProperty().addListener((observableValue, tab, t1) -> {
            switch (tab.getText()) {
                case "Games":
                    box_AddGame.setVisible(false);
                    break;
                case "Users":
                    box_AddUser.setVisible(false);
                    break;
            }

            switch (t1.getText()) {
                case "Games":
                    box_AddGame.setVisible(true);
                    break;
                case "Users":
                    box_AddUser.setVisible(true);
                    break;
            }
        });
        box_AddGame.setVisible(true);
        cbox_T1.setItems(list_Teams);
        cbox_T2.setItems(list_Teams);
    }

    private void initGames() {
        list_Games = FXCollections.observableArrayList();
        try {
            list_Games.addAll(ServiceController.gameServiceInstance().getAllGames());
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }

        col_Games_id.setCellValueFactory(new PropertyValueFactory<>("id"));

        col_Games_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_Games_name.setCellFactory(TextFieldTableCell.forTableColumn());
        col_Games_name.setOnEditCommit(event -> {
            final var value = event.getNewValue().isEmpty() ? event.getOldValue() : event.getNewValue();
            var g = (Game) event.getTableView().getItems().get(event.getTablePosition().getRow());
            g.setName(value);
            UpdateGame(g);
            tbl_Games.refresh();
        });

        col_Games_t1.setCellValueFactory(new PropertyValueFactory<>("t1"));
        col_Games_t1.setCellFactory(ComboBoxTableCell.forTableColumn(new TeamStringConverter(), list_Teams));
        col_Games_t1.setOnEditCommit(event -> {
            final var value = event.getNewValue() == null ? event.getOldValue() : event.getNewValue();
            var g = (Game) event.getTableView().getItems().get(event.getTablePosition().getRow());
            g.setT1(value);
            UpdateGame(g);
            tbl_Games.refresh();
        });

        col_Games_t2.setCellValueFactory(new PropertyValueFactory<>("t2"));
        col_Games_t2.setCellFactory(ComboBoxTableCell.forTableColumn(new TeamStringConverter(), list_Teams));
        col_Games_t2.setOnEditCommit(event -> {
            final var value = event.getNewValue() == null ? event.getOldValue() : event.getNewValue();
            var g = (Game) event.getTableView().getItems().get(event.getTablePosition().getRow());
            g.setT2(value);
            UpdateGame(g);
            tbl_Games.refresh();
        });

        col_Games_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_Games_time.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter()));
        col_Games_time.setOnEditCommit(event -> {
            final var value = event.getNewValue() == null ? event.getOldValue() : event.getNewValue();
            var g = (Game) event.getTableView().getItems().get(event.getTablePosition().getRow());
            g.setTime(value);
            UpdateGame(g);
            tbl_Games.refresh();
        });

        col_Games_scoreT1.setCellValueFactory(new PropertyValueFactory<>("scoreT1"));
        col_Games_scoreT1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col_Games_scoreT1.setOnEditCommit(event -> {
            final var value = event.getNewValue() == null ? event.getOldValue() : event.getNewValue();
            var g = (Game) event.getTableView().getItems().get(event.getTablePosition().getRow());
            g.setScoreT1(value);
            UpdateGame(g);
            tbl_Games.refresh();
        });

        col_Games_scoreT2.setCellValueFactory(new PropertyValueFactory<>("scoreT2"));
        col_Games_scoreT2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col_Games_scoreT2.setOnEditCommit(event -> {
            final var value = event.getNewValue() == null ? event.getOldValue() : event.getNewValue();
            var g = (Game) event.getTableView().getItems().get(event.getTablePosition().getRow());
            g.setScoreT2(value);
            UpdateGame(g);
            tbl_Games.refresh();
        });

        col_Games_delete.setCellFactory(new Callback<TableColumn<Game, Void>, TableCell<Game, Void>>() {
            @Override
            public TableCell<Game, Void> call(TableColumn<Game, Void> gameVoidTableColumn) {
                final TableCell<Game, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Löschen");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Game g = getTableView().getItems().get(getIndex());
                            DeleteGame(g);
                            list_Games.remove(g);
                        });
                    }

                    @Override
                    protected void updateItem(Void unused, boolean empty) {
                        super.updateItem(unused, empty);
                        if (empty)
                            setGraphic(null);
                        else
                            setGraphic(btn);
                    }
                };
                return cell;
            }
        });

        tbl_Games.setItems(list_Games);
    }

    private void DeleteGame(Game g) {
        try {
            ServiceController.gameServiceInstance().deleteGame(g);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void UpdateGame(Game g) {
        try {
            ServiceController.gameServiceInstance().updateGame(g);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void initTeams() {
        list_Teams = FXCollections.observableArrayList();
        try {
            list_Teams.addAll(ServiceController.teamServiceInstance().getAllTeams());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        col_Teams_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Teams_name.setCellValueFactory(new PropertyValueFactory<>("name"));

        tbl_Teams.setItems(list_Teams);
    }

    private void initUsers() {
        list_Users = FXCollections.observableArrayList();
        try {
            list_Users.addAll(ServiceController.userServiceInstance().getAllUsers());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        col_Users_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Users_fName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_Users_lName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_Users_name.setCellValueFactory(new PropertyValueFactory<>("userName"));
        col_Users_password.setCellValueFactory(new PropertyValueFactory<>("password"));

        tbl_Users.setItems(list_Users);
    }

    @FXML
    public void btnCreateGameClicked() {
        txt_GameName.setStyle("-fx-border-color: none;");
        txt_GameTime.setStyle("-fx-border-color: none;");
        cbox_T1.setStyle("-fx-border-color: none;");
        cbox_T2.setStyle("-fx-border-color: none;");

        boolean isValid = true;

        if (txt_GameName.getText().isEmpty()) {
            txt_GameName.setStyle("-fx-border-color: red;");
            isValid = false;
        }
        if (txt_GameTime.getText().isEmpty()) {
            txt_GameTime.setStyle("-fx-border-color: red;");
            isValid = false;
        }
        if (cbox_T1.getValue() == null) {
            cbox_T1.setStyle("-fx-border-color: red;");
            isValid = false;
        }
        if (cbox_T2.getValue() == null) {
            cbox_T2.setStyle("-fx-border-color: red;");
            isValid = false;
        }

        if (isValid) {
            try {
                var g = new Game(1, txt_GameName.getText(), cbox_T1.getValue(), cbox_T2.getValue(), LocalDateTime.parse(txt_GameTime.getText()));
                ServiceController.gameServiceInstance().createGame(g);
                list_Games.add(g);
            } catch (DateTimeParseException e) {
                txt_GameTime.setStyle("-fx-border-color: red;");
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    public void btnCreateUserClicked() {
        txt_UserName.setStyle("-fx-border-color: none;");
        txt_UserPassword.setStyle("-fx-border-color: none;");
        txt_FirstName.setStyle("-fx-border-color: none;");
        txt_LastName.setStyle("-fx-border-color: none;");

        boolean isValid = true;

        if (txt_UserName.getText().isEmpty()) {
            txt_UserName.setStyle("-fx-border-color: red;");
            isValid = false;
        }
        if (txt_UserPassword.getText().isEmpty()) {
            txt_UserPassword.setStyle("-fx-border-color: red;");
            isValid = false;
        }
        if (txt_FirstName.getText().isEmpty()) {
            txt_FirstName.setStyle("-fx-border-color: red;");
            isValid = false;
        }
        if (txt_LastName.getText().isEmpty()) {
            txt_LastName.setStyle("-fx-border-color: red;");
            isValid = false;
        }

        if (isValid) {
            var u = new User(1, txt_FirstName.getText(), txt_LastName.getText(), txt_UserName.getText(), txt_UserPassword.getText());
            list_Users.add(u);

            try {
                ServiceController.userServiceInstance().createUser(u);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
