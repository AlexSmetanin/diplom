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
import mypackage.Printer;
import mypackage.PrinterDatabaseHandler;
import mypackage.User;
import mypackage.UserDatabaseHandler;

import javax.swing.*;

public class PrinterController {

    private ObservableList<Printer> printerData = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Printer> printerTable;

    @FXML
    private TableColumn<Printer, Integer> idColumn;

    @FXML
    private TableColumn<Printer, String> modelColumn;

    @FXML
    private TableColumn<Printer, Integer> userColumn;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    void initialize() throws SQLException {
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
            Printer printer = printerTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mypackage/views/printerEditForm.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrinterEditController printerEditController = loader.getController();
            printerEditController.setPrinter(printer);
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
            printerData.add(new Printer(rs.getInt("id"), rs.getString("printerModel"),
                    rs.getInt("userID")));
        }

        idColumn.setCellValueFactory(   new PropertyValueFactory<Printer, Integer>("id"));
        modelColumn.setCellValueFactory(  new PropertyValueFactory<Printer, String>("printerModel"));
        userColumn.setCellValueFactory(new PropertyValueFactory<Printer, Integer>("userID"));

        printerTable.setItems(printerData);
    }
}