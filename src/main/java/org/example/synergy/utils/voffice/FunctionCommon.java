//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.synergy.utils.voffice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
@Slf4j
public class FunctionCommon {
    private static Gson gson = (new GsonBuilder()).serializeNulls().create();

    public FunctionCommon() {
    }

    public static String createTokenRandom() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        String token = Arrays.toString(bytes);
        return token;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];

        for(int j = 0; j < bytes.length; ++j) {
            int v = bytes[j] & 255;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 15];
        }

        return new String(hexChars);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];

        for(int i = 0; i < len; i += 2) {
            data[i / 2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }

        return data;
    }

    public static String convertValueToJson(Map<String, Object> params) {
        JSONObject json = new JSONObject();
        if (params != null) {
            Iterator i$ = params.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry<String, Object> param = (Map.Entry)i$.next();

                try {
                    json.put((String)param.getKey(), param.getValue());
                } catch (JSONException var5) {
                    log.error("Error converting value to JSON: ", var5);
                    var5.printStackTrace();
                }
            }
        }

        return json.toString();
    }

    public static Object getItemInJson(String item, String strJsonData) {
        Object result = null;

        try {
            JSONObject obj = new JSONObject(strJsonData);
            result = obj.get(item);
        } catch (Exception var4) {
            log.error("Error converting value to JSON: ", var4);
            var4.printStackTrace();
        }

        return result;
    }

    public static Object getDataByKeyJson(String dataRequest, String keyResult) {
        Object strResult = "";
        JSONObject jObject = null;

        try {
            jObject = new JSONObject(dataRequest);
            String[] arrKey = keyResult.split("\\.");

            for(int i = 0; i < arrKey.length - 1; ++i) {
                String strKey = arrKey[i];
                if (strKey != null && strKey.trim().length() > 0) {
                    jObject = jObject != null && !jObject.isNull(strKey) ? jObject.getJSONObject(strKey) : null;
                }
            }

            if (jObject != null) {
                strResult = !jObject.isNull(arrKey[arrKey.length - 1]) ? jObject.get(arrKey[arrKey.length - 1]) : "";
            }
        } catch (JSONException var7) {
            log.error("Error converting value to JSON: ", var7);
            var7.printStackTrace();
        }

        return strResult;
    }

    public static String encrypt(String plaintext) throws Exception {
        String hash = null;
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            if (md != null) {
                md.update(plaintext.getBytes("UTF-8"));
                byte[] raw = md.digest();
                hash = new String(Base64.encodeBase64(raw));
            }
        } catch (NoSuchAlgorithmException var4) {
            log.error("NoSuchAlgorithmException: ", var4);
            var4.printStackTrace();
        }

        return hash;
    }

    public static String toJsonString(Object obj) {
        String json = "";
        return obj == null ? json : gson.toJson(obj);
    }

    public static String dateToString(Date value) {
        if (value != null) {
            SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
            return ddMMyyyy.format(value);
        } else {
            return "";
        }
    }
}
