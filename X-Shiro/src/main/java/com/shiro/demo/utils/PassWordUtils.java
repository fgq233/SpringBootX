package com.shiro.demo.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 密码加密
 */
public class PassWordUtils {

    public static final String SHA1 = "SHA-1";

    public static final int ITERATIONS = 1024;

    /**
     * SHA1加密
     */
    public static String sha1(String input, String salt) {
        return new SimpleHash(SHA1, input, salt, ITERATIONS).toString();
    }

}
