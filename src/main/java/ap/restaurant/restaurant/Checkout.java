package ap.restaurant.restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Checkout {

    @FXML
    private GridPane checkout;

    @FXML
    private TextField thankid;

    @FXML
    private Button setOrderButton;
    @FXML
    private Button log;

    @FXML
    public void initialize() {
        int row = 0;
        for (OrderItem item : Menucontroller.orderList) {
            checkout.add(new Label(item.getName()), 0, row);
            checkout.add(new Label(String.valueOf(item.getQuantity())), 1, row);
            checkout.add(new Label(String.format("%.2f", item.getPrice())), 2, row);
            row++;
        }

        setOrderButton.setOnAction(e -> saveOrderToDatabase());
    }

    private void saveOrderToDatabase() {
        try {
            Connection conn = JDBC.database();

            int userId = Session.userId; // make sure Session.userId is correctly set
            double total = Menucontroller.orderList.stream().mapToDouble(OrderItem::getPrice).sum();

            // Insert into orderss table
            String orderSql = "INSERT INTO orderss (userid, totalprice) VALUES (?, ?) RETURNING id";
            PreparedStatement orderStmt = conn.prepareStatement(orderSql);
            orderStmt.setInt(1, userId);
            orderStmt.setDouble(2, total);
            ResultSet rs = orderStmt.executeQuery();

            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt("id");
            }
            orderStmt.close();

            // Insert into orderdetail table
            String detailSql = "INSERT INTO orderdetail (orderid, menuitemid, quantity, price) " +
                    "VALUES (?, (SELECT id FROM menuitem WHERE text = ?), ?, ?)";
            PreparedStatement detailStmt = conn.prepareStatement(detailSql);

            for (OrderItem item : Menucontroller.orderList) {
                detailStmt.setInt(1, orderId);
                detailStmt.setString(2, item.getName());
                detailStmt.setInt(3, item.getQuantity());
                detailStmt.setDouble(4, item.getPrice());
                detailStmt.executeUpdate();
            }
            detailStmt.close();

            Menucontroller.orderList.clear();

        } catch (Exception ex) {
            ex.printStackTrace();
            thankid.setText("Thanks for your purchase :)");
//            thankid.setText("An error occurred while saving your order.");
        }
    }

    @FXML
    protected void backtoauth(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Authentication-Page.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();



    }

}
