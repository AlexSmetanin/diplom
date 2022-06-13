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

    // Get users list from database
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
}
