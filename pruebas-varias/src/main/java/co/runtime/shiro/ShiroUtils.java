package co.runtime.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.Map;

public class ShiroUtils {

    /**
     * Hash the plain-text password with a (generated) random salt and multiple iterations and then
     * Base64-encode the value (requires less space than Hex)
     *
     * @param plainTextPassword the plain text passwort to check
     * @param hashIterations    the number of times the argument hashed for attack resiliency.
     * @param algorithmName     the  algorithm name to use when performing the hash.
     * @return a Map with the hashed password as the key and the generated salt as the value
     */
    public static Map<String, String> hashPassword(String plainTextPassword, int hashIterations, String algorithmName) {
        // We'll use a Random Number Generator to generate salts. This is much
        // more secure than using a username as a salt or not having a salt at
        // all. Shiro makes this easy.

        // Note that a normal app would reference an attribute rather than
        // create a new RNG every time:
        RandomNumberGenerator rng = new SecureRandomNumberGenerator();
        Object salt = rng.nextBytes();
        String saltString = salt.toString();

        // Now hash the plain-text password with the random salt and multiple iterations and
        // then Base64-encode the value (requires less space than Hex):
        SimpleHash simpleHash = new SimpleHash(algorithmName, plainTextPassword, saltString, hashIterations);
        String hashedPasswordBase64 = simpleHash.toBase64();

        HashMap<String, String> hashedPassSaltMap = new HashMap<String, String>();
        hashedPassSaltMap.put(hashedPasswordBase64, saltString);

        return hashedPassSaltMap;
    }

    /**
     * Check if the given plain-text password matches with the hashedPassword using the salt
     *
     * @param plainTextPassword the plain text passwort to check
     * @param hashedPassword    the hashed password
     * @param salt              the salt used to hash the password
     * @param algorithmName     the  algorithm name to use when performing the hash.
     * @param hashIterations    the number of times the argument hashed for attack resiliency.
     * @return <tt>true</tt> if the text-plain password matches the hashed one, <tt>false</tt> otherwise
     */
    public static boolean checkPassword(String plainTextPassword, String hashedPassword, String salt, String algorithmName, int hashIterations) {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setStoredCredentialsHexEncoded(false);
        matcher.setHashIterations(hashIterations);
        matcher.setHashAlgorithmName(algorithmName);
        final String dummyUser = "";

        AuthenticationToken token = new UsernamePasswordToken(dummyUser, plainTextPassword);
        JdbcRealm customSecurityRealm = new JdbcRealm();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(dummyUser, hashedPassword.toCharArray(),
                customSecurityRealm.getName());
        info.setCredentialsSalt(ByteSource.Util.bytes(salt));

        return matcher.doCredentialsMatch(token, info);
    }
}