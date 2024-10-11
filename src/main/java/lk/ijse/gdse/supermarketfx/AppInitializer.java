package lk.ijse.gdse.supermarketfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lk.ijse.gdse.supermarketfx.dto.tm.CustomerTM;

import java.io.IOException;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/MainLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Supermarket FX");

        Image image = new Image(getClass().getResourceAsStream("/images/app_icon.png"));
        stage.getIcons().add(image);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}