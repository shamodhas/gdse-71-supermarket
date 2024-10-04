package lk.ijse.gdse.supermarketfx.model;

import lk.ijse.gdse.supermarketfx.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/4/2024 2:39 PM
 * Project: supermarketfx
 * --------------------------------------------
 **/

public class CustomerModel {
    public String getNextCustomerId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select customer_id from customer order by customer_id desc limit 1";
        PreparedStatement pst = connection.prepareStatement(sql);

        ResultSet rst = pst.executeQuery();
        if (rst.next()){
            String lastId = rst.getString(1); // C002
            String substring = lastId.substring(1); // 002
            int i = Integer.parseInt(substring); // 2
            int newIdIndex = i+1; // 3
//            String newId = ; // C003
            return String.format("C%03d",newIdIndex);
        }
        return  "C001";
    }
}














