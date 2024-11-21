import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Scanner;

public class LoginTesting {
    @Test
    public void testLoginCustomer() throws Exception {
        Scanner sc = new Scanner(System.in);

        try {
            Assertions.assertTrue(Customer.customerLogin("garvit", "garvit123", sc, 1));
            Assertions.assertFalse(Customer.customerLogin("garvit", "garvit1234", sc, 1));
            Assertions.assertFalse(Customer.customerLogin("garvit123", "garvit123", sc, 1));
        }
        finally {
            sc.close();
        }
    }

    @Test
    public void testLoginAdmin() throws Exception {
        Scanner sc = new Scanner(System.in);

        try {
            Assertions.assertTrue(Admin.adminLogin("admin", "admin123", sc, 1));
            Assertions.assertFalse(Admin.adminLogin("admin", "admin1234", sc, 1));
            Assertions.assertFalse(Admin.adminLogin("admin123", "admin123", sc, 1));
        }
        finally {
            sc.close();
        }
    }
}
