package mypackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mypackage.User;
import mypackage.UserDatabaseHandler;

import javax.swing.*;

public class UserController {

    private ObservableList<User> usersData = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> fioColumn;

    @FXML
    private TableColumn<User, String> loginColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> otdelColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    void initialize() throws SQLException{
        initData();

        // Додати нового користувача
        btnAdd.setOnAction(event -> {
            openNewScene("/mypackage/views/userEditForm.fxml");
            try {
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Редагувати існуючого користувача
        btnEdit.setOnAction(event -> {
            User user = usersTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mypackage/views/userEditForm.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            UserEditController userEditController = loader.getController();
            userEditController.setUser(user);
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

            try {
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        btnDelete.setOnAction(event -> {
            int answer = JOptionPane.showConfirmDialog(null,
                    "Ви дійсно бажаєте вилучити цей запис?", "Вилучення запису",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        });
    }

    public void openNewScene(String window) {
        //btnAdd.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
    }

    // готуємо дані для табліці
    private void initData() throws SQLException {
        usersData.clear();
        UserDatabaseHandler handler = new UserDatabaseHandler();
        ResultSet rs = handler.getAllUsers();
        while (rs.next()) {
            usersData.add(new User(rs.getInt("id"), rs.getString("login"),
                    rs.getString("password"), rs.getString("userName"),
                    rs.getString("otdel"), rs.getString("role")));
        }

        idColumn.setCellValueFactory(   new PropertyValueFactory<User, Integer>("id"));
        fioColumn.setCellValueFactory(  new PropertyValueFactory<User, String>("userName"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        otdelColumn.setCellValueFactory(new PropertyValueFactory<User, String>("otdel"));
        roleColumn.setCellValueFactory( new PropertyValueFactory<User, String>("role"));

        usersTable.setItems(usersData);
    }
}
