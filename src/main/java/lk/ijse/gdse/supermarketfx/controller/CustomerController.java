package lk.ijse.gdse.supermarketfx.controller;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/4/2024 9:31 AM
 * Project: supermarketfx
 * --------------------------------------------
 **/

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.supermarketfx.dto.tm.CustomerTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    @FXML
    public Label lblCustomerId;
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtNic;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtPhone;
    @FXML
    private TableColumn<CustomerTM, String> colCustomerId;
    @FXML
    private TableColumn<CustomerTM, String> colEmail;
    @FXML
    private TableColumn<CustomerTM, String> colName;
    @FXML
    private TableColumn<CustomerTM, String> colNic;
    @FXML
    private TableColumn<CustomerTM, String> colPhone;
    @FXML
    private TableView<CustomerTM> tblCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set table column to cell factory value
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));


        // load data to table (50 - 64)
        CustomerTM customerTM = new CustomerTM("C001", "amal", "000000000000", "sample@gmail.com", "0777777777");
        CustomerTM customerTM2 = new CustomerTM("C002", "amal", "000000000000", "sample@gmail.com", "0777777777");

        ArrayList<CustomerTM> customertmsArray = new ArrayList<>();
        customertmsArray.add(customerTM);
        customertmsArray.add(customerTM2);

        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for (CustomerTM customer : customertmsArray) {
            customerTMS.add(customer);
        }

        tblCustomer.setItems(customerTMS);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent actionEvent) {
    }
}
