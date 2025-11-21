//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.synergy.utils.voffice;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
@Slf4j
public class AES {
    private static AES aes;
    private Cipher cipher;
    private byte[] _key;
    private byte[] _iv;

    @SuppressWarnings("squid:S5542") // hoặc "java:S5542" tùy rule ID
    private AES() {
        try {
            this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            Security.addProvider(new BouncyCastleProvider());
            this._key = new byte[16];
            this._iv = new byte[16];
        } catch (NoSuchAlgorithmException var2) {
            log.error("NoSuchAlgorithmException: ", var2);
            this.cipher = null;
            var2.printStackTrace();
        } catch (NoSuchPaddingException var3) {
            log.error("NoSuchPaddingException: ", var3);
            this.cipher = null;
            var3.printStackTrace();
        }

    }

    public static synchronized AES getInstance() {
        if (aes == null) {
            aes = new AES();
        }

        return aes;
    }

//    public static final String md5(String inputString) {
////        String MD5 = "MD5";
//
//        try {
//            MessageDigest digest = MessageDigest.getInstance("MD5");
//            digest.update(inputString.getBytes());
//            byte[] messageDigest = digest.digest();
//            StringBuilder hexString = new StringBuilder();
//            byte[] arr$ = messageDigest;
//            int len$ = messageDigest.length;
//
//            for(int i$ = 0; i$ < len$; ++i$) {
//                byte aMessageDigest = arr$[i$];
//
//                String h;
//                for(h = Integer.toHexString(255 & aMessageDigest); h.length() < 2; h = "0" + h) {
//                }
//
//                hexString.append(h);
//            }
//
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException var10) {
//            var10.printStackTrace();
//            return "";
//        }
//    }

    private String encryptDecrypt(String _inputText, String _encryptionKey, EncryptMode _mode, String _initVector) throws UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        String _out = "";
        int len = _encryptionKey.getBytes("UTF-8").length;
        if (_encryptionKey.getBytes("UTF-8").length > this._key.length) {
            len = this._key.length;
        }

        int ivlen = _initVector.getBytes("UTF-8").length;
        if (_initVector.getBytes("UTF-8").length > this._iv.length) {
            ivlen = this._iv.length;
        }

        System.arraycopy(_encryptionKey.getBytes("UTF-8"), 0, this._key, 0, len);
        System.arraycopy(_initVector.getBytes("UTF-8"), 0, this._iv, 0, ivlen);
        SecretKeySpec keySpec = new SecretKeySpec(this._key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(this._iv);
        byte[] decodedValue;
        if (_mode.equals(EncryptMode.ENCRYPT)) {
            this.cipher.init(1, keySpec, ivSpec);
            decodedValue = this.cipher.doFinal(_inputText.getBytes("UTF-8"));
            _out = FunctionCommon.bytesToHex(decodedValue);
        }

        if (_mode.equals(EncryptMode.DECRYPT)) {
            this.cipher.init(2, keySpec, ivSpec);
            decodedValue = FunctionCommon.hexStringToByteArray(_inputText);
            byte[] decryptedVal = this.cipher.doFinal(decodedValue);
            _out = new String(decryptedVal, "UTF-8");
        }

        return _out;
    }

    public static String SHA256(String text, int length) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes("UTF-8"));
        byte[] digest = md.digest();
        StringBuffer result = new StringBuffer();
        byte[] arr$ = digest;
        int len$ = digest.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            byte b = arr$[i$];
            result.append(String.format("%02x", b));
        }

        String resultStr;
        if (length > result.toString().length()) {
            resultStr = result.toString();
        } else {
            resultStr = result.toString().substring(0, length);
        }

        return resultStr;
    }

    public synchronized String encrypt(String _plainText, String _key, String _iv) {
        String strResult = "";

        try {
            strResult = this.encryptDecrypt(_plainText, _key, EncryptMode.ENCRYPT, _iv);
        } catch (InvalidKeyException var6) {
            log.error("InvalidKeyException: ", var6);
            strResult = null;
            var6.printStackTrace();
        } catch (UnsupportedEncodingException var7) {
            log.error("UnsupportedEncodingException: ", var7);
            strResult = null;
            var7.printStackTrace();
        } catch (InvalidAlgorithmParameterException var8) {
            log.error("InvalidAlgorithmParameterException: ", var8);
            strResult = null;
            var8.printStackTrace();
        } catch (IllegalBlockSizeException var9) {
            log.error("IllegalBlockSizeException: ", var9);
            strResult = null;
            var9.printStackTrace();
        } catch (BadPaddingException var10) {
            log.error("BadPaddingException: ", var10);
            strResult = null;
            var10.printStackTrace();
        }

        return strResult;
    }

    public synchronized String decrypt(String _encryptedText, String _key, String _iv) {
        String strResult = "";

        try {
            if (_encryptedText != null && !_encryptedText.isEmpty()) {
                if (_encryptedText.contains("{")) {
                    String checkEmpty = _encryptedText.replace("{", "").replace("}", "");
                    if (!checkEmpty.isEmpty()) {
                        strResult = this.encryptDecrypt(_encryptedText, _key, EncryptMode.DECRYPT, _iv);
                    }
                } else {
                    strResult = this.encryptDecrypt(_encryptedText, _key, EncryptMode.DECRYPT, _iv);
                }
            } else {
                strResult = null;
            }
        } catch (InvalidKeyException var6) {
            log.error("InvalidKeyException: ", var6);
            strResult = null;
            var6.printStackTrace();
        } catch (UnsupportedEncodingException var7) {
            log.error("UnsupportedEncodingException: ", var7);
            strResult = null;
            var7.printStackTrace();
        } catch (InvalidAlgorithmParameterException var8) {
            log.error("InvalidAlgorithmParameterException: ", var8);
            strResult = null;
            var8.printStackTrace();
        } catch (IllegalBlockSizeException var9) {
            log.error("IllegalBlockSizeException: ", var9);
            strResult = null;
            var9.printStackTrace();
        } catch (BadPaddingException var10) {
            log.error("BadPaddingException: ", var10);
            strResult = null;
            var10.printStackTrace();
        }

        return strResult;
    }

    public static String generateRandomIV(int length) {
        SecureRandom ranGen = new SecureRandom();
        byte[] aesKey = new byte[16];
        ranGen.nextBytes(aesKey);
        StringBuffer result = new StringBuffer();
        byte[] arr$ = aesKey;
        int len$ = aesKey.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            byte b = arr$[i$];
            result.append(String.format("%02x", b));
        }

        return length > result.toString().length() ? result.toString() : result.toString().substring(0, length);
    }

    public static String createAesKey() {
        String result = "";

        try {
            String key = SHA256(FunctionCommon.createTokenRandom(), 32);
            String iv = generateRandomIV(16);
            result = key + "VIAESKEYSPACE" + iv;
        } catch (NoSuchAlgorithmException var3) {
            log.error("NoSuchAlgorithmException: ", var3);
            result = null;
            var3.printStackTrace();
        } catch (UnsupportedEncodingException var4) {
            log.error("UnsupportedEncodingException: ", var4);
            result = null;
            var4.printStackTrace();
        }

        return result;
    }

    private static enum EncryptMode {
        ENCRYPT,
        DECRYPT;

        private EncryptMode() {
        }
    }
}
