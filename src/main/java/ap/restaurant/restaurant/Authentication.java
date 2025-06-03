package ap.restaurant.restaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Authentication extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(Authentication.class.getResource("Authentication-Page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setWidth(460);
        stage.setHeight(585);
        stage.setTitle("Starbucks");
        stage.setScene(scene);
        stage.show();
       
    }



    public static void main(String[] args) {
        launch();
    }
}