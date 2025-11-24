package org.example.synergy.utils.voffice;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class AESUtil {
    private static final String ENCRYPTED = "8374921065832947";

    public static String decryptCryptoJS(String password) throws Exception {
        // Giải mã Base64 để lấy dữ liệu đã mã hóa và salt
        byte[] encryptedBytesWithSalt = Base64.getDecoder().decode(password);

        // Kiểm tra tiền tố "Salted__" để xác nhận chuỗi mã hóa có đúng
        byte[] saltPrefix = Arrays.copyOfRange(encryptedBytesWithSalt, 0, 8);
        String prefix = new String(saltPrefix);
        if (!"Salted__".equals(prefix)) {
            throw new IllegalArgumentException("Invalid encrypted data - no 'Salted__' prefix");
        }

        // Lấy salt và dữ liệu mã hóa
        byte[] salt = Arrays.copyOfRange(encryptedBytesWithSalt, 8, 16);
        byte[] encrypted = Arrays.copyOfRange(encryptedBytesWithSalt, 16, encryptedBytesWithSalt.length);

        // Dẫn xuất key và IV từ password và salt
        byte[] keyAndIv = EVP_BytesToKey(32, 16, salt, ENCRYPTED.getBytes("UTF-8"));
        byte[] key = Arrays.copyOfRange(keyAndIv, 0, 32);
        byte[] iv = Arrays.copyOfRange(keyAndIv, 32, 48);

        // Sử dụng AES/CBC/PKCS5Padding để giải mã
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        @SuppressWarnings("squid:S5542") // hoặc "java:S5542" tùy rule ID
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] decryptedBytes = cipher.doFinal(encrypted);
        return new String(decryptedBytes, "UTF-8");
    }

    // Dẫn xuất key và IV từ password + salt
    public static byte[] EVP_BytesToKey(int keyLen, int ivLen, byte[] salt, byte[] password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] keyAndIv = new byte[keyLen + ivLen];
        byte[] prev = new byte[0];
        int offset = 0;

        while (offset < keyAndIv.length) {
            md.reset();
            md.update(prev);
            md.update(password);
            md.update(salt);
            byte[] hash = md.digest();
            int length = Math.min(hash.length, keyAndIv.length - offset);
            System.arraycopy(hash, 0, keyAndIv, offset, length);
            offset += length;
            prev = hash;
        }
        return keyAndIv;
    }
}