import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Order {
    private static int orderNumber = 1;
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
    private boolean vip;
    private String address;
    private static TreeMap<Order, Integer> allOrders = new TreeMap<>(Comparator.comparing(Order::getOrderID));

    public Order(TreeMap<MenuItem, Integer> orderList, Customer customer, float orderTotal, String specialRequest, boolean vip, String address) {
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
        this.vip = vip;
        this.address = address;
        allOrders.put(this, this.orderID);
    }

    public void displayOrderDetails() {
        System.out.println("Customer name: " + customer.getName() + '\n' +
                           "VIP: " + customer.getVIPStatus() + '\n' +
                           "Order ID: " + orderID + '\n' +
                           "Order total: " + orderTotal + '\n' +
                           "Date: " + orderDate + '\n' +
                           "Time: " + orderPlaceTime + '\n' +
                           "Status: " + orderStatus + '\n' +
                           "Delivered at: " + orderCompleteTime + '\n' +
                           "Special request: " + specialRequest + '\n' +
                           "Special request accepted: " + specialRequestAccepted + '\n' +
                           "Address: " + address + '\n' +
                           "Items: " );
        displayItemList();
        System.out.println();
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
        for (Order order : allOrders.keySet()) {
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
            System.out.println("Invalid input. Please enter a number b/w 1 and 3.");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
            choice = sc.nextInt();
            sc.nextLine();
        }

        switch (choice) {
            case 1:
                this.orderStatus = "Cooking";
                System.out.println("Status updated: Cooking");
                break;
            case 2:
                this.orderStatus = "Out for delivery";
                System.out.println("Status updated: Out for delivery");
                break;
            case 3:
                this.orderStatus = "Delivered";
                System.out.println("Status updated: Delivered");
                completeOrder();
                break;
            default:
                break;
        }
    }

    public void cancelOrder() {
        this.orderStatus = "Cancelled";
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

    public boolean getVIPStatus() {
        return this.vip;
    }

    public String getSpecialRequest() {
        return this.specialRequest;
    }

    public String getAddress() {
        return this.address;
    }

    public Set<MenuItem> getItems() {
        return this.itemList.keySet();
    }
}
