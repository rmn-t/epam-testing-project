package com.epam.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * A util class, used for password encryption. It is expected to use this class only with static context.
 */
public class Encrypt {

    /**
     * Private constructor in order to prevent creating instances of the class.
     */
    private Encrypt() {    }

    /**
     * Encrypts the password into a hashed string
     * @param password provided by the user
     * @param salt used to provide additional layer of complexity if database will be exposed
     * @return 128 char string
     */
    public static String getSecurePassword(String password, int salt) {
        StringBuilder res = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(intToByteArray(salt));
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            for (byte aByte : bytes) {
                res.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return res.toString();
    }

    public static int generateSalt() {
        return new SecureRandom().nextInt(Integer.MAX_VALUE);
    }

    private static byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }

}
