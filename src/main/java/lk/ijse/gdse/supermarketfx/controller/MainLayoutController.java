package lk.ijse.gdse.supermarketfx.controller;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/4/2024 9:18 AM
 * Project: supermarketfx
 * --------------------------------------------
 **/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainLayoutController {
    @FXML
    private AnchorPane content;
    @FXML
    void navigateCustomerOnAction(ActionEvent event) {
        navigateTo("/view/CustomerView.fxml");
    }
    @FXML
    void navigateItemOnAction(ActionEvent event) {
        navigateTo("/view/ItemView.fxml");
    }
    @FXML
    void navigateOrdersOnAction(ActionEvent event) {
        navigateTo("/view/OrdersView.fxml");
    }
    private void navigateTo(String fxmlPath) {
        try {
            content.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            content.getChildren().add(load);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui !").show();
        }
    }
}