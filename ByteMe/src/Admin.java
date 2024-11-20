import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class Admin {
    private String username;
    private String password;
    private String name;
    private static TreeMap<Order, Integer> allOrdersAdmin = new TreeMap<>(Comparator.comparing(Order::getOrderID));
    private static ArrayList<Admin> allAdmin = new ArrayList<>();

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Admin(String username, String password, String name) throws IOException {
        this.username = username;
        this.password = password;
        this.name = name;
        this.adminData();
        allAdmin.add(this);
    }

    public static void AdminSignUp(Scanner sc) throws IOException {
        System.out.println("Enter username: ");
        String username = sc.nextLine();
        System.out.println("Enter password: ");
        String password = sc.nextLine();
        System.out.println("Enter name: ");
        String name = sc.nextLine();

        if (findAdminByUsername(username) == null) {
            new Admin(username, password, name);
            return;
        }
        else {
            System.out.println("Admin already exists with username: " + username);
        }
    }

    private void adminData() throws IOException{
        try {
            FileWriter fw = new FileWriter("adminData.txt", true);
            fw.write(username + "--" + password + "--" + name);
            fw.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void adminLogin(String username, String password, Scanner sc) throws IOException {
        /*Admin adminToLogin = findAdminByUsername(username);
        if (adminToLogin == null) {
            System.out.println("Admin with username: " + username + " does not exists.");
            return;
        }

        if ((username.equals(adminToLogin.getUsername())) && (password.equals(adminToLogin.getPassword()))) {
            System.out.println("Successfully logged in as: " + username);
            adminToLogin.displayAdminMenu(sc);
        }
        else {
            System.out.println("Login failed. Invalid credentials.");
            return;
        }*/
        try {
            List<String> allAdminData = Files.readAllLines(Paths.get("adminData.txt"));

            for (String adminDataTemp : allAdminData) {
                String[] adminData = adminDataTemp.split("--");
                if (adminData[0].equals(username)) {
                    if (adminData[1].equals(password)) {
                        Admin adminTologin = findAdminByUsername(username);
                        System.out.println("Login successful.");
                        adminTologin.displayAdminMenu(sc);
                        return;
                    }
                    else {
                        System.out.println("Login unsuccessful. Invalid credentials.");
                        return;
                    }
                }
            }

            System.out.println("Username: " + username + "not found.");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            Admin newAdmin = new Admin("admin", "admin123", "Default Admin");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initialiseAdmin() {}

    private void displayAdminMenu(Scanner sc) {
        while (true) {
            int choice = -1;
            System.out.println("Choose option: " + '\n' +
                    "1. Menu Management" + '\n' +
                    "2. Order Management" + '\n' +
                    "3. Generate today's report" + '\n' +
                    "4. Logout");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    manageMenu(sc);
                    break;
                case 2:
                    manageOrder(sc);
                    break;
                case 3:
                    reportGeneration(sc);
                    break;
                case 4:
                    System.out.println("Logging out.");
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void manageMenu(Scanner sc) {
        while (true) {
            int option = -1;
            System.out.println("Choose option: " + '\n' +
                               "1. View current menu" + '\n' +
                               "2. Add new item" + '\n' +
                               "3. Update existing item" + '\n' +
                               "4. Remove item" + '\n' +
                               "5. Modify Price" + '\n' +
                               "6. Update Availability" + '\n' +
                               "7. Go back");
            while(!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    Menu.displayFullMenu();
                    break;
                case 2:
                    addMenuItem(sc);
                    break;
                case 3:
                    updateMenuItem(sc);
                    break;
                case 4:
                    removeMenuItem(sc);
                    break;
                case 5:
                    modifyItemPrice(sc);
                    break;
                case 6:
                    updateItemAvailability(sc);
                    break;
                case 7:
                    System.out.println("Returning to previous screen.");
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void addMenuItem(Scanner sc) {
        System.out.println("Enter item code: ");
        String itemCode = sc.nextLine();

        for (MenuItem item : MenuItem.getAllItems()) {
            if (itemCode.equals(item.getItemCode())) {
                System.out.println("Item " + item.getItemCode() + " already exists.");
                return;
            }
        }

        System.out.println("Enter item name: ");
        String itemName = sc.nextLine();

        System.out.println("Enter item price: ");
        while (!sc.hasNextFloat()) {
            System.out.println("Price must be a float value.");
            sc.nextLine();
        }
        float itemPrice = sc.nextFloat();

        System.out.println("Is the item available? \n1. Yes \n2. No");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Enter a number.");
            sc.nextLine();
        }
        int availability = sc.nextInt();
        sc.nextLine();
        boolean itemAvailability;

        while ((availability != 1) && (availability != 2)) {
            System.out.println("Invalid input. Enter 1 for Yes and 2 for No.");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid Input. Enter a number.");
                sc.nextLine();
            }
            availability = sc.nextInt();
            sc.nextLine();
        }

        itemAvailability = (availability == 1);

        System.out.println("Choose category: " + '\n' +
                           "1. Snack" + '\n' +
                           "2. Meal" + '\n' +
                           "3. Beverage");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Enter a number.");
            sc.nextLine();
        }
        int category = sc.nextInt();
        sc.nextLine();
        while (!((category <= 3) && (category >= 1))) {
            System.out.println("Input must be value b/w 1 and 3. Re enter input: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Input must be an integer.");
                sc.nextLine();
            }
            category = sc.nextInt();
            sc.nextLine();
        }
        String itemCategory;
        if (category == 1) {
            itemCategory = "Snack";
        }
        else if (category == 2) {
            itemCategory = "Meal";
        }
        else {
            itemCategory = "Beverage";
        }

        new MenuItem(itemCode, itemName, itemPrice, itemAvailability, itemCategory);
        System.out.println("Item: " + itemName + " added successfully.");
    }

    private void updateMenuItem(Scanner sc) {
        MenuItem itemToUpdate = findItem(sc);
        if (itemToUpdate == null) {
            System.out.println("Item not found.");
            return;
        }

        while (true) {
            int choice = -1;
            System.out.println("Choose option: " + '\n' +
                               "1. Update Item Code" + '\n' +
                               "2. Update Item Name" + '\n' +
                               "3. Go back");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                sc.nextLine();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter new item code: ");
                    String inputCode = sc.nextLine();
                    itemToUpdate.setItemCode(inputCode);
                    System.out.println("Item code updated.");
                    itemToUpdate.displayItemDetails();
                    break;
                case 2:
                    System.out.println("Enter new item name: ");
                    String inputName = sc.nextLine();
                    itemToUpdate.setItemName(inputName);
                    System.out.println("Item name updated.");
                    itemToUpdate.displayItemDetails();
                    break;
                case 3:
                    System.out.println("Going to previous screen.");
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void removeMenuItem(Scanner sc) {
        MenuItem itemToRemove = findItem(sc);
        if (itemToRemove == null) {
            System.out.println("Item is not in menu.");
            return;
        }

        System.out.println("Removing the item: ");
        itemToRemove.displayItemDetails();
        updateOrdersAfterAvailability(itemToRemove);
        itemToRemove.removeItem();
        System.out.println("Item removed successfully.");
    }

    private void modifyItemPrice(Scanner sc) {
        MenuItem itemToModify = findItem(sc);
        if(itemToModify == null) {
            System.out.println("Item is not in menu.");
            return;
        }

        System.out.println("Enter new price: ");
        while(!sc.hasNextFloat()) {
            System.out.println("Invalid input. Price must be float.");
            sc.nextLine();
        }
        float newPrice = sc.nextFloat();
        sc.nextLine();

        while(newPrice <= 0) {
            System.out.println("Price must be positive.");
            while(!sc.hasNextFloat()) {
                System.out.println("Invalid input. Price must be a float.");
                sc.nextLine();
            }
            newPrice = sc.nextFloat();
        }

        itemToModify.setItemPrice(newPrice);

        System.out.println("Price updated successfully.");
        itemToModify.displayItemDetails();
    }

    private MenuItem findItem(Scanner sc) {
        while (true) {
            int option = -1;
            System.out.println("Find item by: " + '\n' +
                               "1. Item Code" + '\n' +
                               "2. Item Name");

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
                    return MenuItem.findItemByCode(inputCode);
                case 2:
                    System.out.println("Enter item name: ");
                    String inputName = sc.nextLine();
                    return MenuItem.findItemByName(inputName);
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void updateItemAvailability(Scanner sc) {
        MenuItem itemToUpdate = findItem(sc);

        if (itemToUpdate == null) {
            System.out.println("Item is not in menu.");
            return;
        }

        System.out.println("Is the item available? \n1. Yes \n2. No");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Enter a number.");
            sc.nextLine();
        }
        int availability = sc.nextInt();
        sc.nextLine();
        boolean itemAvailability;

        while ((availability != 1) && (availability != 2)) {
            System.out.println("Invalid input. Enter 1 for Yes and 2 for No.");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid Input. Enter a number.");
                sc.nextLine();
            }
            availability = sc.nextInt();
            sc.nextLine();
        }

        itemAvailability = (availability == 1);
        itemToUpdate.setItemAvailability(itemAvailability);

        if (!itemAvailability) {
            updateOrdersAfterAvailability(itemToUpdate);
        }

        System.out.println("Item availability updated successfully.");
        itemToUpdate.displayItemDetails();
    }

    private void updateOrdersAfterAvailability(MenuItem itemToUpdate) {
        for (Order order : allOrdersAdmin.keySet()) {
            if (!((order.getOrderStatus().equals("Delivered")) || order.getOrderStatus().equals("Cancelled"))) {
                if (order.getItems().contains(itemToUpdate)) {
                    order.cancelOrder();
                }
            }
        }
    }

    public static Admin findAdminByUsername(String username) {
        for (Admin admin : allAdmin) {
            if (username.equals(admin.getUsername())) {
                return admin;
            }
        }
        return null;
    }

    private void manageOrder(Scanner sc) {
        while (true) {
            int choice = -1;
            System.out.println("Choose option: " + '\n' +
                               "1. View pending orders" + '\n' +
                               "2. Update order status" + '\n' +
                               "3. Process refunds" + '\n' +
                               "4. Handle special requests" + '\n' +
                               "5. Go back");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewPendingOrder();
                    break;
                case 2:
                    updateOrderStatus(sc);
                    break;
                case 3:
                    System.out.println("All cancelled orders refunded successfully.");
                    break;
                case 4:
                    handleSpecialRequest(sc);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void viewPendingOrder() {
        if (allOrdersAdmin.isEmpty()) {
            System.out.println("There are no orders.");
            return;
        }

        int count = 0;
        for (Order order : allOrdersAdmin.keySet()) {
            if (!((order.getOrderStatus().equals("Delivered")) || order.getOrderStatus().equals("Cancelled"))) {
                if (order.getVIPStatus()) {
                    order.displayOrderDetails();
                    count++;
                }
            }
        }

        for (Order order : allOrdersAdmin.keySet()) {
            if (!((order.getOrderStatus().equals("Delivered")) || order.getOrderStatus().equals("Cancelled"))) {
                if (!order.getVIPStatus()) {
                    order.displayOrderDetails();
                    count++;
                }
            }
        }

        if (count == 0) {
            System.out.println("There are no pending orders.");
        }
    }

    private void updateOrderStatus(Scanner sc) {
        if (allOrdersAdmin.isEmpty()) {
            System.out.println("There are no orders.");
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

        Order orderToUpdate = null;
        for (Order order : allOrdersAdmin.keySet()) {
            if (order.getOrderID() == orderID) {
                orderToUpdate = order;
                break;
            }
        }

        if (orderToUpdate == null) {
            System.out.println("Order with ID: " + orderID + " does not exist.");
            return;
        }

        orderToUpdate.updateOrderStatus(sc);
    }

    private void handleSpecialRequest(Scanner sc) {
        if (allOrdersAdmin.isEmpty()) {
            System.out.println("There are no orders.");
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

        Order orderToUpdate = null;
        for (Order order : allOrdersAdmin.keySet()) {
            if (order.getOrderID() == orderID) {
                orderToUpdate = order;
                break;
            }
        }

        if (orderToUpdate == null) {
            System.out.println("Order with ID: " + orderID + " does not exist.");
            return;
        }

        int specreq = -1;
        System.out.println("Special request: " + orderToUpdate.getOrderStatus());
        System.out.println("Is special request accepted:\n 1. Yes\n 2. No");

        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            sc.nextLine();
        }
        specreq = sc.nextInt();
        sc.nextLine();

        if (specreq == 1) {
            orderToUpdate.setSpecialRequestAccepted(true);
            System.out.println("Special request accepted.");
            return;
        }

        System.out.println("Special request rejected.");
    }

    public static void addOrderToAdmin(Order order) {
        allOrdersAdmin.put(order, order.getOrderID());
    }

    private void reportGeneration(Scanner sc) {
        while (true) {
            int choice = -1;
            System.out.println("Choose options: " + '\n' +
                               "1. Generate sales report" + '\n' +
                               "2. View today's orders" + '\n' +
                               "3. Go back");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    dailySalesReport();
                    break;
                case 2:
                    showTodayOrders();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void dailySalesReport() {
        if (allOrdersAdmin.isEmpty()) {
            System.out.println("There are no orders.");
            return;
        }

        int count = 0;
        float orderTotal = 0;
        LocalDate todayDate = LocalDate.now();
        for (Order order : allOrdersAdmin.keySet()) {
            if (order.getOrderDate().equals(todayDate)) {
                if (!order.getRefundStatus()) {
                    orderTotal += order.getOrderTotal();
                    count++;
                }
            }
        }

        if (count == 0) {
            System.out.println("There are no orders today.");
            return;
        }

        System.out.println("Total number of orders: " + count);
        System.out.println("Total sales: " + orderTotal);
    }

    private void showTodayOrders() {
        if (allOrdersAdmin.isEmpty()) {
            System.out.println("There are no orders.");
            return;
        }

        int count = 0;
        LocalDate todayDate = LocalDate.now();
        for (Order order : allOrdersAdmin.keySet()) {
            if (order.getOrderDate().equals(todayDate)) {
                order.displayOrderDetails();
                count++;
            }
        }

        if (count == 0) {
            System.out.println("There are no orders today.");
        }
    }
}
