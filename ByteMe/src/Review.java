import java.util.ArrayList;

public class Review {
    private Customer customer;
    private String review;
    private MenuItem item;
    private static ArrayList<Review> allReviews = new ArrayList<>();

    Review(Customer customer, String review, MenuItem item) {
        this.customer = customer;
        this.review = review;
        this.item = item;
        allReviews.add(this);
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getReview() {
        return review;
    }

    public MenuItem getItem() {
        return item;
    }
}
