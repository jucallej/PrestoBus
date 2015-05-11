package com.fdi.ucm.prestobus;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Julián and Jesús
 * More info on the REEDME
 * Credit: Credit: http://blog.denevell.org/android-trust-all-ssl-certificates.html
 */

/**
 * La API de la EMT está en un servidor que tiene problemas con sus certificados (estarán firmados por ellos mismos, o no los habrán renovado).
 * Esta clase se encarga de que podamos ignorar los certificados en conexiones con https (no mandamos datos especialmente sensibles, así que aceptamos esto como decisión de diseño)
 */
public final class NukeSSLCerts {
    protected static final String TAG = "NukeSSLCerts";

    /**
     * Metodo que se encarga de que podamos ignorar los certificados en conexiones con https (no mandamos datos especialmente sensibles, así que aceptamos esto como decisión de diseño)
     */
    public static void nuke() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }
}