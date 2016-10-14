package co.runtime.tests;

import java.util.Arrays;

import junit.framework.TestCase;
import co.runtime.clustering.Recursiones;

/**
 * Created by camilosarmiento on 5/21/14.
 */
public class TestRecursiones extends TestCase {
    public void testPosiblesCambios() {
        Integer[] monedas = {10, 5, 1};

        assertEquals(4, Recursiones.posibilidadesCambio(10, Arrays.asList(monedas), 0));
    }
}
