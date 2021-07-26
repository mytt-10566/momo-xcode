package com.momo.xcode.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 *
 * @author moqinggen
 * @date 2021/07/26
 */
public class MD5Util {

    private static MessageDigest MD5_DIGEST;

    static {
        try {
            MD5_DIGEST = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * md5
     *
     * @param source
     * @return
     */
    public static byte[] encode2Bytes(String source) {
        if (source == null || source.length() <= 0) {
            throw new IllegalArgumentException("message should not be empty");
        }
        byte[] btInput = source.getBytes();
        MD5_DIGEST.reset();
        MD5_DIGEST.update(btInput);
        return MD5_DIGEST.digest();
    }

    /**
     * md5
     *
     * @param source
     * @return
     */
    public static String encode2String(String source) {
        byte[] mdBytes = encode2Bytes(source);
        StringBuilder hexString = new StringBuilder();
        for (byte mdByte : mdBytes) {
            String hex = Integer.toHexString(0xff & mdByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
