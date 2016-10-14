package amazon.interview;

import java.util.*;

public class KindleNewFeature {
    // A customer's "reading speed" is the maximum number of pages they
    // have read in a single minute over the previous 10 minutes.
    public static final int TIME_FRAME = 10;

    // Here using a map according to: "Do not use any databases, external libraries, or third party solutions."
    Map<String, Customer> customers;

    // Assuming N: Number of customers, K space used for the pageHistory queue
    // Space complexity = N * K in average
    public KindleNewFeature() {
        // This implementation (HashMap) provides constant-time performance for the basic operations (get and put)
        customers = new HashMap<String, Customer>();
    }

    // Time complexity = K (constant-time Map access) + 20
    public void updateReadingSpeeds(String customerID, String bookID, int pageNumber) {
        Customer c = customers.get(customerID);
        if (c == null) {
            c = new Customer(customerID, bookID);
            customers.put(customerID, c);
        }
        c.updatePageHistory(pageNumber);
    }

    // Assuming N: Number of customers, M: Number of readers of the Book:bookId
    // Time complexity = N + M + 2 M log M in average
    // Space complexity = 2M + log M
    void printLeaderboard(String bookID) {
        Customer[] leaderBoard = prepareLeaderBoard(bookID);

        System.out.println("Customer ID,Reading Speed,Rank");
        for (int i = 0; i < leaderBoard.length; i++) {
            System.out.print(leaderBoard[i].getCustomerId());
            System.out.print(",");
            System.out.print(leaderBoard[i].getReadingSpeed());
            System.out.print(",");
            System.out.println(i + 1);
        }
    }

    // Assuming N: Number of customers, M: Number of readers of the Book:bookId
    // Time complexity = N + 2 M log M in average
    // Space complexity = 2M + log M
    public Customer[] prepareLeaderBoard(String bookId) {
        List<Customer> bookReaders = new ArrayList<>();
        for (String custId : customers.keySet()) {
            Customer c = customers.get(custId);
            if (c.getBookId().equals(bookId)) {
                bookReaders.add(c);
            }
        }
        Customer[] leaderBoard = bookReaders.toArray(new Customer[bookReaders.size()]);
        Arrays.sort(leaderBoard);
        return leaderBoard;
    }
}