//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.synergy.utils.voffice;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
@Slf4j
public class RSA {
    private PublicKey public_Key;
    private PrivateKey private_Key;

    public PrivateKey getPrivate_Key() {
        return this.private_Key;
    }

    public PublicKey getPublic_Key() {
        return this.public_Key;
    }

    public RSA() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            this.public_Key = keyPair.getPublic();
            this.private_Key = keyPair.getPrivate();
        } catch (NoSuchAlgorithmException var3) {
            log.error("NoSuchAlgorithmException: ", var3);
            var3.printStackTrace();
        }

    }

    public static String getAesKey(String inputAesKey, String inputPublicKey) throws Exception {
        if (inputAesKey != null && !inputAesKey.isEmpty() && inputPublicKey != null && !inputPublicKey.isEmpty()) {
            byte[] publicKey = hexStringToByteArray(inputPublicKey);
            PublicKey publicKey_key = convertByteToPublicKey(publicKey);
            byte[] dataEncodeAesKey = encryptData(inputAesKey, publicKey_key);
            return bytesToHex(dataEncodeAesKey);
        } else {
            return null;
        }
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];

        for(int i = 0; i < len; i += 2) {
            data[i / 2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }

        return data;
    }

    public static String bytesToHex(byte[] bytes) {
        String result = null;
        if (bytes != null) {
            char[] hexArray = "0123456789ABCDEF".toCharArray();
            char[] hexChars = new char[bytes.length * 2];

            for(int j = 0; j < bytes.length; ++j) {
                int v = bytes[j] & 255;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 15];
            }

            result = new String(hexChars);
        }

        return result;
    }

    public static byte[] encryptData(String data, PublicKey keyPL) throws IOException {
        byte[] dataToEncrypt = data.getBytes();
        byte[] encryptedData = null;

        try {
            @SuppressWarnings("squid:S5542") // hoặc "java:S5542" tùy rule ID
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(1, keyPL);
            encryptedData = cipher.doFinal(dataToEncrypt);
        } catch (Exception var6) {
            log.error("Error encrypting data: ", var6);
            var6.printStackTrace();
        }

        return encryptedData;
    }

    public static String decryptData(byte[] data, PrivateKey keyPr) throws IOException {
        String strDecreate = null;
        byte[] descryptedData = null;

        try {
            Security.addProvider(new BouncyCastleProvider());
            @SuppressWarnings("squid:S5542") // hoặc "java:S5542" tùy rule ID
            Cipher cipher = Cipher.getInstance("RSA/CBC/PKCS5Padding");
            cipher.init(2, keyPr);
            descryptedData = cipher.doFinal(data);
            strDecreate = new String(descryptedData);
        } catch (Exception var6) {
            log.error("Error decrypt data: ", var6);
            var6.printStackTrace();
        }

        return strDecreate;
    }

    public static PublicKey convertByteToPublicKey(byte[] publicKeyBytes) {
        PublicKey publicKey = null;

        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        } catch (InvalidKeySpecException var3) {
            log.error("InvalidKeySpecException: ", var3);
            var3.printStackTrace();
        } catch (NoSuchAlgorithmException var4) {
            log.error("NoSuchAlgorithmException: ", var4);
            var4.printStackTrace();
        }

        return publicKey;
    }

    public static PrivateKey convertByteToPrivateKey(byte[] privateKeyBytes) {
        PrivateKey privateKey = null;

        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        } catch (InvalidKeySpecException var3) {
            log.error("InvalidKeySpecException: ", var3);
            var3.printStackTrace();
        } catch (NoSuchAlgorithmException var4) {
            log.error("NoSuchAlgorithmException: ", var4);
            var4.printStackTrace();
        }

        return privateKey;
    }
}
