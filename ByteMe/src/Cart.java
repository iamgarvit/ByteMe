import java.util.Comparator;
import java.util.TreeMap;

public class Cart {
    private float cartTotal;
    private Customer customer;
    private TreeMap<MenuItem, Integer> cartList = new TreeMap<>(Comparator.comparing(MenuItem::getItemCode));

    public Cart(Customer customer) {
        this.cartTotal = 0;
        this.customer = customer;
    }

    public boolean addItem(MenuItem item, int quantity) {
        if (item.getItemAvailability()) {
            cartList.put(item, quantity);
            cartTotal += item.getItemPrice() * quantity;
            return true;
        }
        System.out.println("Item: " + item.getItemName() + " is unavailable.");
        return false;
    }

    public void modifyQuantity(MenuItem item, int quantity) {
        cartTotal -= item.getItemPrice()*(cartList.get(item));
        cartList.put(item, quantity);
        cartTotal += item.getItemPrice()*quantity;
    }

    public void removeItem(MenuItem item) {
        cartTotal -= item.getItemPrice()*(cartList.get(item));
        cartList.remove(item);
    }

    public void placeOrder(String specialRequest, String address) {
        Order orderToPlace = new Order(cartList, customer, cartTotal, specialRequest, customer.getVIPStatus(), address);
        customer.addOrder(orderToPlace);
        Admin.addOrderToAdmin(orderToPlace);
        clearCart();
    }

    public void clearCart() {
        cartList.clear();
        cartTotal = 0;
    }

    public boolean isCartEmpty() {
        return cartList.isEmpty();
    }

    public void displayCart() {
        if (cartList.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        for (MenuItem item : cartList.keySet()) {
            System.out.println(item.getItemName() + " : " + cartList.get(item));
        }
    }

    public TreeMap<MenuItem, Integer> getCartList() {
        return cartList;
    }

    public float getTotal() {
        return cartTotal;
    }
}
