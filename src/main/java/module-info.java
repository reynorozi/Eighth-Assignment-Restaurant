module ap.restaurant.restaurant {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens ap.restaurant.restaurant to javafx.fxml;
    exports ap.restaurant.restaurant;
}