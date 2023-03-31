package lk.ijse.gdse.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection;
    private Connection connection;
    private DbConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/sanasa?allowPublicKeyRetrieval=true&useSSL=false","root","1234");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static DbConnection getDbConnection(){
        return dbConnection==null?dbConnection=new DbConnection(): dbConnection;
    }
    public Connection getConnection(){return connection;}
}
