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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.supermarketfx.dto.CustomerDTO;
import lk.ijse.gdse.supermarketfx.dto.tm.CustomerTM;
import lk.ijse.gdse.supermarketfx.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;
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

//        CustomerTM customerTM = new CustomerTM("C001", "amal", "000000000000", "sample@gmail.com", "0777777777");
//        CustomerTM customerTM2 = new CustomerTM("C002", "amal", "000000000000", "sample@gmail.com", "0777777777");
//
//        ArrayList<CustomerTM> customertmsArray = new ArrayList<>();
//        customertmsArray.add(customerTM);
//        customertmsArray.add(customerTM2);
//
//        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();
//
//        for (CustomerTM customer : customertmsArray) {
//            customerTMS.add(customer);
//        }
//
//        tblCustomer.setItems(customerTMS);

        // inside initialize method
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load customer id").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextCustomerId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtNic.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
    }

    CustomerModel customerModel = new CustomerModel();

    private void loadTableData() throws SQLException {
        ArrayList<CustomerDTO> customerDTOS = customerModel.getAllCustomers();

        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

//        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();
//        customerTMS.addAll(customerModel.getAllCustomer().stream().map(customerDTO->{
//            return new CustomerTM(
//                    customerDTO.getId(),
//                    customerDTO.getName(),
//                    customerDTO.getNic(),
//                    customerDTO.getEmail(),
//                    customerDTO.getPhone()
//            );
//        }).collect(Collectors.toList()));
//        tblCustomer.setItems(customerTMS);

        for (CustomerDTO customerDTO : customerDTOS) {
            CustomerTM customerTM = new CustomerTM(
                    customerDTO.getCustomerId(),
                    customerDTO.getName(),
                    customerDTO.getNic(),
                    customerDTO.getEmail(),
                    customerDTO.getPhone()
            );
            customerTMS.add(customerTM);
        }

        tblCustomer.setItems(customerTMS);
    }

    public void loadNextCustomerId() throws SQLException {
//        customerModel.helloCustomerModel();
        String nextCustomerId = customerModel.getNextCustomerId();
        lblCustomerId.setText(nextCustomerId);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        CustomerDTO customerDTO = new CustomerDTO(
                customerId,
                name,
                nic,
                email,
                phone
        );

        boolean isSaved = customerModel.saveCustomer(customerDTO);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Customer saved...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save customer...!").show();
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        CustomerTM customerTM = tblCustomer.getSelectionModel().getSelectedItem();
        if (customerTM != null) {
            lblCustomerId.setText(customerTM.getCustomerId());
            txtName.setText(customerTM.getName());
            txtNic.setText(customerTM.getNic());
            txtEmail.setText(customerTM.getEmail());
            txtPhone.setText(customerTM.getPhone());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String customerId = lblCustomerId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = customerModel.deleteCustomer(customerId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        CustomerDTO customerDTO = new CustomerDTO(
                customerId,
                name,
                nic,
                email,
                phone
        );

        boolean isUpdate = customerModel.updateCustomer(customerDTO);
        if (isUpdate) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Customer update...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update customer...!").show();
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }
}
