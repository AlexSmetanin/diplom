package mypackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imgView;

    @FXML
    private Button btnCartridge;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnZapravki;

    @FXML
    private Button btnPrinters;

    @FXML
    private Button btnCompany;

    @FXML
    void initialize() {
        imgView.setImage(
                new Image("file:../../resources/gerb.png")
        );

        btnUsers.setOnAction(event -> {
            openNewScene("/mypackage/views/usersForm.fxml", 1);
        });

        btnCompany.setOnAction(event -> {
            openNewScene("/mypackage/views/companyForm.fxml", 1);
        });

        btnPrinters.setOnAction(event -> {
            openNewScene("/mypackage/views/printersForm.fxml", 1);
        });

        btnZapravki.setOnAction(event -> {
            openNewScene("/mypackage/views/zapravkiForm.fxml", 1);
        });

        btnCartridge.setOnAction(event -> {
            openNewScene("/mypackage/views/cartridgeForm.fxml", 1);
        });
    }

    public void openNewScene(String window, int flag) {
        btnUsers.getScene().getWindow().hide();

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
        stage.showAndWait();
        if (flag==1) {
            openNewScene("/mypackage/views/mainForm.fxml", 0);
        }
    }
}
