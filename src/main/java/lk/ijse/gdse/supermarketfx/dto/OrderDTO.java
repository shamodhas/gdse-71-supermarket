package lk.ijse.gdse.supermarketfx.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/11/2024 2:43 PM
 * Project: supermarketfx-71
 * --------------------------------------------
 **/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {
    private String orderId;
    private String customerId;
    private Date orderDate;
    private ArrayList<OrderDetailsDTO> orderDetailsDTOS;
}
