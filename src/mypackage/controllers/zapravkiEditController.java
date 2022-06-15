package mypackage.controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mypackage.*;

public class zapravkiEditController {
    private Zapravki zapravki;
    private Zapravki4Table zapravki4Table;
    Map<Integer, String> cartridgeMap = new HashMap<>();
    Map<Integer, String> companyMap = new HashMap<>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane editAnchorPane;

    @FXML
    private DatePicker dateField;

    @FXML
    private ComboBox<String> cartridgeComboBox;

    @FXML
    private ComboBox<String> companyComboBox;

    @FXML
    private TextField sumaField;

    @FXML
    private TextArea workField;

    @FXML
    private Button bntSave;

    @FXML
    private Button btnCancel;

    @FXML
    void initialize() throws SQLException {
        initData();

        bntSave.setOnAction(event ->
                addNewZapravka());

        btnCancel.setOnAction(event -> {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        });
    }

    private void addNewZapravka() {
        ZapravkiDatabaseHandler handler = new ZapravkiDatabaseHandler();
        Date data = Date.valueOf(dateField.getValue());
        Integer cartridgeID = 0;
        Integer companyID = 0;
        Double suma = Double.valueOf(sumaField.getText());
        String work = workField.getText();

        String value = cartridgeComboBox.getValue().toString();
        for(Integer k : cartridgeMap.keySet()) {
            if (cartridgeMap.get(k).equals(cartridgeComboBox.getValue().toString())) {
                cartridgeID = k;
                break;
            }
        }

        value = companyComboBox.getValue().toString();
        for(Integer k : companyMap.keySet()) {
            if (companyMap.get(k).equals(companyComboBox.getValue().toString())) {
                companyID = k;
                break;
            }
        }

        Zapravki zapravki = new Zapravki(data, cartridgeID, companyID, suma, work);
        handler.addZapravki(zapravki);
        Stage stage = (Stage) bntSave.getScene().getWindow();
        stage.close();
    }

    // готуємо дані для випадаючих спискі картриджів та компаній
    private void initData() throws SQLException {
        cartridgeMap.clear();
        CartridgeDatabaseHandler cartridgeDatabaseHandler = new CartridgeDatabaseHandler();
        ResultSet rs = cartridgeDatabaseHandler.getAllCartridge();
        while (rs.next()) {
            cartridgeComboBox.getItems().add(rs.getString("model"));
            cartridgeMap.put(rs.getInt("id"), rs.getString("model"));
        }
        companyMap.clear(); rs = null;
        CompanyDatabaseHandler companyDatabaseHandler = new CompanyDatabaseHandler();
        rs = companyDatabaseHandler.getAllCompany();
        while (rs.next()) {
            companyComboBox.getItems().add(rs.getString("companyName"));
            companyMap.put(rs.getInt("id"), rs.getString("companyName"));
        }
    }
}
