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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mypackage.*;

import javax.swing.*;

public class cartridgeController {

    private ObservableList<Cartridge4Table> cartridgeData = FXCollections.observableArrayList();
    Map<Integer, String> printerMap = new HashMap<>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane editAnchorPane;

    @FXML
    private TableView<Cartridge4Table> cartridgeTable;

    @FXML
    private TableColumn<Cartridge4Table, Integer> idColumn;

    @FXML
    private TableColumn<Cartridge4Table, Integer> nomerColumn;

    @FXML
    private TableColumn<Cartridge4Table, String> modelColumn;

    @FXML
    private TableColumn<Cartridge4Table, String> printerColumn;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    void initialize() throws SQLException {
        initDataPrinters();
        initData();

        // Додати новий картридж
        btnAdd.setOnAction(event -> {
            openNewScene("/mypackage/views/cartridgeEditForm.fxml");
            try {
                initData(); // оновити дані в таблиці
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Редагувати існуючий картридж
        btnEdit.setOnAction(event -> {
            Cartridge4Table cartridge4Table = cartridgeTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mypackage/views/cartridgeEditForm.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            cartridgeEditController cartridgeEditController = new cartridgeEditController();
            cartridgeEditController.setCartridge(cartridge4Table);
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

            try {
                initData();  // оновити дані в таблиці
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Видалити картридж
        btnDelete.setOnAction(event -> {
            int answer = JOptionPane.showConfirmDialog(null,
                    "Ви дійсно бажаєте вилучити цей запис?", "Вилучення запису",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        });
    }

    private void initData() throws SQLException {
        cartridgeData.clear();
        CartridgeDatabaseHandler handler = new CartridgeDatabaseHandler();
        ResultSet rs = handler.getAllCartridge();
        while (rs.next()) {
            cartridgeData.add(new Cartridge4Table(rs.getInt("id"), rs.getInt("nomer"),
                    rs.getString("model"), printerMap.get(rs.getInt("printerID"))));
        }

        idColumn.setCellValueFactory(   new PropertyValueFactory<Cartridge4Table, Integer>("id"));
        nomerColumn.setCellValueFactory(new PropertyValueFactory<Cartridge4Table, Integer>("nomer"));
        modelColumn.setCellValueFactory(  new PropertyValueFactory<Cartridge4Table, String>("model"));
        printerColumn.setCellValueFactory(  new PropertyValueFactory<Cartridge4Table, String>("printer"));

        cartridgeTable.setItems(cartridgeData);
    }

    // готуємо дані для списку принтерів
    private void initDataPrinters() throws SQLException {
        printerMap.clear();
        PrinterDatabaseHandler handler = new PrinterDatabaseHandler();
        ResultSet rs = handler.getAllPrinters();
        while (rs.next()) {
            printerMap.put(rs.getInt("id"), rs.getString("printerModel"));
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
}
