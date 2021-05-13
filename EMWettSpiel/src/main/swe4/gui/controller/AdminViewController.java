package swe4.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import swe4.gui.Startup;
import swe4.gui.data.Entities.*;
import swe4.gui.data.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class AdminViewController {

    private static class TeamStringConverter extends StringConverter {
        @Override
        public String toString(Object o) {
            var t = (Team)o;
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
    public HBox box_AddTeam;
    public HBox box_AddUser;

    public TextField txt_GameName;
    public TextField txt_GameTime;
    public ComboBox<Team> cbox_T1;
    public ComboBox<Team> cbox_T2;

    public TextField txt_TeamName;

    public TextField txt_UserName;
    public PasswordField txt_UserPassword;
    public ComboBox<Role> cbox_UserRole;

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

    public TableColumn<Team, Integer> col_Teams_id;
    public TableColumn<Team, String> col_Teams_name;

    public TableColumn<User, Integer> col_Users_id;
    public TableColumn<User, String> col_Users_name;
    public TableColumn<User, String> col_Users_password;
    public TableColumn<User, Role> col_Users_role;

    public static void SetFxml(Parent node) {
        rootElement = node;
    }

    public static void LoadScene() {
        if (scene == null) {
            scene = new Scene(rootElement);
        }
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
                case "Teams":
                    box_AddTeam.setVisible(false);
                    break;
                case "Users":
                    box_AddUser.setVisible(false);
                    break;
            }

            switch (t1.getText()) {
                case "Games":
                    box_AddGame.setVisible(true);
                    break;
                case "Teams":
                    box_AddTeam.setVisible(true);
                    break;
                case "Users":
                    box_AddUser.setVisible(true);
                    break;
            }
        });
        box_AddGame.setVisible(true);
        cbox_T1.setItems(list_Teams);
        cbox_T2.setItems(list_Teams);
        cbox_UserRole.setItems(Repository.Instance.getAllRoles());
    }

    private void initGames() {
        list_Games = FXCollections.observableArrayList();
        list_Games.addAll(Repository.Instance.getAllGames());

        col_Games_id.setCellValueFactory(new PropertyValueFactory<>("id"));

        col_Games_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_Games_name.setCellFactory(TextFieldTableCell.forTableColumn());
        col_Games_name.setOnEditCommit(event -> {
            final var value = event.getNewValue().isEmpty() ? event.getOldValue() : event.getNewValue();
            var x = (Game) event.getTableView().getItems().get(event.getTablePosition().getRow());
            x.setName(value);
            tbl_Games.refresh();
        });

        col_Games_t1.setCellValueFactory(new PropertyValueFactory<>("t1"));
        col_Games_t1.setCellFactory(ComboBoxTableCell.forTableColumn(new TeamStringConverter(), list_Teams));
        col_Games_t2.setCellValueFactory(new PropertyValueFactory<>("t2"));
        col_Games_t2.setCellFactory(ComboBoxTableCell.forTableColumn(new TeamStringConverter(), list_Teams));
        col_Games_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_Games_scoreT1.setCellValueFactory(new PropertyValueFactory<>("scoreT1"));
        col_Games_scoreT1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col_Games_scoreT1.setOnEditCommit(event -> {
            final var value = event.getNewValue() == null ? event.getOldValue() : event.getNewValue();
            var x = (Game) event.getTableView().getItems().get(event.getTablePosition().getRow());
            x.setScoreT1(value);
            tbl_Games.refresh();
        });
        col_Games_scoreT2.setCellValueFactory(new PropertyValueFactory<>("scoreT2"));
        col_Games_scoreT2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col_Games_scoreT2.setOnEditCommit(event -> {
            final var value = event.getNewValue() == null ? event.getOldValue() : event.getNewValue();
            var x = (Game) event.getTableView().getItems().get(event.getTablePosition().getRow());
            x.setScoreT2(value);
            tbl_Games.refresh();
        });
        tbl_Games.setItems(list_Games);
    }

    private void initTeams() {
        list_Teams = FXCollections.observableArrayList();
        list_Teams.addAll(Repository.Instance.getAllTeams());

        col_Teams_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Teams_name.setCellValueFactory(new PropertyValueFactory<>("name"));

        tbl_Teams.setItems(list_Teams);
    }

    private void initUsers() {
        list_Users = FXCollections.observableArrayList();
        list_Users.addAll(Repository.Instance.getAllUsers());

        col_Users_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Users_name.setCellValueFactory(new PropertyValueFactory<>("userName"));
        col_Users_name.setCellFactory(TextFieldTableCell.forTableColumn());
        col_Users_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        col_Users_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        col_Users_role.setCellFactory(ComboBoxTableCell.forTableColumn(Role.ADMIN, Role.USER));

        tbl_Users.setItems(list_Users);
    }

    @FXML
    public void btnCreateGameClicked() {
        txt_GameName.setStyle("-fx-border-color: none;");
        txt_GameTime.setStyle("-fx-border-color: none;");
        cbox_T1.setStyle("-fx-border-color: none;");
        cbox_T2.setStyle("-fx-border-color: none;");

        if (txt_GameName.getText().isEmpty()) {
            txt_GameName.setStyle("-fx-border-color: red;");
        } else if (txt_GameTime.getText().isEmpty()) {
            txt_GameTime.setStyle("-fx-border-color: red;");
        } else if (cbox_T1.getValue() == null) {
            cbox_T1.setStyle("-fx-border-color: red;");
        } else if (cbox_T2.getValue() == null) {
            cbox_T2.setStyle("-fx-border-color: red;");
        } else {
            // TODO add to repository
            try {
                list_Games.add(new Game(1, txt_GameName.getText(), cbox_T1.getValue(), cbox_T2.getValue(), LocalDateTime.parse(txt_GameTime.getText())));
            } catch (DateTimeParseException ex) {
                txt_GameTime.setStyle("-fx-border-color: red;");
            }
        }
    }

    @FXML
    public void btnCreateTeamClicked() {
        if (txt_TeamName.getText().isEmpty()) {
            txt_TeamName.setStyle("-fx-border-color: red;");
        } else {
            list_Teams.add(new Team(1, txt_TeamName.getText()));
        }
    }

    @FXML
    public void btnCreateUserClicked() {
        txt_UserName.setStyle("-fx-border-color: none;");
        txt_UserPassword.setStyle("-fx-border-color: none;");
        cbox_UserRole.setStyle("-fx-border-color: none;");

        if (txt_UserName.getText().isEmpty()) {
            txt_UserName.setStyle("-fx-border-color: red;");
        } else if (txt_UserPassword.getText().isEmpty()) {
            txt_UserPassword.setStyle("-fx-border-color: red;");
        } else if (cbox_UserRole.getValue() == null) {
            cbox_UserRole.setStyle("-fx-border-color: red;");
        } else {
            list_Users.add(new User(1, txt_UserName.getText(), txt_UserPassword.getText(), cbox_UserRole.getValue()));
        }
    }
}
