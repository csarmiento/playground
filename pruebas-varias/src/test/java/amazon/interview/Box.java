package amazon.interview;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Assumptions:
 * 1. The height and weight of each box are positive integers > 0
 * 2. Modeling a Box class to encapsulate height and weight
 */
public class Box {
    public final static WeightComparator
            WEIGHT_COMPARATOR = new WeightComparator();
    public final static DescendingHeightWeightComparator
            DESCENDING_HEIGHT_WEIGHT_COMPARATOR = new DescendingHeightWeightComparator();

    private int height, weight;

    public Box(int height, int weight) {
        this.height = height;
        this.weight = weight;
    }

    // Assuming N: Number of boxes
    // Time complexity = N*N + N log N in the worst case, in average must be less than N*N
    // Space complexity = 2N (because of merge sort - Collections.sort)
    public static int largestTower(List<Box> boxes) {
        int largest = 0;
        if (boxes.size() > 0) {
            Collections.sort(boxes, DESCENDING_HEIGHT_WEIGHT_COMPARATOR);
            for (int i = 0; i < boxes.size() - largest; i++) {
                int tower = 1;
                int pivot = i;
                for (int j = i + 1; j < boxes.size() && pivot < boxes.size() - largest; j++) {
                    Box box1 = boxes.get(pivot);
                    Box box2 = boxes.get(j);

                    if (WEIGHT_COMPARATOR.compare(box1, box2) > 0) {
                        tower++;
                        pivot = j;
                    }
                }
                if (tower > largest) {
                    largest = tower;
                }
            }
        }
        return largest;
    }

    @Override
    public String toString() {
        return "[h:" + height + ", w:" + weight + "]";
    }

    private static class WeightComparator implements Comparator<Box> {
        @Override
        public int compare(Box o1, Box o2) {
            return o1.weight - o2.weight;
        }
    }

    private static class DescendingHeightWeightComparator implements Comparator<Box> {
        @Override
        public int compare(Box o1, Box o2) {
            int diffH = o2.height - o1.height;
            int diffW = o2.weight - o1.weight;
            if (diffH == 0) {
                return diffW;
            } else {
                return diffH;
            }
        }
    }
}
