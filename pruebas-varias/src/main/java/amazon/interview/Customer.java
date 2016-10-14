package amazon.interview;

import java.util.ArrayDeque;

public class Customer implements Comparable<Customer> {
    // Each customer will only read one book at a time,
    // but multiple customers may read the same book.
    private String bookId;
    private String customerId;
    // Queue storing the last 10 readings
    private ArrayDeque<Integer> pageHistory;
    // Latest reading speed
    private int readingSpeed;

    public Customer(String customerId, String bookId) {
        pageHistory = new ArrayDeque<Integer>();
        this.customerId = customerId;
        this.bookId = bookId;
    }

    public String getBookId() {
        return this.bookId;
    }

    public int getReadingSpeed() {
        return this.readingSpeed;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void updatePageHistory(int page) {
        pageHistory.add(page);
        if (pageHistory.size() > KindleNewFeature.TIME_FRAME) {
            // just keep the last 10 readings
            pageHistory.removeFirst();
        }
        updateReadSpeed();
    }

    private void updateReadSpeed() {
        readingSpeed = 0;
        Integer[] history = pageHistory.toArray(new Integer[pageHistory.size()]);
        if (history.length > 1) {
            for (int i = 1; i < history.length; i++) {
                int speed = history[i] - history[i - 1];
                if (speed > readingSpeed) {
                    readingSpeed = speed;
                }
            }
        }
    }

    public int compareTo(Customer other) {
        // As the leader board requires descending order this implementation
        // returns inverse comparison
        return other.readingSpeed - this.readingSpeed;
    }
}