package ap.restaurant.restaurant;

import javafx.event.ActionEvent;

//import java.awt.*;
import java.security.MessageDigest;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//import java.awt.*;
import java.io.IOException;

public class HandleAuth {
    @FXML private TextField username;
    @FXML private PasswordField pass;

    @FXML
    protected void signupbuttonclick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene currentScene = new Scene(root);
        currentStage.setScene(currentScene);
        currentStage.show();
//        String usernamee = username.getText();
//        String password = pass.getText();
//        String hashpass = hash(password);
//        System.out.println("username field: " + username);
//        System.out.println("password field: " + password);
//
//
//        Singup.savedata(usernamee, hashpass);



    }

    @FXML
    protected void loginbuttonclick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene currentScene = new Scene(root);
        currentStage.setScene(currentScene);
        currentStage.show();

    }




}