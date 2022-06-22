package mypackage.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mypackage.Printer;
import mypackage.Printer4Table;
import mypackage.PrinterDatabaseHandler;
import mypackage.UserDatabaseHandler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PrinterEditController {
    private Printer printer;
    private Printer4Table printer4Table;
    Map<Integer, String> userMap = new HashMap<>();
    public static boolean editMode;
    private int id;

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
                savePrinter());

        btnCancel.setOnAction(event -> {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        });
    }

    // готуємо дані для випадаючого списку корстувачів
    private void initData() throws SQLException {
        userMap.clear();
        UserDatabaseHandler handler = new UserDatabaseHandler();
        ResultSet rs = handler.getAllUsers();
        while (rs.next()) {
            userField.getItems().add(rs.getString("userName"));
            userMap.put(rs.getInt("id"), rs.getString("userName"));
        }
    }

    void setPrinter(Printer4Table printer4Table) {
        this.printer4Table = printer4Table;
        getData();
    }

    void getData() {
        modelField.setText(printer4Table.getPrinterModel());
        userField.setValue(printer4Table.getUser());
        id = printer4Table.getId();
    }

    private void savePrinter() {
        PrinterDatabaseHandler dbHandler = new PrinterDatabaseHandler();
        String printerModel = modelField.getText();
        Integer userID = 0;

        String value = userField.getValue().toString();
        for(Integer k : userMap.keySet()) {
           if (userMap.get(k).equals(userField.getValue().toString())) {
               userID = k;
               break;
           }
        }

        if (editMode) {
            Printer printer = new Printer(id, printerModel, userID);
            dbHandler.editPrinter(printer);
        } else {
            Printer printer = new Printer(printerModel, userID);
            dbHandler.addPrinter(printer);
        }
        Stage stage = (Stage) bntSave.getScene().getWindow();
        stage.close();
    }
}