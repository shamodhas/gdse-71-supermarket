module lk.ijse.gdse.supermarketfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens lk.ijse.gdse.supermarketfx.controller to javafx.fxml;
    exports lk.ijse.gdse.supermarketfx;
}