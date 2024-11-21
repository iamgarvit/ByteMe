import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class UnavailabilityTesting {
    @Test
    public void testUnavailableItems() throws IOException {
        Customer customerTest = new Customer("TestCustomer", "TestCustomer123", "Test");
        Cart testCart = new Cart(customerTest);
        ArrayList<MenuItem> unavailableItems = MenuItem.getUnavailableItems();

        for (MenuItem unavailableItem : unavailableItems) {
            Assertions.assertFalse(testCart.addItem(unavailableItem, 1));
        }
    }
}
