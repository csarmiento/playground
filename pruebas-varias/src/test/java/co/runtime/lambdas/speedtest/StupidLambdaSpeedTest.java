package co.runtime.lambdas.speedtest;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Camilo Sarmiento
 * @since 2016-10-21
 */
public class StupidLambdaSpeedTest {
    private static final String[] testArray;

    private static final int ARRAY_SIZE = 10000;

    static {
        testArray = new String[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            testArray[i] = String.format("%05d", ThreadLocalRandom.current().nextInt(10000));
        }
    }

    @Test
    public void checkLambdaMapSpeed() {
        long lambdaTime = timeElapsed(() -> Arrays.stream(testArray).map(StupidLambdaSpeedTest::converter).toArray(Integer[]::new));
        long foriTime = timeElapsed(() -> {
            Integer[] numbers = new Integer[ARRAY_SIZE];
            for (int i = 0; i < ARRAY_SIZE; i++) {
                numbers[i] = converter(testArray[i]);
            }
        });

        System.out.println("Lambda Time: " + lambdaTime);
        System.out.println("For-i Time: " + foriTime);

        System.out.println("Difference " + Math.abs(lambdaTime - foriTime) + "ms in favor of: " + ((lambdaTime > foriTime) ? "For-i" : "Lambda"));
    }

    private static int converter(String s) {
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private long timeElapsed(Runnable function) {
        long start = System.currentTimeMillis();
        function.run();
        long end = System.currentTimeMillis();
        return end - start;
    }
}
