package Library.database;

import java.sql.*;

public class DataBase {

    private String url = "jdbc:mysql://localhost:3306/";
    private String username = "root";
    private String password = "Ashkan007";
    private Connection connection;

    public DataBase(final String username, final String password, String database) {
        loadDriver();
        this.username = username;
        this.password = password;
        url += database;
    }

    public void connectDatabase() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet runQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery(query);
        return statement.getResultSet();
    }

    private void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
