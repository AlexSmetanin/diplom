package mypackage;

import java.sql.*;

public class UserDatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString,
                dbUser, dbPass);

        return dbConnection;
    }

    // Додати нового користувача в базу даних
    public void addUser(User user)  {
        String insert = "INSERT INTO   " + UserConst.USER_TABLE + "(" +
                UserConst.USER_LOGIN + "," + UserConst.USER_PASSWORD + "," +
                UserConst.USER_NAME + "," +  UserConst.USER_OTDEL + "," +
                UserConst.USER_ROLE + ")" +
                "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, user.getUserName());
            prSt.setString(4, user.getOtdel());
            prSt.setString(5, user.getRole());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Отримати користувача з певним логіном та паролем
    public ResultSet getUser(User user) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + UserConst.USER_TABLE + " WHERE " +
                UserConst.USER_LOGIN + "=? AND " + UserConst.USER_PASSWORD + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Отримати список всіх користувачів з бази даних
    public ResultSet getAllUsers() {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + UserConst.USER_TABLE;

        try {
            resultSet = getDbConnection().createStatement().executeQuery(select);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Редагувати дані користувача в базі даних
    public void editUser(User user) {
        String update = "UPDATE " + UserConst.USER_TABLE + " SET " +
                UserConst.USER_LOGIN + " = ?," + UserConst.USER_PASSWORD + " = ?," +
                UserConst.USER_NAME + " = ?," +  UserConst.USER_OTDEL + " = ?," +
                UserConst.USER_ROLE + " = ?" +
                " WHERE " + UserConst.USER_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, user.getUserName());
            prSt.setString(4, user.getOtdel());
            prSt.setString(5, user.getRole());
            prSt.setInt(6, user.getId());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Вилучити користувача з бази даних
    public void deleteUser(Integer id) {
        String delete = "DELETE FROM " + UserConst.USER_TABLE +
                " WHERE " + UserConst.USER_ID + " = ?";
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
}
