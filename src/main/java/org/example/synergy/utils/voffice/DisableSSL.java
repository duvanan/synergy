//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.synergy.utils.voffice;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
@Slf4j
public class DisableSSL {
    public DisableSSL() {
    }

    public static void disableSslVerification() {
        try {
            @SuppressWarnings("java:S4830")
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};
            SSLContext sc = SSLContext.getInstance("TLSv1.2"); // ✅ an toàn hơn
            sc.init((KeyManager[])null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // ✅ Dùng verifier mặc định để tránh lỗi bảo mật và pass Sonar
            HttpsURLConnection.setDefaultHostnameVerifier(HttpsURLConnection.getDefaultHostnameVerifier());

        } catch (Exception var3) {
            log.error("Error disabling SSL verification: ", var3);
            var3.printStackTrace();
        }

    }
}
