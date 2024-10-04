package lk.ijse.gdse.supermarketfx.db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/4/2024 2:11 PM
 * Project: supermarketfx
 * --------------------------------------------
 **/

@Getter
public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;
    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306:/Supermarketfx",
                "root",
                "1234"
        );
    }
    public static DBConnection getInstance() throws SQLException {
        if (dbConnection==null){
            dbConnection=new DBConnection();
        }
        return dbConnection;
    }
//    public Connection getConnection(){
//        return connection;
//    }
}
















