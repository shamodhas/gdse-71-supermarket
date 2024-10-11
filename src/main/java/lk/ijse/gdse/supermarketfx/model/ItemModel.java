package lk.ijse.gdse.supermarketfx.model;

import lk.ijse.gdse.supermarketfx.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/11/2024 11:31 AM
 * Project: supermarketfx-71
 * --------------------------------------------
 **/

public class ItemModel {
    public ArrayList<String> getAllItemIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select item_id from item");

        ArrayList<String> itemIds = new ArrayList<>();

        while (rst.next()){
            itemIds.add(rst.getString(1));
        }

        return itemIds;
    }
}
