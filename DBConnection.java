import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/railway_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
            "root",
            "subham@911266"
        );
    }
}