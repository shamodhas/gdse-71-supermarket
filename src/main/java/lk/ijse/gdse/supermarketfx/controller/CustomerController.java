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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse.supermarketfx.db.DBConnection;
import lk.ijse.gdse.supermarketfx.dto.CustomerDTO;
import lk.ijse.gdse.supermarketfx.dto.tm.CustomerTM;
import lk.ijse.gdse.supermarketfx.model.CustomerModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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

//        [A-Za-z ]+
//        (1)
//        Pattern namePattern = Pattern.compile("^[A-Za-z ]+$");
//        boolean isValidName = namePattern.matcher(name).matches();
//        System.out.println("method 1 : "+isValidName);

//        (2)
//        System.out.println("method 2 : "+name.matches("^[A-Za-z ]+$"));

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (!isValidNic) {
            txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
//            return;
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidName && isValidNic && isValidEmail && isValidPhone) {
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

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (!isValidNic) {
            txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
//            return;
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidNic && isValidEmail && isValidPhone) {
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
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    public void generateAllCustomerReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/customer_report.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    null,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
//           e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }

    @FXML
    public void orderReportOnAction(ActionEvent actionEvent) {
        CustomerTM customerTM = tblCustomer.getSelectionModel().getSelectedItem();

        if (customerTM == null) {
            return;
        }

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/CustomerOrderDetailsReport.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("P_Date", LocalDate.now().toString());
            parameters.put("P_Customer_Id", customerTM.getCustomerId());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
//           e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }

    @FXML
    public void openSendMailModel(ActionEvent actionEvent) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select customer..!");
            return;
        }

        try {
            // Load the mail dialog from FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SendMailView.fxml"));
            Parent load = loader.load();

            SendMailController sendMailController = loader.getController();

            String email = selectedItem.getEmail();
            sendMailController.setCustomerEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send email");

            // Set window as modal
            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }
    }
}






