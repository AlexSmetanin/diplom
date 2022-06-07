package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import sample.UserDatabaseHandler;
import sample.User;

public class UserEditController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane userEditAnchorPane;

    @FXML
    private TextField userFIO;

    @FXML
    private TextField userLogin;

    @FXML
    private TextField userPassword;

    @FXML
    private TextField userOtdel;

    @FXML
    private RadioButton userRoleAdmin;

    @FXML
    private ToggleGroup role;

    @FXML
    private RadioButton userRoleUser;

    @FXML
    private Button bntSave;

    @FXML
    private Button btnCancel;

    @FXML
    void initialize() {
       bntSave.setOnAction(event ->
               addNewUser());
    }

    private void addNewUser() {
        UserDatabaseHandler dbHandler = new UserDatabaseHandler();
        String userName = userFIO.getText();
        String login = userLogin.getText();
        String password = userPassword.getText();
        String otdel = userOtdel.getText();
        String role = "";
        if (userRoleAdmin.isSelected())
            role = "admin";
        else
            role = "user";

        User user = new User(userName, login, password, otdel, role);
        dbHandler.addUser(user);
    }
}
