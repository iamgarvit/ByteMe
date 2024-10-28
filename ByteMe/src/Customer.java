import java.util.ArrayList;
import java.util.Scanner;

public class Customer {
    private String username;
    private String password;
    private String name;
    private Scanner sc;
    private Cart currentCart;
    private ArrayList<Order> allOrders = new ArrayList<>();
    public static ArrayList<Customer> allCustomers = new ArrayList<>();

    public Customer(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.currentCart = new Cart(this);
        allCustomers.add(this);
    }

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

    public static void customerSignup(Scanner sc) {
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

    public static void customerLogin(String username, String password, Scanner sc) {
        Customer customerToLogin = findCustomerByUsername(username);
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
        }
    }

    private void displayCustomerMenu(Scanner sc) {
        while (true) {
            int choice = -1;
            System.out.println("Choose option:" + '\n' +
                    "1. Menu view" + '\n' +
                    "2. Cart View" + '\n' +
                    "3. Order View" + '\n' +
                    "4. Logout");

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
                               "8. Go back" + '\n');
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
                    currentCart.placeOrder(specialReq);
                    System.out.println("Order placed successfully. Use cash or UPI on delivery.");
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
                    "3. Go back" + '\n');

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

    private void displayOrderMenu(Scanner sc) {
        while (true) {
            int choice = -1;
            System.out.println("Choose order operation: " + '\n' +
                               "1. Order status" + '\n' +
                               "2. Cancel order" + '\n' +
                               "3. Order History" + '\n' +
                               "4. Go back" + '\n');
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
                    if (allOrders.isEmpty()) {
                        System.out.println("No order history.");
                        return;
                    }
                    for (Order order : allOrders) {
                        order.displayOrderDetails();
                    }
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

    private void cancelOrder(Scanner sc) {
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
            System.out.println("Order with ID: " + " is cooking and cannot be cancelled.");
            return;
        }

        orderToCancel.cancelOrder();
        System.out.println("Order cancelled successfully. Your amount will be refunded if deducted.");
    }
}
