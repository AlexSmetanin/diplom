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
                PrinterConst.PRINTER_MODEL + "," + PrinterConst.PRINTER_USERID +
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
}
