import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Order {
    private static int orderNumber = 0;
    private final int orderID;
    private TreeMap<MenuItem, Integer> itemList;
    private Customer customer;
    private LocalDate orderDate;
    private LocalTime orderPlaceTime;
    private LocalTime orderCompleteTime;
    private String orderStatus;
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
        this.orderStatus = "Placed";
        allOrders.add(this);
    }

    public void displayOrderDetails() {
        System.out.println("Customer name: " + customer.getName() + '\n' +
                           "Order total: " + orderTotal + '\n' +
                           "Date: " + orderDate + '\n' +
                           "Time: " + orderPlaceTime + '\n' +
                           "Delivered at: " + orderCompleteTime + '\n' +
                           "Items: " );
        displayItemList();
    }

    public void displayItemList() {
        if (itemList.isEmpty()) {
            return;
        }
        for (MenuItem item : itemList.keySet()) {
            System.out.println(item.getItemName() + " : " + itemList.get(item) + '\n');
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
                           "3. Delivered" + '\n');
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
                orderStatus = "Cooking";
                break;
            case 2:
                orderStatus = "Out for delivery";
                break;
            case 3:
                orderStatus = "Delivered";
                orderCompleteTime = LocalTime.now();
                break;
            default:
                break;
        }
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
}
