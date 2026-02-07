package com.example.IT342_G1_Maturan.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public static boolean verify(String rawPassword, String hashed) {
        if (rawPassword == null || hashed == null) return false;
        return encoder.matches(rawPassword, hashed);
    }
}
