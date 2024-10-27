import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
    private String username;
    private String password;
    private String name;
    private static ArrayList<Admin> allAdmin = new ArrayList<>();

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Admin(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        allAdmin.add(this);
    }

    public void AdminSignUp(Scanner sc) {
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

    public void adminLogin(String username, String password, Scanner sc) {
        Admin adminToLogin = findAdminByUsername(username);
        if (adminToLogin == null) {
            System.out.println("Admin with username: " + username + " does not exists.");
            return;
        }

        if ((username.equals(adminToLogin.getUsername())) && (password.equals(adminToLogin.getPassword()))) {
            System.out.println("Successfully logged in as: " + username + '\n');
            displayAdminMenu(sc);
        }
        else {
            System.out.println("Login failed. Invalid credentials.");
            return;
        }
    }

    private void displayAdminMenu(Scanner sc) {
        while (true) {
            int choice = -1;
            System.out.println("Choose option: " + '\n' +
                    "1. Menu Management" + '\n' +
                    "2. Order Management" + '\n' +
                    "3. Report Generation" + '\n' +
                    "4. Logout" + '\n');
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
                    //Order management
                    break;
                case 3:
                    //Report generation
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
                               "7. Go back" + '\n');
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
                    System.out.println("Returning to previous screen.\n");
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
                           "3. Beverage" + '\n');
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Enter a number.");
            sc.nextLine();
        }
        int category = sc.nextInt();
        while (!((category <= 3) && (category >= 1))) {
            System.out.println("Input must be value b/w 1 and 3. Re enter input: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Input must be an integer.");
                sc.nextLine();
            }
            category = sc.nextInt();
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
                               "3. Go back" + '\n');
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                sc.nextLine();
            }
            choice = sc.nextInt();

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
                    System.out.println("Going to previous screen.\n");
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

        System.out.println("Removing the item: \n");
        itemToRemove.displayItemDetails();
        itemToRemove.removeItem();
        System.out.println("Item removed successfully.\n");
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

        System.out.println("Price updated successfully.\n");
        itemToModify.displayItemDetails();
    }

    private MenuItem findItem(Scanner sc) {
        while (true) {
            int option = -1;
            System.out.println("Find item by: " + '\n' +
                               "1. Item Code" + '\n' +
                               "2. Item Name" + '\n');

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
        System.out.println("Item availability updated successfully.");
        itemToUpdate.displayItemDetails();
    }

    public Admin findAdminByUsername(String username) {
        for (Admin admin : allAdmin) {
            if (username.equals(admin.getUsername())) {
                return admin;
            }
        }
        return null;
    }
}
