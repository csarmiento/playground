package algo;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class SelectionMedianTest {

    private int randInt(int min, int max) {
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return rand.nextInt((max - min) + 1) + min;
    }

    private Integer[] generateRandomArray(int size, int min, int max) {
        Integer[] intArray = new Integer[size];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = randInt(min, max);
        }
        return intArray;
    }

    @Test
    public void testMedian() throws Exception {
        Integer[] a = generateRandomArray(15, 1, 40);

        System.out.println(Arrays.toString(a));

        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (Integer num : a) {
            stats.addValue(num);
        }

        double median = stats.getPercentile(50);

        System.out.println(median);

        Arrays.sort(a);
        System.out.println(Arrays.toString(a));

    }
}