import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Menu {
    private static ArrayList<MenuItem> allItemsMenu = new ArrayList<>();

    public Menu(){
        allItemsMenu = MenuItem.getAllItems();
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

            switch (choice) {
                case 1:
                    displayFullMenu();
                    break;
                case 2:
                    searchInMenu(sc);
                    break;
                case 3:
                    //Filter by category
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
            if (item.getItemName().contains(inputText)) {
                displayItemDetails(item);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No items match your search.");
        }
        return;
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
