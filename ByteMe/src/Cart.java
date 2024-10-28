import java.util.TreeMap;

public class Cart {
    private float cartTotal;
    private Customer customer;
    private TreeMap<MenuItem, Integer> cartList = new TreeMap<>();

    public Cart(Customer customer) {
        this.cartTotal = 0;
        this.customer = customer;
    }

    public void addItem(MenuItem item, int quantity) {
        cartList.put(item, quantity);
        cartTotal += item.getItemPrice()*quantity;
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

    public void placeOrder() {
        Order orderToPlace = new Order(cartList, customer, cartTotal);
        customer.addOrder(orderToPlace);
        clearCart();
    }

    public void clearCart() {
        cartList.clear();
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
            System.out.println(item.getItemName() + " : " + cartList.get(item) + '\n');
        }
    }

    public TreeMap<MenuItem, Integer> getCartList() {
        return cartList;
    }

    public float getTotal() {
        return cartTotal;
    }
}