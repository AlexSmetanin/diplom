package mypackage.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import mypackage.Company;
import mypackage.CompanyDatabaseHandler;
import mypackage.User;

public class companyEditController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane editAnchorPane;

    @FXML
    private TextField txtCompanyName;

    @FXML
    private Button bntSave;

    @FXML
    private Button btnCancel;

    @FXML
    private TextArea txtContacts;

    @FXML
    void initialize() {
        bntSave.setOnAction(event ->
                addNewCompany());
    }

    private void addNewCompany() {
        CompanyDatabaseHandler dbHandler = new CompanyDatabaseHandler();
        String companyName = txtCompanyName.getText();
        String phoneNumber = txtContacts.getText();

        Company company = new Company(companyName, phoneNumber);
        dbHandler.addCompany(company);
    }
}
