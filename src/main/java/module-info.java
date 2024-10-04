module lk.ijse.gdse.supermarketfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens lk.ijse.gdse.supermarketfx.dto.tm to javafx.base;
    opens lk.ijse.gdse.supermarketfx.controller to javafx.fxml;
    exports lk.ijse.gdse.supermarketfx;
}