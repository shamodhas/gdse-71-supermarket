package lk.ijse.gdse.supermarketfx.controller;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/4/2024 9:32 AM
 * Project: supermarketfx
 * --------------------------------------------
 **/

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.supermarketfx.dto.CustomerDTO;
import lk.ijse.gdse.supermarketfx.dto.ItemDTO;
import lk.ijse.gdse.supermarketfx.dto.OrderDTO;
import lk.ijse.gdse.supermarketfx.dto.OrderDetailsDTO;
import lk.ijse.gdse.supermarketfx.dto.tm.CartTM;
import lk.ijse.gdse.supermarketfx.model.CustomerModel;
import lk.ijse.gdse.supermarketfx.model.ItemModel;
import lk.ijse.gdse.supermarketfx.model.OrderModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbItemId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<CartTM, String> colItemId;

    @FXML
    private TableColumn<CartTM, String> colName;

    @FXML
    private TableColumn<CartTM, Double> colPrice;

    @FXML
    private TableColumn<CartTM, Integer> colQuantity;

    @FXML
    private TableColumn<CartTM, Double> colTotal;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblItemPrice;

    @FXML
    private Label lblItemQty;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label orderDate;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private TextField txtAddToCartQty;

    private final OrderModel orderModel = new OrderModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final ItemModel itemModel = new ItemModel();

    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data").show();
        }
    }

    private void setCellValues() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        tblCart.setItems(cartTMS);
    }

    private void refreshPage() throws SQLException {
        lblOrderId.setText(orderModel.getNextOrderId());

        orderDate.setText(LocalDate.now().toString());
//        orderDate.setText(String.valueOf(LocalDate.now()));

        ArrayList<String> customerIds = customerModel.getAllCustomerIds();
        ObservableList<String> customerIdsTMS = FXCollections.observableArrayList();
        customerIdsTMS.addAll(customerIds);
        cmbCustomerId.setItems(customerIdsTMS);

        ArrayList<String> itemIds = itemModel.getAllItemIds();
        ObservableList<String> itemIdsTMS = FXCollections.observableArrayList();
        itemIdsTMS.addAll(itemIds);
        cmbItemId.setItems(itemIdsTMS);

//        cmbCustomerId.setOnAction(e->{
//            String selectedCusId = cmbCustomerId.getSelectionModel().getSelectedItem();
//            System.out.println(selectedCusId);
//        });

    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String selectedItemId = cmbItemId.getValue();
        if (selectedItemId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select item...!").show();
            return;
        }

        String itemName = lblItemName.getText();
        int cartQty = Integer.parseInt(txtAddToCartQty.getText());
        double unitPrice = Double.parseDouble(lblItemPrice.getText());
        double total = cartQty * unitPrice;
        Button button = new Button("Remove");

        int qtyOnHand = Integer.parseInt(lblItemQty.getText());
        if (qtyOnHand < cartQty) {
            new Alert(Alert.AlertType.WARNING, "Not enough items...!").show();
            return;
        }

        txtAddToCartQty.setText("");

        for (CartTM cartTM : cartTMS) {
            if (cartTM.getItemId().equals(selectedItemId)) {
                int newQty = cartTM.getCartQuantity() + cartQty;
                cartTM.setCartQuantity(newQty);

                cartTM.setTotal(unitPrice * newQty);

                tblCart.refresh();
                return;
            }
        }

        CartTM cartTM = new CartTM(
                selectedItemId,
                itemName,
                cartQty,
                unitPrice,
                total,
                button
        );

        button.setOnAction(actionEvent -> {
            cartTMS.remove(cartTM);
            tblCart.refresh();
        });

        cartTMS.add(cartTM);
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please add item to cart...!").show();
            return;
        }

        String orderId = lblOrderId.getText();
        String customerId = cmbCustomerId.getValue();
        Date dateOfOrder = Date.valueOf(orderDate.getText());

        if (customerId == null) {
            new Alert(Alert.AlertType.WARNING, "Please select customer for place order...!").show();
            return;
        }

        ArrayList<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();
        for (CartTM cartTM : cartTMS) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    orderId,
                    cartTM.getItemId(),
                    cartTM.getCartQuantity(),
                    cartTM.getUnitPrice()
            );
            orderDetailsDTOS.add(orderDetailsDTO);
        }

        OrderDTO orderDTO = new OrderDTO(orderId, customerId, dateOfOrder, orderDetailsDTOS);

       boolean isSaved =  orderModel.saveOrder(orderDTO);

       if (isSaved){
           new Alert(Alert.AlertType.INFORMATION,"Order saved...!").show();
           refreshPage();
       }else {
           new Alert(Alert.AlertType.ERROR,"Order fail...!").show();
       }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException {
        String selectedCusId = cmbCustomerId.getSelectionModel().getSelectedItem();
        CustomerDTO customerDTO = customerModel.findById(selectedCusId);

        if (customerDTO != null) {
            lblCustomerName.setText(customerDTO.getName());
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException {
        String selectedItemId = cmbItemId.getSelectionModel().getSelectedItem();
        ItemDTO itemDTO = itemModel.findById(selectedItemId);

        if (itemDTO != null) {
            lblItemName.setText(itemDTO.getItemName());
            lblItemQty.setText(String.valueOf(itemDTO.getQuantity()));
            lblItemPrice.setText(String.valueOf(itemDTO.getPrice()));
        }
    }
}

