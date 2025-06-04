package ap.restaurant.restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
//import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    @FXML
    private TextField loginusername;
    @FXML
    private PasswordField loginpassword;
    @FXML
    private Label loginresult;
    @FXML
    private Label userlogin;
    @FXML
    private Label passlogin;

    public void verify(ActionEvent event) {
        try {
            String username = loginusername.getText();
            String password = loginpassword.getText();
            String hashpass = Singup.hash(password);
            Connection con = JDBC.database();

            String queryusername = "SELECT user_name FROM userr WHERE user_name = ?";
            PreparedStatement ps1 = con.prepareStatement(queryusername);
            ps1.setString(1, username);
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                String querypassword = "SELECT password FROM userr WHERE user_name = ?";
                PreparedStatement ps2 = con.prepareStatement(querypassword);
                ps2.setString(1, username);
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    String hasspassdata = rs2.getString("password");

                    if (hashpass.equals(hasspassdata)) {
                        loginresult.setText("Login Success");


                        String queryId = "SELECT id FROM userr WHERE user_name = ?";
                        PreparedStatement psId = con.prepareStatement(queryId);
                        psId.setString(1, username);
                        ResultSet rsId = psId.executeQuery();
                        if (rsId.next()) {
                            Session.userId = rsId.getInt("id");
                            Session.username = username;
                        }


                        Menucontroller m = new Menucontroller();
                        m.displaymenu(event);
                    } else {
                        passlogin.setText("Invalid Password!");
                    }
                }
            } else {
                userlogin.setText("Invalid Username!");
            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
