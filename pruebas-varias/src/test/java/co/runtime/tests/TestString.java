package co.runtime.tests;

import org.junit.Test;

/**
 * @author Camilo Sarmiento
 * @since 2016-10-14
 */
public class TestString {
    @Test
    public void testStringFormatPaddingInteger() {
        for (int i = 0; i < 30; i++) {
            String paddedInteger = String.format("%02d", i);
            System.out.println(paddedInteger);
        }
    }
}
