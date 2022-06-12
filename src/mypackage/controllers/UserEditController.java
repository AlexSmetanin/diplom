package mypackage.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mypackage.UserDatabaseHandler;
import mypackage.User;

public class UserEditController {
    private User user;

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
    private RadioButton userRoleUser;

    @FXML
    private ToggleGroup role;

    @FXML
    private RadioButton userRoleAdmin;

    @FXML
    private Button bntSave;

    @FXML
    private Button btnCancel;

    @FXML
    void initialize() {

       bntSave.setOnAction(event ->
               addNewUser());

       btnCancel.setOnAction(event -> {
           Stage stage = (Stage) btnCancel.getScene().getWindow();
           stage.close();
       });
    }

    void setUser(User user) {
        this.user = user;
        getData();
    }

    void getData() {
        userFIO.setText(user.getUserName());
        userLogin.setText(user.getLogin());
        userPassword.setText(user.getPassword());
        userOtdel.setText(user.getOtdel());
        if (user.getRole().equals("admin")) {
            userRoleAdmin.setSelected(true);
            userRoleUser.setSelected(false);
        } else {
            userRoleAdmin.setSelected(false);
            userRoleUser.setSelected(true);
        }
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

        User user = new User(login, password, userName, otdel, role);
        dbHandler.addUser(user);
        Stage stage = (Stage) bntSave.getScene().getWindow();
        stage.close();
    }
}
