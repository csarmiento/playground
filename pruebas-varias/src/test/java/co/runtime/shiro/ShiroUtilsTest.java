package co.runtime.shiro;

import junit.framework.TestCase;

import java.util.Map;

public class ShiroUtilsTest extends TestCase {
    public static final String SHA_256 = "SHA-256";
    public static final int HASH_ITERATIONS = 1024;

    public void testHashPassword() throws Exception {
        String password = "p0p01234";

        Map<String, String> map = ShiroUtils.hashPassword(password, HASH_ITERATIONS, SHA_256);

        assertNotNull(map);

        for (String hashedPass : map.keySet()) {
            assertTrue(ShiroUtils.checkPassword(password, hashedPass, map.get(hashedPass), SHA_256, HASH_ITERATIONS));
        }
    }

    public void testCheckPassword() {
        String password = "p0p01234";
        String hashedPassword = "lnl1xDIYcIhb3Vj4hDcyXfXdGqULfZVWYSdDS/IulFs=";
        String salt = "5h86Xn2d41K/xDoBXa+UMQ==";

        assertTrue(ShiroUtils.checkPassword(password, hashedPassword, salt, SHA_256, HASH_ITERATIONS));
    }
}