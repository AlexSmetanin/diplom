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
import mypackage.*;

import javax.swing.*;

public class CompanyController {

    private ObservableList<Company> companyData = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Company> viewTable;

    @FXML
    private TableColumn<Company, Integer> idColumn;

    @FXML
    private TableColumn<Company, String> nameColumn;

    @FXML
    private TableColumn<Company, String> contactColumn;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    void initialize() throws SQLException {
        initData();

        btnAdd.setOnAction(event -> {
            openNewScene("/mypackage/views/companyEditForm.fxml");
            try {
                initData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        btnEdit.setOnAction(event -> {
            Company company = viewTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mypackage/views/companyEditForm.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            CompanyEditController companyEditController = loader.getController();
            companyEditController.setCompany(company);
            CompanyEditController.editMode = true;
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
                deleteCompany();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    // готуємо дані для табліці
    private void initData() throws SQLException {
        companyData.clear();
        CompanyDatabaseHandler handler = new CompanyDatabaseHandler();
        ResultSet rs = handler.getAllCompany();
        while (rs.next()) {
            companyData.add(new Company(rs.getInt("id"), rs.getString("companyName"),
                    rs.getString("phoneNumber")));
        }

        idColumn.setCellValueFactory(   new PropertyValueFactory<Company, Integer>("id"));
        nameColumn.setCellValueFactory(  new PropertyValueFactory<Company, String>("companyName"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<Company, String>("phoneNumber"));

        viewTable.setItems(companyData);
    }

    private void deleteCompany() throws SQLException {
        int answer = JOptionPane.showConfirmDialog(null,
                "Ви дійсно бажаєте вилучити цей запис?", "Вилучення запису",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (answer == 0) {
            Company company = viewTable.getSelectionModel().getSelectedItem();
            int id = company.getId();
            ZapravkiDatabaseHandler zapravkiDatabaseHandler = new ZapravkiDatabaseHandler();
            ResultSet rs = zapravkiDatabaseHandler.getZapravkiByCompanyID(id);
            if (rs.next()) {
                JOptionPane.showMessageDialog(null,
                        "Не можна видаляти запис, на який є посилання?", "Помилка!",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                CompanyDatabaseHandler companyDatabaseHandler = new CompanyDatabaseHandler();
                companyDatabaseHandler.deleteCompany(id);
                try {
                    initData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
