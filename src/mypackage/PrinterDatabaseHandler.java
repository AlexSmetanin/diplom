package mypackage;

import java.sql.*;

public class PrinterDatabaseHandler extends Configs{
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString,
                dbUser, dbPass);

        return dbConnection;
    }

    // Add new printer to database
    public void addPrinter(Printer printer)  {
        String insert = "INSERT INTO   " + PrinterConst.PRINTER_TABLE + "(" +
                PrinterConst.PRINTER_MODEL + "," + PrinterConst.PRINTER_USERID + ")" +
                "VALUES(?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, printer.getPrinterModel());
            prSt.setInt(2, printer.getUserID());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Get printers list from database
    public ResultSet getPrinter(Printer printer) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + PrinterConst.PRINTER_TABLE + " WHERE " +
                PrinterConst.PRINTER_MODEL + "=? AND " + PrinterConst.PRINTER_USERID + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, printer.getPrinterModel());
            prSt.setInt(2, printer.getUserID());
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Get printer list from database
    public ResultSet getAllPrinters() {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + PrinterConst.PRINTER_TABLE;

        try {
            resultSet = getDbConnection().createStatement().executeQuery(select);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Get printers list from database by user ID
    public ResultSet getPrinterByUserID(Integer id) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + PrinterConst.PRINTER_TABLE + " WHERE " +
                PrinterConst.PRINTER_USERID + " = ?";

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

    // Delete printer
    public void deletePrinter(int id) {
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

    // Edit printer
    public void editPrinter(Printer printer) {
        String update = "UPDATE " + PrinterConst.PRINTER_TABLE + " SET " +
                PrinterConst.PRINTER_MODEL + " = ?," + PrinterConst.PRINTER_USERID + " = ?" +
                " WHERE " + PrinterConst.PRINTER_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setString(1, printer.getPrinterModel());
            prSt.setInt(2, printer.getUserID());
            prSt.setInt(3, printer.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
