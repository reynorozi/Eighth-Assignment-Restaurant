package ap.restaurant.restaurant;

public class OrderItem {
    private int id;
    private String name;
    private int quantity;
    private double price;

    public OrderItem(String name, double price) {
        this.name = name;
        this.price = price;
        this.quantity = 1;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }
}
