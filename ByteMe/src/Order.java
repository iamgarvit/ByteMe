import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeMap;

public class Order {
    private static int orderNumber = 0;
    private final int orderID;
    private String specialRequest;
    private boolean specialRequestAccepted;
    private TreeMap<MenuItem, Integer> itemList;
    private Customer customer;
    private LocalDate orderDate;
    private LocalTime orderPlaceTime;
    private LocalTime orderCompleteTime;
    private String orderStatus;
    private float orderTotal;
    private boolean isRefunded;
    private static ArrayList<Order> allOrders = new ArrayList<>();

    public Order(TreeMap<MenuItem, Integer> orderList, Customer customer, float orderTotal, String specialRequest) {
        this.orderID = orderNumber;
        orderNumber++;
        this.customer = customer;
        this.itemList = new TreeMap<>(orderList);
        this.orderDate = LocalDate.now();
        this.orderPlaceTime = LocalTime.now();
        this.orderTotal = orderTotal;
        this.isRefunded = false;
        this.orderStatus = "Placed";
        this.specialRequest = specialRequest;
        this.specialRequestAccepted = false;
        allOrders.add(this);
    }

    public void displayOrderDetails() {
        System.out.println("Customer name: " + customer.getName() + '\n' +
                           "Order ID:" + orderID + '\n' +
                           "Order total: " + orderTotal + '\n' +
                           "Date: " + orderDate + '\n' +
                           "Time: " + orderPlaceTime + '\n' +
                           "Delivered at: " + orderCompleteTime + '\n' +
                           "Special request: " + specialRequest + '\n' +
                           "Special request accepted: " + specialRequestAccepted + '\n' +
                           "Items: " );
        displayItemList();
    }

    private void displayItemList() {
        if (itemList.isEmpty()) {
            System.out.println("No items found");
            return;
        }
        for (MenuItem item : itemList.keySet()) {
            System.out.println(item.getItemName() + " : " + itemList.get(item));
        }
    }

    public Order findOrderByID(int orderIDSearch) {
        for (Order order : allOrders) {
            if (order.orderID == orderIDSearch) {
                return order;
            }
        }
        return null;
    }

    public void updateOrderStatus(Scanner sc) {
        int choice = -1;
        System.out.println("Choose status: " + '\n' +
                           "1. Cooking" + '\n' +
                           "2. Out for delivery" + '\n' +
                           "3. Delivered");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            sc.nextLine();
        }
        choice = sc.nextInt();
        sc.nextLine();

        while (!((choice >= 1) && choice <=3)) {
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
            System.out.println("Invalid input. Please enter a number b/w 1 and 3.");
            choice = sc.nextInt();
            sc.nextLine();
        }

        switch (choice) {
            case 1:
                this.orderStatus = "Cooking";
                break;
            case 2:
                this.orderStatus = "Out for delivery";
                break;
            case 3:
                this.orderStatus = "Delivered";
                completeOrder();
                break;
            default:
                break;
        }
    }

    public void cancelOrder() {
        this.orderStatus = "Canceled";
        this.orderCompleteTime = LocalTime.now();
        this.isRefunded = true;
    }

    public boolean getRefundStatus() {
        return this.isRefunded;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void completeOrder() {
        this.orderCompleteTime = LocalTime.now();
    }

    public int getOrderID() {
        return this.orderID;
    }

    public float getOrderTotal() {
        return this.orderTotal;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public void setSpecialRequestAccepted(boolean accepted) {
        this.specialRequestAccepted = accepted;
    }

    public String getSpecialRequest() {
        return this.specialRequest;
    }
}
