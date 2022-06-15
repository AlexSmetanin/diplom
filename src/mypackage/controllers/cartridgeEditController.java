package mypackage.controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mypackage.Cartridge;
import mypackage.Cartridge4Table;
import mypackage.CartridgeDatabaseHandler;
import mypackage.PrinterDatabaseHandler;

public class cartridgeEditController {
    private Cartridge cartridge;
    private Cartridge4Table cartridge4Table;
    Map<Integer, String> printerMap = new HashMap<>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane editAnchorPane;

    @FXML
    private TextField nomer;

    @FXML
    private TextField model;

    @FXML
    private ComboBox<String> printerComboBox;

    @FXML
    private Button bntSave;

    @FXML
    private Button btnCancel;

    @FXML
    void initialize() throws SQLException {
        initData();

        bntSave.setOnAction(event ->
                addNewCartridge());

        btnCancel.setOnAction(event -> {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        });
    }

    private void addNewCartridge() {
        CartridgeDatabaseHandler cartridgeDatabaseHandler = new CartridgeDatabaseHandler();
        Integer nom =  Integer.parseInt(nomer.getText());
        String mod = model.getText();
        Integer printerID = 0;

        String value = printerComboBox.getValue().toString();
        for(Integer k : printerMap.keySet()) {
            if (printerMap.get(k).equals(printerComboBox.getValue().toString())) {
                printerID = k;
                break;
            }
        }

        Cartridge cartridge = new Cartridge(nom, mod, printerID);
        cartridgeDatabaseHandler.addCartridge(cartridge);
        Stage stage = (Stage) bntSave.getScene().getWindow();
        stage.close();
    }

    void setCartridge(Cartridge4Table cartridge4Table) {
        this.cartridge4Table = cartridge4Table;
        getData();
    }


    void getData() {
        nomer.setText(cartridge4Table.getNomer().toString());
        model.setText(cartridge4Table.getModel());
        printerComboBox.setValue(printerMap.get(cartridge4Table.getId()));
    }

    // готуємо дані для випадаючого списку принтерів
    private void initData() throws SQLException {
        printerMap.clear();
        PrinterDatabaseHandler handler = new PrinterDatabaseHandler();
        ResultSet rs = handler.getAllPrinters();
        while (rs.next()) {
            printerComboBox.getItems().add(rs.getString("printerModel"));
            printerMap.put(rs.getInt("id"), rs.getString("printerModel"));
        }
    }
}
