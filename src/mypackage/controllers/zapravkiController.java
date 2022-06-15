package mypackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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

public class zapravkiController {
    private ObservableList<Zapravki4Table> zapravkiData = FXCollections.observableArrayList();
    Map<Integer, String> cartridgeMap = new HashMap<>();
    Map<Integer, String> companyMap = new HashMap<>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Zapravki4Table> zapravkiTable;

    @FXML
    private TableColumn<Zapravki4Table, Integer> idColumn;

    @FXML
    private TableColumn<Zapravki4Table, Date> dataColumn;

    @FXML
    private TableColumn<Zapravki4Table, String> nameColumn;

    @FXML
    private TableColumn<Zapravki4Table, String> copmanyColumn;

    @FXML
    private TableColumn<Zapravki4Table, Double> sumaColumn;

    @FXML
    private TableColumn<Zapravki4Table, String> workColumn;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReport;

    @FXML
    void initialize() throws SQLException {
        initDataCartridge();
        initDataCompany();
        initData();

        // Додати новий картридж
        btnAdd.setOnAction(event -> {
            openNewScene("/mypackage/views/zapravkiEditForm.fxml");
            try {
                initData(); // оновити дані в таблиці
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Видалити заправку
        btnDelete.setOnAction(event -> {
            int answer = JOptionPane.showConfirmDialog(null,
                    "Ви дійсно бажаєте вилучити цей запис?", "Вилучення запису",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        });

        // Зформувати звіт
        btnReport.setOnAction(event -> {
            openNewScene("/mypackage/views/zvit.fxml");
        });
    }

    private void initData() throws SQLException {
        zapravkiData.clear();
        ZapravkiDatabaseHandler handler = new ZapravkiDatabaseHandler();
        ResultSet rs = handler.getAllZapravki();
        while (rs.next()) {
            zapravkiData.add(new Zapravki4Table(rs.getInt("id"), rs.getDate("data"),
                    cartridgeMap.get(rs.getInt("cartridgeID")), companyMap.get(rs.getInt("company")),
                    rs.getDouble("suma"), rs.getString("work")));
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<Zapravki4Table, Integer>("id"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<Zapravki4Table, Date>("data"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Zapravki4Table, String>("cartridgeID"));
        copmanyColumn.setCellValueFactory(new PropertyValueFactory<Zapravki4Table, String>("companyID"));
        sumaColumn.setCellValueFactory(new PropertyValueFactory<Zapravki4Table, Double>("suma"));
        workColumn.setCellValueFactory(new PropertyValueFactory<Zapravki4Table, String>("work"));

        zapravkiTable.setItems(zapravkiData);
    }

    private void initDataCompany() throws SQLException {
        companyMap.clear();
        CompanyDatabaseHandler companyDatabaseHandler = new CompanyDatabaseHandler();
        ResultSet rs = companyDatabaseHandler.getAllCompany();
        while (rs.next()) {
            companyMap.put(rs.getInt("id"), rs.getString("companyName"));
        }
    }

    private void initDataCartridge() throws SQLException {
        cartridgeMap.clear();
        CartridgeDatabaseHandler cartridgeDatabaseHandler = new CartridgeDatabaseHandler();
        ResultSet rs = cartridgeDatabaseHandler.getAllCartridge();
        while (rs.next()) {
            cartridgeMap.put(rs.getInt("id"), rs.getString("model"));
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
