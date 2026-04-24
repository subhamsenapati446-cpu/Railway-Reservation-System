import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws Exception {

        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/railway_db?useSSL=false&serverTimezone=UTC",
            "root",
            "subham@911266"
        );
    }
}