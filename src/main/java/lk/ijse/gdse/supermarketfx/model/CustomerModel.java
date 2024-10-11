package lk.ijse.gdse.supermarketfx.model;

import lk.ijse.gdse.supermarketfx.db.DBConnection;
import lk.ijse.gdse.supermarketfx.dto.CustomerDTO;
import lk.ijse.gdse.supermarketfx.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
//        Connection connection = DBConnection.getInstance().getConnection();
//        String sql = "select customer_id from customer order by customer_id desc limit 1";
//        PreparedStatement pst = connection.prepareStatement(sql);

//        ResultSet rst = pst.executeQuery();

       ResultSet rst =  CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");

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

    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        String sql = "insert into customer values (?,?,?,?,?)";
//        PreparedStatement pst = connection.prepareStatement(sql);
//
//        pst.setObject(1,customerDTO.getCustomerId());
//        pst.setObject(2,customerDTO.getName());
//        pst.setObject(3,customerDTO.getNic());
//        pst.setObject(4,customerDTO.getEmail());
//        pst.setObject(5,customerDTO.getPhone());

//        int result = pst.executeUpdate();
//        boolean isSaved = result>0;

      boolean isSaved =  CrudUtil.execute(
              "insert into customer values (?,?,?,?,?)",
              customerDTO.getCustomerId(),
              customerDTO.getName(),
              customerDTO.getNic(),
              customerDTO.getEmail(),
              customerDTO.getPhone()
      );

        return isSaved;
    }

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
       ResultSet rst =  CrudUtil.execute("select * from customer");

        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        while (rst.next()){
            CustomerDTO customerDTO = new CustomerDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
       return CrudUtil.execute(
                "update customer set name=?, nic=?, email=?, phone=? where customer_id=?",
                customerDTO.getName(),
                customerDTO.getNic(),
                customerDTO.getEmail(),
                customerDTO.getPhone(),
                customerDTO.getCustomerId()
        );
    }

    public boolean deleteCustomer(String customerId) throws SQLException {
       return CrudUtil.execute("delete from customer where customer_id=?",customerId);
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()){
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }

    public CustomerDTO findById(String selectedCusId) throws SQLException {
       ResultSet rst =  CrudUtil.execute("select * from customer where customer_id=?",selectedCusId);

       if (rst.next()){
           return new CustomerDTO(
                   rst.getString(1),
                   rst.getString(2),
                   rst.getString(3),
                   rst.getString(4),
                   rst.getString(5)
           );
       }
       return null;
    }
}














