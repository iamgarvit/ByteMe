import java.util.ArrayList;

public class MenuItem {
    private String itemCode;
    private String itemName;
    private float itemPrice;
    private boolean itemAvailability;
    private String itemCategory;
    private static ArrayList<MenuItem> snackItems = new ArrayList<>();
    private static ArrayList<MenuItem> mealItems = new ArrayList<>();
    private static ArrayList<MenuItem> beverageItems = new ArrayList<>();
    private static ArrayList<MenuItem> allItems = new ArrayList<>();

    public MenuItem(String itemCode, String itemName, float itemPrice, boolean itemAvailability, String itemCategory) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemAvailability = itemAvailability;
        this.itemCategory = itemCategory;
        switch (itemCategory) {
            case "Snack":
                snackItems.add(this);
                break;
            case "Meal":
                mealItems.add(this);
                break;
            case "Beverage":
                beverageItems.add(this);
                break;
        }
        allItems.add(this);
    }

    static {
        MenuItem patty = new MenuItem("S001", "Aloo Patty", 20, true, "Snack");
        MenuItem coke = new MenuItem("B001", "Coca cola", 20, true, "Beverage");
        MenuItem coldCoffee = new MenuItem("B002", "Cold Coffee", 40, true, "Beverage");
        MenuItem chowmein = new MenuItem("S002", "Chowmein", 45, true, "Snack");
        MenuItem roti = new MenuItem("M001", "Roti", 5, true, "Meal");
        MenuItem dalMakhani =  new MenuItem("M002", "Dal Makhani", 70, true, "Meal");
        MenuItem paneerParatha = new MenuItem("M003", "Paner Paratha", 40, true, "Meal");
        MenuItem mixParatha = new MenuItem("M004", "Mix Paratha", 30, true, "Meal");
        MenuItem mountainDew = new MenuItem("B003", "Mountain Dew", 20, true, "Beverage");
        MenuItem vegRoll = new MenuItem("S003", "Veg Roll", 40, true, "Snack");
    }

    public static void initialiseMenuItems() {}

    public String getItemCode() {
        return this.itemCode;
    }

    public String getItemName() {
        return this.itemName;
    }

    public float getItemPrice() {
        return this.itemPrice;
    }

    public boolean getItemAvailability() {
        return this.itemAvailability;
    }

    public String getItemCategory() {
        return this.itemCategory;
    }

    public void setItemCode(String newCode) {
        this.itemCode = newCode;
    }

    public void setItemName(String newName) {
        this.itemName = newName;
    }

    public void setItemPrice(float newPrice) {
        this.itemPrice = newPrice;
    }

    public void setItemAvailability(boolean newAvailability) {
        this.itemAvailability = newAvailability;
    }

    public static ArrayList<MenuItem> getAllItems() {
        return allItems;
    }

    public static MenuItem findItemByCode(String code) {
        for (MenuItem item : allItems) {
            if (item.itemCode.equals(code)) {
                return item;
            }
        }
        return null;
    }

    public static MenuItem findItemByName(String name) {
        for (MenuItem item : allItems) {
            if (item.itemName.equals(name)) {
                return item;
            }
        }
        return null;
    }

    public void displayItemDetails() {
        System.out.println("Item code: " + this.itemCode + '\n' +
                           "Item name: " + this.itemName + '\n' +
                           "Item price: " + this.itemPrice + '\n' +
                           "Item category: " + this.itemCategory + '\n' +
                           "Item availability: " + this.itemAvailability + '\n');
    }

    public static ArrayList<MenuItem> getSnackItems() {
        return snackItems;
    }

    public static ArrayList<MenuItem> getMealItems() {
        return mealItems;
    }

    public static ArrayList<MenuItem> getBeverageItems() {
        return beverageItems;
    }

    public void removeItem() {
        allItems.remove(this);
    }
}
