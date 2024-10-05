package me.tolek.network;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

public class MflpServerConnection {

    private static final String SERVER_IP = "https://epsi.ddns.net"; // Change to your server's IP
    private static final int PORT = 3000;
    private static final String BASE_URL = SERVER_IP + ":" + PORT;

    private void disableSSLCertificateChecking() throws Exception {
        // Create a TrustManager that does not validate certificate chains
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

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create an all-trusting host name verifier
        HostnameVerifier allHostsValid = (hostname, session) -> true;

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    public String sendGetRequest(String endpoint) throws Exception {
        // Create the full URL
        disableSSLCertificateChecking();

        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(1 * 1000);
        connection.setReadTimeout(1 * 1000);

        // Set the request method to GET
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        // Read the response
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

        // Send the JSON data in the request body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = username.getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
        }

        // Read the response
        return getResponse(connection);
    }

    // Function to send a PUT request
    public String sendPostRequest(String endpoint, String jsonInput) throws Exception {
        disableSSLCertificateChecking();

        // Create the full URL
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(1 * 1000);
        connection.setReadTimeout(1 * 1000);

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Send the JSON data in the request body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
        }

        // Read the response
        return getResponse(connection);
    }

    // Function to handle the response and read it as a String
    private String getResponse(HttpURLConnection connection) {
        try {
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                // Read the response from the input stream
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    return content.toString();
                }
            } else {
                // Handle errors
                System.out.println("Server returned HTTP response code: " + responseCode);
            }
        } catch (Exception ignored) {

        }
        return null;
    }

}
