package mypackage.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mypackage.Company;
import mypackage.CompanyDatabaseHandler;

public class CompanyEditController {
    private Company company;
    public static boolean editMode;
    private Integer id;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane editAnchorPane;

    @FXML
    private TextField nameField;

    @FXML
    private Button bntSave;

    @FXML
    private Button btnCancel;

    @FXML
    private TextArea contactField;

    @FXML
    void initialize() {
        bntSave.setOnAction(event ->
                saveCompany());

        btnCancel.setOnAction(event -> {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        });
    }

    void setCompany(Company company) {
        this.company = company;
        getData();
    }

    void getData() {
        nameField.setText(company.getCompanyName());
        contactField.setText(company.getPhoneNumber());
        id = company.getId();
    }

    private void saveCompany() {
        CompanyDatabaseHandler dbHandler = new CompanyDatabaseHandler();
        String companyName = nameField.getText();
        String phoneNumber = contactField.getText();

        if (editMode) {
            Company company = new Company(id, companyName, phoneNumber);
            dbHandler.editCompany(company);
        } else {
            Company company = new Company(companyName, phoneNumber);
            dbHandler.addCompany(company);
        }
        Stage stage = (Stage) bntSave.getScene().getWindow();
        stage.close();
    }
}
