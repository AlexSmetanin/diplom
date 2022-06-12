package mypackage.controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mypackage.Printer;
import mypackage.PrinterDatabaseHandler;
import mypackage.User;
import mypackage.UserDatabaseHandler;

public class PrinterEditController {
    private Printer printer;
    private ObservableList<String> users = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane editAnchorPane;

    @FXML
    private TextField modelField;

    @FXML
    private Button bntSave;

    @FXML
    private Button btnCancel;

    @FXML
    private ComboBox<String> userField;

    @FXML
    void initialize() throws SQLException {
        initData();

        bntSave.setOnAction(event ->
                addNewPrinter());

        btnCancel.setOnAction(event -> {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        });
    }

    void setPrinter(Printer printer) {
        this.printer = printer;
        getData();
    }

    // готуємо дані для табліці
    private void initData() throws SQLException {
        users.clear();
        UserDatabaseHandler handler = new UserDatabaseHandler();
        ResultSet rs = handler.getAllUsers();
        while (rs.next()) {
            userField.getItems().add(rs.getString("userName"));
        }
    }

    void getData() {
        modelField.setText(printer.getPrinterModel());
        userField.getSelectionModel();
    }

    private void addNewPrinter() {
        PrinterDatabaseHandler dbHandler = new PrinterDatabaseHandler();
        String printerModel = modelField.getText();
        String userID = userField.getValue();

        //Printer printer = new Printer(printerModel, userID);
        dbHandler.addPrinter(printer);
        Stage stage = (Stage) bntSave.getScene().getWindow();
        stage.close();
    }
}
