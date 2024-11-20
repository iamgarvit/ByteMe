import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Menu {
    private static ArrayList<MenuItem> allItemsMenu = new ArrayList<>();

    public Menu(){
        allItemsMenu = MenuItem.getAllItems();
    }

    public static void initialiseMenu(){
        new Menu();
    }

    public static void GUIMenuDisplay() {
        new Menu();
        JFrame frame = new JFrame();
        frame.setTitle("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        String[] titles = {"Item ID", "Item Name", "Item Price", "Item Availability"};
        DefaultTableModel menu = new DefaultTableModel(titles, 0);
        JTable table = new JTable(menu);

        for (MenuItem item : allItemsMenu) {
            menu.addRow(new Object[]{item.getItemCode(), item.getItemName(), item.getItemPrice(), item.getItemAvailability()});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void displayMenu(Scanner sc) {
        new Menu();
        while (true) {
            int choice = -1;
            System.out.println("Choose option: " + '\n' +
                    "1. Display Full Menu" + '\n' +
                    "2. Search in Menu" + '\n' +
                    "3. Filter by category" + '\n' +
                    "4. Sort by price" + '\n' +
                    "5. Go back");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    GUIMenuDisplay();
                    break;
                case 2:
                    searchInMenu(sc);
                    break;
                case 3:
                    filterByCategory(sc);
                    break;
                case 4:
                    sortByPrice();
                    break;
                case 5:
                    System.out.println("Going back to previous screen.");
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    public static void displayFullMenu() {
        for (MenuItem item : allItemsMenu) {
            displayItemDetails(item);
        }
    }

    private static void searchInMenu(Scanner sc) {
        System.out.println("Enter search input: ");
        String inputText = sc.nextLine();
        int count = 0;
        for (MenuItem item : allItemsMenu) {
            if (item.getItemName().toLowerCase().contains(inputText.toLowerCase())) {
                displayItemDetails(item);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No items match your search.");
        }
        return;
    }

    private static void filterByCategory(Scanner sc) {
        int choice = 0;
        System.out.println("Choose category: " + '\n' +
                           "1. Snack" + '\n' +
                           "2. Meal" + '\n' +
                           "3. Beverage");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            sc.nextLine();
        }
        choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                ArrayList<MenuItem> snacks = MenuItem.getSnackItems();
                for (MenuItem item : snacks) {
                    displayItemDetails(item);
                }
                break;
            case 2:
                ArrayList<MenuItem> meals = MenuItem.getMealItems();
                for (MenuItem item : meals) {
                    displayItemDetails(item);
                }
                break;
            case 3:
                ArrayList<MenuItem> beverages = MenuItem.getBeverageItems();
                for (MenuItem item : beverages) {
                    displayItemDetails(item);
                }
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private static void sortByPrice() {
        ArrayList<MenuItem> sortedMenu = allItemsMenu;
        Collections.sort(sortedMenu, Comparator.comparing(MenuItem::getItemPrice));
        for (MenuItem item : sortedMenu) {
            displayItemDetails(item);
        }
    }

    private static void displayItemDetails(MenuItem item) {
        System.out.println("Item code : " + item.getItemCode() + '\n' +
                           "Item name: " + item.getItemName() + '\n' +
                           "Item price: " + item.getItemPrice() + '\n' +
                           "Item category: " + item.getItemCategory() + '\n' +
                           "Item availability: " + item.getItemAvailability() + '\n');
    }
}
