package ap.restaurant.restaurant;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Singup {

    @FXML
    private TextField username;
    @FXML private PasswordField pass;

    public  void signup (ActionEvent event) {

        String usernamee = username.getText();
        String password = pass.getText();
        String hashpass = hash(password);
        System.out.println("username field: " + username);
        System.out.println("password field: " + password);
        savedata(usernamee,hashpass);


    }

    public static void savedata(String username, String password) {

        try {
            System.out.println("lskejd;lkjs;lgdkj;dlgj");
            Connection con = JDBC.database();
            String query = "INSERT INTO userr(user_name , password) VALUES(?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            ps.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
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
