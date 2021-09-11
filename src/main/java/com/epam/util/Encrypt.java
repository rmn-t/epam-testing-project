package com.epam.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Encrypt {
    private Encrypt() {    }

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
            // add log
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

//    public static void main(String[] args) {
//        String p1 = "testqq";
//        int salt1 = generateSalt();
//        String p2 = "jack";
//        int salt2 = generateSalt();
//        String p3 = "amy";
//        int salt3 = generateSalt();
//        String pass1 = getSecurePassword(p1,salt1);
//        String pass2 = getSecurePassword(p2,salt2);
//        String pass3 = getSecurePassword(p3,salt3);
//
//        System.out.println(salt1);
//        System.out.println(pass1);
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        System.out.println(salt2);
//        System.out.println(pass2);
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        System.out.println(salt3);
//        System.out.println(pass3);
//
//
//    }
}
