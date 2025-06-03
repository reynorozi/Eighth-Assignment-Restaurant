package ap.restaurant.restaurant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    public static Connection database() throws SQLException {

        String JDBCURL = "jdbc:postgresql://localhost:5432/Restaurant";

        Connection connection = DriverManager.getConnection(JDBCURL, "postgres", "Rey@2006");

        if(connection != null){
        return connection;
        }
        else{
        return null;
        }
    }
}
