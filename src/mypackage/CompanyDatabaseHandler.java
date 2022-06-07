package mypackage;

import java.sql.*;

public class CompanyDatabaseHandler extends Configs{
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString,
                dbUser, dbPass);

        return dbConnection;
    }

    // Add new company to database
    public void addCompany(Company company)  {
        String insert = "INSERT INTO   " + CompanyConst.COMPANY_TABLE + "(" +
                CompanyConst.COMPANY_NAME + "," + CompanyConst.COMPANY_PHONE +
                "VALUES(?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, company.getCompanyName());
            prSt.setString(2, company.getPhoneNumber());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Get users list from database
    public ResultSet getCompany(Company company) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + CompanyConst.COMPANY_TABLE + " WHERE " +
                CompanyConst.COMPANY_NAME + "=? AND " + CompanyConst.COMPANY_PHONE + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, company.getCompanyName());
            prSt.setString(2, company.getPhoneNumber());
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
}
