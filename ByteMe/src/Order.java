import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeMap;

public class Order {
    private static int orderNumber = 0;
    private final int orderID;
    private TreeMap<MenuItem, Integer> itemList;
    private Customer customer;
    private LocalDate orderDate;
    private LocalTime orderPlaceTime;
    private LocalTime orderCompleteTime;
    private String status;
    private float orderTotal;
    private boolean isRefunded;
    private static ArrayList<Order> allOrders = new ArrayList<>();

    public Order(TreeMap<MenuItem, Integer> orderList, Customer customer, float orderTotal) {
        this.orderID = orderNumber;
        orderNumber++;
        this.customer = customer;
        this.itemList = orderList;
        this.orderDate = LocalDate.now();
        this.orderPlaceTime = LocalTime.now();
        this.orderTotal = orderTotal;
        this.isRefunded = false;
        allOrders.add(this);
    }

    public void displayOrderDetails() {
        //System.out.println();
    }

    public Order findOrderByID(int orderIDSearch) {
        for (Order order : allOrders) {
            if (order.orderID == orderIDSearch) {
                return order;
            }
        }
        return null;
    }

    public boolean getRefundStatus() {
        return this.isRefunded;
    }

    public void completeOrder() {
        this.orderCompleteTime = LocalTime.now();
    }
}
