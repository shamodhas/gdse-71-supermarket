package lk.ijse.gdse.supermarketfx.model;

import lk.ijse.gdse.supermarketfx.db.DBConnection;
import lk.ijse.gdse.supermarketfx.dto.OrderDTO;
import lk.ijse.gdse.supermarketfx.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/11/2024 11:30 AM
 * Project: supermarketfx-71
 * --------------------------------------------
 **/

public class OrderModel {
    private final OrderDetailsModel orderDetailsModel=new OrderDetailsModel();

    public String getNextOrderId() throws SQLException {
        ResultSet rst =  CrudUtil.execute("select order_id from orders order by order_id desc limit 1");

        if (rst.next()){
            String lastId = rst.getString(1); // C002
            String substring = lastId.substring(1); // 002
            int i = Integer.parseInt(substring); // 2
            int newIdIndex = i+1; // 3
//            String newId = ; // C003
            return String.format("O%03d",newIdIndex);
        }
        return  "O001";
    }

    public boolean saveOrder(OrderDTO orderDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try{
            connection.setAutoCommit(false); // 1

            boolean isOrderSaved = CrudUtil.execute(
                    "insert into orders values (?,?,?)",
                    orderDTO.getOrderId(),
                    orderDTO.getCustomerId(),
                    orderDTO.getOrderDate()
            );
            if (isOrderSaved){
               boolean isOrderDetailListSaved =  orderDetailsModel.saveOrderDetailsList(orderDTO.getOrderDetailsDTOS());
               if (isOrderDetailListSaved){
                   connection.commit(); // 2
                   return true;
               }
            }
            connection.rollback(); // 3
            return false;
        }catch (Exception e){
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true); // 4
        }
    }
}
