package ap.restaurant.restaurant;

import  javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Menucontroller {

    @FXML
    private GridPane orderGrid;
    @FXML
    private TextField total_price;
    public static List<OrderItem> orderList = new ArrayList<>();
    @FXML
    protected void displaymenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Menucontroller.class.getResource("menu&orders.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene currentScene = new Scene(root);
        currentStage.setScene(currentScene);
        currentStage.show();

    }
    private int currentRow = 0;

    private  double totalPrice = 0.0;

    @FXML
    public void addItemToGrid(ActionEvent event) {
        try {
            Button clickedButton = (Button) event.getSource();
            String itemId = clickedButton.getId();

            Connection con = JDBC.database();
            String query = "SELECT text, price FROM menuitem WHERE text = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, itemId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String itemName = rs.getString("text");
                double price = rs.getDouble("price");


                boolean found = false;
                for (OrderItem item : orderList) {
                    if (item.getName().equals(itemName)) {
                        item.incrementQuantity();
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    orderList.add(new OrderItem(itemName, price));
                }


                orderGrid.getChildren().clear();
                currentRow = 0;
                totalPrice = 0.0;

                for (OrderItem item : orderList) {
                    Label nameLabel = new Label(item.getName());
                    Label priceLabel = new Label(String.format("%.2f", item.getPrice()));
                    Button deleteBtn = new Button("✖");

                    int rowIndex = currentRow;
                    orderGrid.add(nameLabel, 0, rowIndex);
                    orderGrid.add(priceLabel, 1, rowIndex);
                    orderGrid.add(deleteBtn, 2, rowIndex);

                    deleteBtn.setOnAction(e -> {
                        orderList.remove(item);
                        refreshOrderGrid();
                    });

                    totalPrice += item.getPrice();
                    currentRow++;
                }

                total_price.setText(String.format("%.2f", totalPrice));
            }

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshOrderGrid() {
        orderGrid.getChildren().clear();
        currentRow = 0;
        totalPrice = 0.0;

        for (OrderItem item : orderList) {
            Label nameLabel = new Label(item.getName());
            Label priceLabel = new Label(String.format("%.2f", item.getPrice()));
            Button deleteBtn = new Button("✖");

            int rowIndex = currentRow;
            orderGrid.add(nameLabel, 0, rowIndex);
            orderGrid.add(priceLabel, 1, rowIndex);
            orderGrid.add(deleteBtn, 2, rowIndex);

            deleteBtn.setOnAction(e -> {
                orderList.remove(item);
                refreshOrderGrid();
            });

            totalPrice += item.getPrice();
            currentRow++;
        }

        total_price.setText(String.format("%.2f", totalPrice));
    }

    @FXML
    private void goToCheckoutPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Checkout.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene currentScene = new Scene(root);
        currentStage.setScene(currentScene);
        currentStage.show();
    }

}
