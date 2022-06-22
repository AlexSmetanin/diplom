package mypackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
import mypackage.*;

import javax.swing.*;

public class PrinterController {

    private ObservableList<Printer4Table> printerData = FXCollections.observableArrayList();
    Map<Integer, String> userMap = new HashMap<>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Printer4Table> printerTable;

    @FXML
    private TableColumn<Printer4Table, Integer> idColumn;

    @FXML
    private TableColumn<Printer4Table, String> modelColumn;

    @FXML
    private TableColumn<Printer4Table, String> userColumn;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    void initialize() throws SQLException {
        initDataUsers();
        initData();

        // Додати новий принтер
        btnAdd.setOnAction(event -> {
            openNewScene("/mypackage/views/printerEditForm.fxml");
            try {
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Редагувати існуючий принтер
        btnEdit.setOnAction(event -> {
            Printer4Table printer4Table = printerTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mypackage/views/printerEditForm.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrinterEditController printerEditController = loader.getController();
            printerEditController.setPrinter(printer4Table);
            printerEditController.editMode = true;
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
            try {
                deletePrinter();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void initDataUsers() throws SQLException {
        userMap.clear();
        UserDatabaseHandler handler = new UserDatabaseHandler();
        ResultSet rs = handler.getAllUsers();
        while (rs.next()) {
            userMap.put(rs.getInt("id"), rs.getString("userName"));
        }
    }

    public void openNewScene(String window) {
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

    private void initData() throws SQLException {
        printerData.clear();
        PrinterDatabaseHandler handler = new PrinterDatabaseHandler();
        ResultSet rs = handler.getAllPrinters();
        while (rs.next()) {
            printerData.add(new Printer4Table(rs.getInt("id"), rs.getString("printerModel"),
                    userMap.get(rs.getInt("userID"))));
        }

        idColumn.setCellValueFactory(   new PropertyValueFactory<Printer4Table, Integer>("id"));
        modelColumn.setCellValueFactory(  new PropertyValueFactory<Printer4Table, String>("printerModel"));
        userColumn.setCellValueFactory(new PropertyValueFactory<Printer4Table, String>("user"));

        printerTable.setItems(printerData);
    }

    private void deletePrinter() throws SQLException {
        int answer = JOptionPane.showConfirmDialog(null,
                "Ви дійсно бажаєте вилучити цей запис?", "Вилучення запису",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (answer == 0) {
            Printer4Table printer4Table = printerTable.getSelectionModel().getSelectedItem();
            int id = printer4Table.getId();
            CartridgeDatabaseHandler cartridgeDatabaseHandler = new CartridgeDatabaseHandler();
            ResultSet rs = cartridgeDatabaseHandler.getCartridgeByPrinterID(id);
            if (rs.next()) {
                JOptionPane.showMessageDialog(null,
                        "Не можна видаляти запис, на який є посилання?", "Помилка!",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                PrinterDatabaseHandler printerDatabaseHandler = new PrinterDatabaseHandler();
                printerDatabaseHandler.deletePrinter(id);
                try {
                    initData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}