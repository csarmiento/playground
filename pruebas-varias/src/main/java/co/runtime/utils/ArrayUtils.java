package co.runtime.utils;

import java.util.Random;

/**
 * Some handful utilities
 *
 * @author Camilo Sarmiento
 */
public class ArrayUtils {
    /**
     * Pseudo-random number generator
     */
    private static Random random;

    static {
        // Pseudo-random number generator seed
        long seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    /**
     * Returns an integer uniformly between 0 (inclusive) and N (exclusive).
     *
     * @param N max range value
     * @return an integer uniformly between 0 (inclusive) and N (exclusive).
     * @throws IllegalArgumentException if <tt>N <= 0</tt>
     */
    private static int uniform(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("Parameter N must be positive");
        return random.nextInt(N);
    }

    /**
     * Rearrange the elements of an array in random order.
     *
     * @param a Array to shuffle
     */
    public static void shuffle(Object[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N - i); // between i and N-1
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
}
