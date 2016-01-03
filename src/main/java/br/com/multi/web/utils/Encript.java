package br.com.multi.web.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encript {
	
    public static String generateSHA256(String s) {

        String encoded = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(s.getBytes());
            byte byteData[] = md.digest();
            encoded = new String(byteData);
        } catch (NoSuchAlgorithmException ex) {
        }
        return getHexString(encoded);
    }

    /**
     * Returns hex string from s
     * @param s
     * @return 
     */
    private static String getHexString(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.getBytes().length; i++) {
            sb.append(Integer.toString((s.getBytes()[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
