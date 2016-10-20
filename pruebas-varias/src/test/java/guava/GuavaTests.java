package guava;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Arrays;
import java.util.EnumMap;

/**
 * @author Camilo Sarmiento
 * @since 2016-10-20
 */
public class GuavaTests {

    private enum TestEnum {
        VAL1, VAL2, VAL3
    }

    @Test
    public void enumMapWithComputeIfAbsentTest() {
        EnumMap<TestEnum, String> enumMap = Maps.newEnumMap(TestEnum.class);

        Arrays.stream(TestEnum.values()).forEach(val -> {
            enumMap.computeIfAbsent(val, testEnum -> testEnum.toString() + " PERRITO");
        });

        enumMap.forEach((key, value) -> {
            System.out.println(key.toString() + ": " + value);
        });
    }
}
