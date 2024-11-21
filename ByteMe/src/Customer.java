import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Customer {
    private String username;
    private String password;
    private String name;
    private Scanner sc;
    private Cart currentCart;
    private boolean vip;
    private ArrayList<Order> allOrders = new ArrayList<>();
    private Set<MenuItem> orderedMenuItems = new HashSet<>();
    private ArrayList<Review> customerReviews = new ArrayList<>();
    public static ArrayList<Customer> allCustomers = new ArrayList<>();

    public Customer(String username, String password, String name) throws IOException {
        this.username = username;
        this.password = password;
        this.name = name;
        this.vip = false;
        this.currentCart = new Cart(this);
        try {
            this.customerData();
            allCustomers.add(this);
        }
        catch (Exception e) {
            System.out.println("Error in signing up. Please try again.");
        }
    }

    private void customerData() throws IOException {
            FileWriter fw = new FileWriter("customerData.txt", true);
            fw.write(username + "--" + password + "--" + name + '\n');
            fw.close();
    }

    static {
        try {
            Customer newCustomer = new Customer("garvit","garvit123", "Garvit");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Customer newCustomer2 = new Customer("shivansh","shivansh123", "Shivansh");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Customer newCustomer3 = new Customer("parth", "parth123", "Parth");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initialiseCustomers() {}

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public static void customerSignup(Scanner sc) throws IOException {
        System.out.println("Enter username: ");
        String username = sc.nextLine();
        System.out.println("Enter password: ");
        String password = sc.nextLine();
        System.out.println("Enter name: ");
        String name = sc.nextLine();

        if (findCustomerByUsername(username) == null) {
            new Customer(username, password, name);
            System.out.println("Signup successful with username: " + username);
            return;
        }

        System.out.println("Username already exists.");
    }

    public static boolean customerLogin(String username, String password, Scanner sc, int Test) throws IOException {
        /*Customer customerToLogin = findCustomerByUsername(username);
        if (customerToLogin == null) {
            System.out.println("Customer with username: " + username + " does not exist.");
            return;
        }

        if ((username.equals(customerToLogin.getUsername())) && (password.equals(customerToLogin.getPassword()))) {
            System.out.println("Login successful.");
            customerToLogin.displayCustomerMenu(sc);
        }
        else {
            System.out.println("Login unsuccessful. Invalid credentials.");
            return;
        }*/
        try {
            List<String> allCustomersData = Files.readAllLines(Paths.get("customerData.txt"));

            for (String customerDataTemp : allCustomersData) {
                String[] customerData = customerDataTemp.split("--");
                if (customerData[0].equals(username)) {
                    if (customerData[1].equals(password)) {
                        Customer customerToLogin = findCustomerByUsername(username);
                        System.out.println("Login successful.");
                        customerToLogin.displayCustomerMenu(sc, Test);
                        return true;
                    }
                    else {
                        System.out.println("Login unsuccessful. Invalid credentials.");
                        return false;
                    }
                }
            }

            System.out.println("Username: " + username + "not found.");
            return false;
        }
        catch (IOException e) {
            System.out.println("Unable to login. Please try again.");
            return false;
        }
    }

    private void displayCustomerMenu(Scanner sc, int Test) throws IOException {
        if (Test == 1)  return;
        while (true) {
            int choice = -1;
            System.out.println("Choose option:" + '\n' +
                    "1. Menu view" + '\n' +
                    "2. Cart View" + '\n' +
                    "3. Order View" + '\n' +
                    "4. Become VIP" + '\n' +
                    "5. Review" + '\n' +
                    "6. Logout");

            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    Menu.displayMenu(sc);
                    break;
                case 2:
                    displayCartMenu(sc);
                    break;
                case 3:
                    displayOrderMenu(sc);
                    break;
                case 4:
                    if (vip) {
                        System.out.println("You're already a VIP customer.");
                        break;
                    }
                    if (payment(sc)) {
                        vip = true;
                        System.out.println("Congratulations on becoming a VIP customer.");
                    }
                    else {
                        System.out.println("Payment unsuccessful. VIP status not updated.");
                    }
                    break;
                case 5:
                    review(sc);
                    break;
                case 6:
                    System.out.println("Successfully logged out.");
                    return;
                default:
                    System.out.println("Invalid input. Please enter correct number.");
                    break;
            }
        }
    }

    public static Customer findCustomerByUsername(String username) {
        for (Customer customer : allCustomers) {
            if (customer.getUsername().equals(username)) {
                return customer;
            }
        }
        return null;
    }

    private void displayCartMenu(Scanner sc) {
        while (true) {
            int choice = -1;
            System.out.println("Choose cart operation: " + '\n' +
                               "1. View Cart" + '\n' +
                               "2. Add items" + '\n' +
                               "3. Modify quantities" + '\n' +
                               "4. Remove items" + '\n' +
                               "5. View total" + '\n' +
                               "6. Clear cart" + '\n' +
                               "7. Place order" + '\n' +
                               "8. Go back");
            while(!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    currentCart.displayCart();
                    break;
                case 2:
                    addItemToCart(sc);
                    break;
                case 3:
                    modifyQuantity(sc);
                    break;
                case 4:
                    removeItem(sc);
                    break;
                case 5:
                    System.out.println("The total is: " + currentCart.getTotal());
                    break;
                case 6:
                    currentCart.clearCart();
                    System.out.println("Cart cleared successfully.");
                    break;
                case 7:
                    if (currentCart.isCartEmpty()) {
                        System.out.println("Cart is empty.");
                        return;
                    }
                    System.out.println("Kindly enter special requests if any: ");
                    String specialReq = sc.nextLine();
                    System.out.println("Enter address: ");
                    String address = sc.nextLine();
                    if (payment(sc)) {
                        for (MenuItem item : currentCart.getCartList().keySet()) {
                            orderedMenuItems.add(item);
                        }
                        currentCart.placeOrder(specialReq, address);
                        System.out.println("Order placed successfully.");
                    }
                    else {
                        System.out.println("Payment failed. Order not placed.");
                    }
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    public void addOrder(Order orderToAdd) {
        allOrders.add(orderToAdd);
    }

    private void addItemToCart(Scanner sc) {
        while (true) {
            int option = -1;
            System.out.println("Find item by: " + '\n' +
                    "1. Item Code" + '\n' +
                    "2. Item Name" + '\n' +
                    "3. Go back");

            while(!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    System.out.println("Enter item code: ");
                    String inputCode = sc.nextLine();
                    MenuItem itemToAdd = MenuItem.findItemByCode(inputCode);

                    if (itemToAdd == null) {
                        System.out.println("Item: " + inputCode + " does not exist.");
                        return;
                    }

                    /*if (!itemToAdd.getItemAvailability()) {
                        System.out.println("Item: " + itemToAdd.getItemName() + " is not available.");
                        return;
                    }*/

                    int quantity = 0;
                    System.out.println("Enter quantity: ");

                    while (!sc.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a number.");
                        sc.nextLine();
                    }
                    quantity = sc.nextInt();
                    sc.nextLine();

                    while (quantity <= 0) {
                        System.out.println("Quantity must be positive. Enter quantity: ");
                        while (!sc.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a number.");
                            sc.nextLine();
                        }
                        quantity = sc.nextInt();
                        sc.nextLine();
                    }

                    currentCart.addItem(itemToAdd, quantity);

                    break;
                case 2:
                    System.out.println("Enter item name: ");
                    String inputName = sc.nextLine();
                    MenuItem itemToAdd1 = MenuItem.findItemByName(inputName);

                    if (itemToAdd1 == null) {
                        System.out.println("Item: " + inputName + " does not exist.");
                        return;
                    }

                    int quantity1 = 0;
                    System.out.println("Enter quantity: ");

                    while (!sc.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a number.");
                        sc.nextLine();
                    }
                    quantity1 = sc.nextInt();
                    sc.nextLine();

                    while (quantity1 <= 0) {
                        System.out.println("Quantity must be positive. Enter quantity: ");
                        while (!sc.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a number.");
                            sc.nextLine();
                        }
                        quantity1 = sc.nextInt();
                        sc.nextLine();
                    }

                    currentCart.addItem(itemToAdd1, quantity1);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void modifyQuantity(Scanner sc) {
        if (currentCart.isCartEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        currentCart.displayCart();

        System.out.println("Enter item name to modify: ");
        String inputName = sc.nextLine();

        MenuItem itemToModify = null;
        for (MenuItem item : currentCart.getCartList().keySet()) {
            if (item.getItemName().equals(inputName)) {
                itemToModify = item;
                break;
            }
        }

        if (itemToModify == null) {
            System.out.println("Item: " + inputName + " is not in cart.");
            return;
        }

        int quantity = 0;
        System.out.println("Enter quantity: ");

        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            sc.nextLine();
        }
        quantity = sc.nextInt();
        sc.nextLine();

        while (quantity <= 0) {
            System.out.println("Quantity must be positive. Enter quantity: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
            quantity = sc.nextInt();
            sc.nextLine();
        }

        currentCart.modifyQuantity(itemToModify, quantity);
    }

    private void removeItem(Scanner sc) {
        if (currentCart.isCartEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        currentCart.displayCart();

        System.out.println("Enter item name to remove: ");
        String inputName = sc.nextLine();

        MenuItem itemToRemove = null;
        for (MenuItem item : currentCart.getCartList().keySet()) {
            if (item.getItemName().equals(inputName)) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove == null) {
            System.out.println("Item: " + inputName + " is not in cart.");
            return;
        }

        currentCart.removeItem(itemToRemove);
    }

    private void displayOrderMenu(Scanner sc) throws IOException {
        while (true) {
            int choice = -1;
            System.out.println("Choose order operation: " + '\n' +
                               "1. Order status" + '\n' +
                               "2. Cancel order" + '\n' +
                               "3. Order History" + '\n' +
                               "4. Go back");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    orderStatusCustomer();
                    break;
                case 2:
                    cancelOrder(sc);
                    break;
                case 3:
                    /*if (allOrders.isEmpty()) {
                        System.out.println("No order history.");
                        return;
                    }
                    for (Order order : allOrders) {
                        order.displayOrderDetails();
                    }*/
                    Order.getOrdersFromFile(this);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void orderStatusCustomer() {
        if (allOrders.isEmpty()) {
            System.out.println("No order history.");
            return;
        }

        int count = 0;
        for (Order order : allOrders) {
            if (!(order.getOrderStatus().equals("Delivered"))) {
                System.out.println("Order ID: " + order.getOrderID() + " Status: " + order.getOrderStatus());
                count++;
            }
        }

        if (count == 0) {
            System.out.println("All orders are delivered. Kindly check order history.");
        }
    }

    private void cancelOrder(Scanner sc) throws IOException {
        if (allOrders.isEmpty()) {
            System.out.println("No order history.");
            return;
        }

        int orderID = -1;
        System.out.println("Enter order ID: ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            sc.nextLine();
        }
        orderID = sc.nextInt();
        sc.nextLine();

        Order orderToCancel = null;
        for (Order order : allOrders) {
            if (order.getOrderID() == orderID) {
                orderToCancel = order;
                break;
            }
        }

        if (orderToCancel == null) {
            System.out.println("Order with ID: " + orderID + " does not exist.");
            return;
        }

        if (orderToCancel.getOrderStatus().equals("Delivered")) {
            System.out.println("Order with ID: " + orderID + " is already delivered.");
            return;
        }

        if (!orderToCancel.getOrderStatus().equals("Placed")) {
            System.out.println("Order with ID: " + orderID + " is cooking and cannot be cancelled.");
            return;
        }

        orderToCancel.cancelOrder();
        System.out.println("Order cancelled successfully. Your amount will be refunded if deducted.");
    }

    private boolean payment(Scanner sc) {
        while (true) {
            int choice = -1;
            System.out.println("Choose payment method: " + '\n' +
                               "1. UPI" + '\n' +
                               "2. Card" + '\n' +
                               "3. Go back");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1, 2:
                    System.out.println("Payment successful.");
                    return true;
                case 3:
                    return false;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    public boolean getVIPStatus() {
        return this.vip;
    }

    private void review(Scanner sc) {
        while (true) {
            int choice = -1;
            System.out.println("Choose option: " + '\n' +
                               "1. View item review" + '\n' +
                               "2. View submitted reviews" + '\n' +
                               "3. Add review" + '\n' +
                               "4. Go back");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewReview(sc);
                    break;
                case 2:
                    viewSubmittedReviews();
                    break;
                case 3:
                    addReview(sc);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void viewReview(Scanner sc) {
        System.out.println("Enter item code: ");
        String itemCodeInput = sc.nextLine();
        MenuItem item = MenuItem.findItemByCode(itemCodeInput);

        if (item == null) {
            System.out.println("Item: " + itemCodeInput + " does not exist.");
            return;
        }

        item.displayItemReviews();
    }

    private void viewSubmittedReviews() {
        if (customerReviews.isEmpty()) {
            System.out.println("You've not reviewed any items.");
            return;
        }

        for (Review review : customerReviews) {
            System.out.println("Item name: " + review.getItem().getItemName() + '\n' +
                               "Review: " + review.getReview() + '\n');
        }
    }

    private void addReview(Scanner sc) {
        if (orderedMenuItems.isEmpty()) {
            System.out.println("No items ordered yet.");
            return;
        }

        for (MenuItem item : orderedMenuItems) {
            System.out.println("Item code: " + item.getItemCode() + " Item name: " + item.getItemName());
        }

        System.out.println("Enter the item code: ");
        String itemCodeInput = sc.nextLine();

        MenuItem itemToReview = null;
        for (MenuItem item : orderedMenuItems) {
            if (item.getItemCode().equals(itemCodeInput)) {
                itemToReview = item;
                break;
            }
        }

        if (itemToReview == null) {
            System.out.println("Item: " + itemCodeInput + " is not ordered by you.");
            return;
        }

        System.out.println("Enter your review: ");
        String reviewInput = sc.nextLine();
        Review newReview = new Review(this, reviewInput, itemToReview);
        customerReviews.add(newReview);
        itemToReview.addItemReview(newReview);
        System.out.println("Review submitted: " + reviewInput);
    }
}
