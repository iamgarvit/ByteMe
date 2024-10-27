import java.util.ArrayList;
import java.util.Scanner;

public class Customer {
    private String username;
    private String password;
    private String name;
    private Scanner sc;
    public static ArrayList<Customer> allCustomers = new ArrayList<>();

    public Customer(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        allCustomers.add(this);
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

    public void customerSignup(Scanner sc) {
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

    public void customerLogin(String username, String password, Scanner sc) {
        Customer customerToLogin = findCustomerByUsername(username);
        if (customerToLogin == null) {
            System.out.println("Customer with username: " + username + " does not exist.");
            return;
        }

        if ((username.equals(customerToLogin.getUsername())) && (password.equals(customerToLogin.getPassword()))) {
            System.out.println("Login successful.");
            displayCustomerMenu(sc);
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
                    "1. Browse Menu" + '\n' +
                    "2. View Cart" + '\n' +
                    "3. Track Order" + '\n' +
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
                    System.out.println("Cart viewing");
                    break;
                case 3:
                    System.out.println("Order tracking");
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

    public Customer findCustomerByUsername(String username) {
        for (Customer customer : allCustomers) {
            if (customer.getUsername().equals(username)) {
                return customer;
            }
        }
        return null;
    }
}
