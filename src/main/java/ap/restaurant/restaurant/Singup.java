package ap.restaurant.restaurant;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;

public class Singup {

    @FXML private TextField username;
    @FXML private PasswordField pass;
    @FXML private Label passlabel;

    public void signup(ActionEvent event) {
        String usernamee = username.getText();
        String password = pass.getText();
        String hashpass = hash(password);

        boolean success = savedata(usernamee, hashpass);

        if (success) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("menu&orders.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean savedata(String username, String password) {
        try {
            Connection con = JDBC.database();
            String query0 = "SELECT user_name FROM userr WHERE user_name = ?";
            PreparedStatement ps = con.prepareStatement(query0);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                passlabel.setText("Username is already taken");
                passlabel.setStyle("-fx-text-fill: red;");
                return false;
            } else {
                String query = "INSERT INTO userr(user_name, password) VALUES(?, ?)";
                PreparedStatement ps1 = con.prepareStatement(query);
                ps1.setString(1, username);
                ps1.setString(2, password);
                ps1.executeUpdate();
                ps1.close();


                String getIdQuery = "SELECT id FROM userr WHERE user_name = ?";
                PreparedStatement ps2 = con.prepareStatement(getIdQuery);
                ps2.setString(1, username);
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    Session.userId = rs2.getInt("id");
                    Session.username = username;
                }
                ps2.close();

                passlabel.setText("Sign up successfully");
                passlabel.setStyle("-fx-text-fill: #056905;");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
