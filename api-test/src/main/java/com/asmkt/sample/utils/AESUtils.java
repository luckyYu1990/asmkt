package com.asmkt.sample.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

@Slf4j
public class AESUtils {
    /*private static String salt = "abcdefg";
    public static String encrypt(String strToEncrypt, String secret) {
        try {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret) {
        try {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }*/

    private static final String CHARSET_NAME = "UTF-8";
    private static final String AES_NAME = "AES";
    // 加密模式
    public static final String ALGORITHM = "AES/CBC/PKCS7Padding";
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 加密
     *
     * @param content
     * @param key
     * @return
     */
    public static String encrypt(@NotNull String content, String secretKey) {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
            SecretKeySpec keySpec = new SecretKeySpec(getAesKey(secretKey.getBytes(StandardCharsets.UTF_8)), AES_NAME);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec("0000000000000000".getBytes(CHARSET_NAME));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
            result = cipher.doFinal(content.getBytes(CHARSET_NAME));
        } catch (Exception e) {
            log.error("encrypt", e);
        }
        return Base64.getEncoder().encodeToString(result);
    }

    /**
     * 解密
     *
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(@NotNull String content, String secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
            SecretKeySpec keySpec = new SecretKeySpec(getAesKey(secretKey.getBytes(StandardCharsets.UTF_8)), AES_NAME);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec("0000000000000000".getBytes(CHARSET_NAME));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(content)), CHARSET_NAME);
        } catch (Exception e) {
            log.error("decrypt failed", e);
        }
        return StringUtils.EMPTY;
    }

    private static byte[] getAesKey(byte[] keyArray) {
        byte[] newArray = new byte[32];
        for (int i = 0; i < newArray.length; i++) {
            if (i >= keyArray.length) {
                newArray[i] = 0;
            }
            else {
                newArray[i] = keyArray[i];
            }
        }
        return newArray;
    }
}
