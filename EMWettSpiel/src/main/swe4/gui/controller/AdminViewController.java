package swe4.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import swe4.gui.Startup;
import swe4.gui.data.Entities.*;
import swe4.gui.data.Repository;

import java.time.LocalDateTime;

public class AdminViewController {

    public static class TeamCell<T> extends TableCell<T, Team> {

        public TeamCell() {
            super();
        }

        @Override
        protected void updateItem(Team team, boolean b) {
            super.updateItem(team, b);

            setText(team.getName());
        }
    }

    public static Scene scene = null;
    public static Parent rootElement = null;

    public TableView<Team> tbl_Teams;
    public TableView<Game> tbl_Games;

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
        list_Users = FXCollections.observableArrayList();

        list_Users.addAll(Repository.Instance.getAllUsers());

        initGames();
        initTeams();
    }

    private void initGames() {
        list_Games = FXCollections.observableArrayList();
        list_Games.addAll(Repository.Instance.getAllGames());

        col_Games_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Games_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_Games_t1.setCellValueFactory(new PropertyValueFactory<>("t1"));
        col_Games_t1.setCellFactory(new Callback<TableColumn<Game, Team>, TableCell<Game, Team>>() {
            @Override
            public TableCell<Game, Team> call(TableColumn<Game, Team> gameTeamTableColumn) {
                return new TeamCell<>();
            }
        });
        col_Games_t2.setCellValueFactory(new PropertyValueFactory<>("t2"));
        col_Games_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_Games_scoreT1.setCellValueFactory(new PropertyValueFactory<>("scoreT1"));
        col_Games_scoreT2.setCellValueFactory(new PropertyValueFactory<>("scoreT2"));

        tbl_Games.setItems(list_Games);
    }

    private void initTeams() {
        list_Teams = FXCollections.observableArrayList();
        list_Teams.addAll(Repository.Instance.getAllTeams());

        col_Teams_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Teams_name.setCellValueFactory(new PropertyValueFactory<>("name"));

        tbl_Teams.setItems(list_Teams);
    }
}
