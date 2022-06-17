package mypackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mypackage.Cartridge4Table;
import mypackage.User;
import mypackage.UserDatabaseHandler;

import javax.swing.*;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    void initialize() throws SQLException {
        loginButton.setOnAction(event -> {
            User user = new User(loginField.getText(), passwordField.getText());
            UserDatabaseHandler userDatabaseHandler = new UserDatabaseHandler();
            ResultSet rs = userDatabaseHandler.getUser(user);
            try {
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null,"Логін або пароль не вірні!", "Помилка!", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    try {
                        if (rs.getString("role").equals("user")) {
                            MainController.isRoleUser = true;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    loginButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mypackage/views/mainForm.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage = new Stage();
                    stage.setTitle("Облік картриджів в Черкаському політехнічному фаховому коледжі");
                    stage.getIcons().add(new Image("file:resources/images/printer2.png"));
                    stage.setScene(new Scene(loader.getRoot()));
                    stage.show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
