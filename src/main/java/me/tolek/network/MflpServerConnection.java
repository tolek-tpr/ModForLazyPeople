package me.tolek.network;

import me.tolek.ModForLazyPeople;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

public class MflpServerConnection {

    private static final String SERVER_IP = "https://epsi.ddns.net";
    private static final int PORT = 3000;
    private static final String BASE_URL = SERVER_IP + ":" + PORT;

    private void disableSSLCertificateChecking() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HostnameVerifier allHostsValid = (hostname, session) -> true;

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    public String sendGetRequest(String endpoint) throws Exception {
        disableSSLCertificateChecking();

        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(1 * 1000);
        connection.setReadTimeout(1 * 1000);

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        return getResponse(connection);
    }

    public String sendDeleteRequest(String endpoint, String username) throws Exception {
        disableSSLCertificateChecking();

        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(1 * 1000);
        connection.setReadTimeout(1 * 1000);

        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = username.getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
        }

        return getResponse(connection);
    }
    
    public String sendPostRequest(String endpoint, String jsonInput) throws Exception {
        disableSSLCertificateChecking();

        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(1 * 1000);
        connection.setReadTimeout(1 * 1000);

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
        }

        return getResponse(connection);
    }

    private String getResponse(HttpURLConnection connection) {
        try {
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    return content.toString();
                }
            } else {
                ModForLazyPeople.LOGGER.info("MFLP Server returned HTTP response code: " + responseCode);
            }
        } catch (Exception ignored) {

        }
        return null;
    }

}
