package mypackage;

import java.sql.*;

public class CartridgeDatabaseHandler extends Configs{
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString,
                dbUser, dbPass);

        return dbConnection;
    }

    // Add new cartridge to database
    public void addCartridge(Cartridge cartridge)  {
        String insert = "INSERT INTO   " + CartridgeConst.CARTRIDGE_TABLE + "(" +
                CartridgeConst.CARTRIDGE_NOMER + "," + CartridgeConst.CARTRIDGE_MODEL + "," +
                CartridgeConst.CARTRIDGE_PRINTERID + ")" +
                "VALUES(?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, cartridge.getNomer());
            prSt.setString(2, cartridge.getModel());
            prSt.setInt(3, cartridge.getPrinterID());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Get cartridge list from database
    public ResultSet getCartridge(Cartridge cartridge) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + CartridgeConst.CARTRIDGE_TABLE + " WHERE " +
                CartridgeConst.CARTRIDGE_NOMER + "=? AND " + CartridgeConst.CARTRIDGE_MODEL + "=? AND " +
                CartridgeConst.CARTRIDGE_PRINTERID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, cartridge.getNomer());
            prSt.setString(2, cartridge.getModel());
            prSt.setInt(3, cartridge.getPrinterID());
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Get allcartridges from database
    public ResultSet getAllCartridge() {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + CartridgeConst.CARTRIDGE_TABLE;

        try {
            resultSet = getDbConnection().createStatement().executeQuery(select);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Get cartridge by printer ID
    public ResultSet getCartridgeByPrinterID(int id) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + CartridgeConst.CARTRIDGE_TABLE + " WHERE " +
                CartridgeConst.CARTRIDGE_PRINTERID + " = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, id);
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Delete cartridge by ID
    public void deleteCartridge(int id) {
        String delete = "DELETE FROM " + PrinterConst.PRINTER_TABLE +
                " WHERE " + PrinterConst.PRINTER_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setInt(1, id);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void editCartridge(Cartridge cartridge) {
        String update = "UPDATE " + CartridgeConst.CARTRIDGE_TABLE + " SET " +
                CartridgeConst.CARTRIDGE_NOMER + " = ?," + CartridgeConst.CARTRIDGE_MODEL + " = ?" +
                CartridgeConst.CARTRIDGE_PRINTERID + " = ?" +
                " WHERE " + CartridgeConst.CARTRIDGE_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setInt(1, cartridge.getNomer());
            prSt.setString(2, cartridge.getModel());
            prSt.setInt(3, cartridge.getPrinterID());
            prSt.setInt(4, cartridge.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
