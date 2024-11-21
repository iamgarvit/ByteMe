import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        MenuItem.initialiseMenuItems();
        Admin.initialiseAdmin();
        Customer.initialiseCustomers();
        Menu.initialiseMenu();

        Scanner sc = new Scanner(System.in);
        while (true) {
            int choice = -1;
            System.out.println("Choose option: " + '\n' +
                               "1. Signup" + '\n' +
                               "2. Login" + '\n' +
                               "3. Exit");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number");
                sc.nextLine();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    signUp(sc);
                    break;
                case 2:
                    login(sc);
                    break;
                case 3:
                    System.out.println("Exited successfully.");
                    sc.close();
                    break;
            }
        }
    }

    public static void signUp(Scanner sc) throws IOException {
        while (true) {
            int choice = -1;
            System.out.println("Sign up as: " + '\n' +
                    "1. Customer" + '\n' +
                    "2. Admin" + '\n' +
                    "3. Go back");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number");
                sc.nextLine();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    Customer.customerSignup(sc);
                    break;
                case 2:
                    Admin.AdminSignUp(sc);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option");
                    sc.nextLine();
            }
        }
    }

    public static void login(Scanner sc) throws IOException {
        while (true) {
            int choice = -1;
            System.out.println("Login as: " + '\n' +
                    "1. Customer" + '\n' +
                    "2. Admin" + '\n' +
                    "3. Go back");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number");
                sc.nextLine();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter customer username: ");
                    String username = sc.nextLine();
                    System.out.println("Enter customer password: ");
                    String password = sc.nextLine();

                    Customer.customerLogin(username, password, sc, 0);
                    break;
                case 2:
                    System.out.println("Enter admin username: ");
                    String usernameAdmin = sc.nextLine();
                    System.out.println("Enter admin password: ");
                    String passwordAdmin = sc.nextLine();

                    Admin.adminLogin(usernameAdmin, passwordAdmin, sc, 0);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }
}
