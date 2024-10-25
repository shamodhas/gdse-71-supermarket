package lk.ijse.gdse.supermarketfx;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {


    public static void main(String[] args) {
        launch(); // Launch the JavaFX application
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Load and display the loading view
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoadingView.fxml"))));
        stage.show();

        // Create a background task to load the main scene
        Task<Scene> loadingTask = new Task<>() {
            @Override
            protected Scene call() throws Exception {
                // Load the main layout from FXML
                FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/MainLayout.fxml"));
                return new Scene(fxmlLoader.load());
            }
        };

        // What to do when loading is successful
        loadingTask.setOnSucceeded(event -> {
            Scene value = loadingTask.getValue();

            stage.setTitle("Supermarket FX");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app_icon.png")));
            stage.setMaximized(true);

            // Switch to the main scene
            stage.setScene(value);
        });

        // What to do in case of loading failure (optional)
        loadingTask.setOnFailed(event -> {
            System.err.println("Failed to load the main layout."); // Print error message
        });


        // Start the task in a separate thread
        new Thread(loadingTask).start();

//        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/MainLayout.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Supermarket FX");
//
//        Image image = new Image(getClass().getResourceAsStream("/images/app_icon.png"));
//        stage.getIcons().add(image);
//
//        stage.setScene(scene);
//        stage.show();
    }
}