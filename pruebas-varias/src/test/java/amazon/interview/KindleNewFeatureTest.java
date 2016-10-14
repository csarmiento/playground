package amazon.interview;

import junit.framework.TestCase;

import java.util.ArrayDeque;

public class KindleNewFeatureTest extends TestCase {

    private int[] pageHistory1 = {0, 5, 6, 8, 12, 15, 17, 21, 24, 27, 29, 31, 37, 42, 49, 52};
    private int[] pageHistory2 = {0, 2, 5, 6, 10, 13, 14, 25, 29, 33, 45, 49, 52, 55, 57, 62};


    public void testDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<Integer>(10);

        for (int page : pageHistory1) {
            deque.add(page);
            if (deque.size() > 10) {
                deque.removeFirst();
            }
            System.out.println(deque);
        }
    }

    public void testUpdateReadingSpeeds() {
        KindleNewFeature newFeature = new KindleNewFeature();

        for (int page : pageHistory1) {
            newFeature.updateReadingSpeeds("Customer 1", "Book 1", page);
        }
        for (int page : pageHistory2) {
            newFeature.updateReadingSpeeds("Customer 2", "Book 1", page);
        }
    }

    public void testPrintLeaderboard() {
        KindleNewFeature newFeature = new KindleNewFeature();
        for (int i = 0; i < pageHistory1.length; i++) {
            newFeature.updateReadingSpeeds("Customer 1", "Book 1", pageHistory1[i]);
            newFeature.updateReadingSpeeds("Customer 2", "Book 1", pageHistory2[i]);

            newFeature.printLeaderboard("Book 1");
        }
    }
}