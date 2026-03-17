package com.nandbox.bots.api.util;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.eclipse.jetty.util.BlockingArrayQueue;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpService {

    private static final BlockingQueue<Runnable> queue = new BlockingArrayQueue<>(500, 500);
    private static final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(20, 20, 500, TimeUnit.SECONDS, queue);

    // ===== PUBLIC METHODS =====

    public static void get(String uri, JSONObject headers, String auth, HttpCallback callback) {
        execute("GET", uri, null, headers, auth, callback);
    }

    public static void post(String uri, Object body, JSONObject headers, String auth, HttpCallback callback) {
        execute("POST", uri, body, headers, auth, callback);
    }

    public static void put(String uri, Object body, JSONObject headers, String auth, HttpCallback callback) {
        execute("PUT", uri, body, headers, auth, callback);
    }

    public static void delete(String uri, JSONObject headers, String auth, HttpCallback callback) {
        execute("DELETE", uri, null, headers, auth, callback);
    }

    // ===== CORE EXECUTION =====

    private static void execute(String method,
                         String uri,
                         Object body,
                         JSONObject headers,
                         String auth,
                         HttpCallback callback) {

        executor.execute(() -> {
            HttpURLConnection conn = null;

            try {
                URL url = new URL(uri);
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod(method);
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(15000);

                // ===== Headers =====
                if (headers != null) {
                    Iterator<String> keys = headers.keySet().iterator();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        conn.setRequestProperty(key, String.valueOf(headers.get(key)));
                    }
                }

                // ===== Auth =====
                if (auth != null) {
                    conn.setRequestProperty("Authorization", auth);
                }

                // ===== Body =====
                if (body != null) {
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json");

                    String jsonString;
                    if (body instanceof JSONObject) {
                        jsonString = ((JSONObject) body).toString();
                    } else if (body instanceof JSONArray) {
                        jsonString = ((JSONArray) body).toString();
                    } else {
                        throw new IllegalArgumentException("Body must be JSONObject or JSONArray");
                    }

                    try (OutputStream os = conn.getOutputStream()) {
                        os.write(jsonString.getBytes(StandardCharsets.UTF_8));
                    }
                }

                // ===== Response =====
                int status = conn.getResponseCode();

                InputStream is = (status >= 200 && status < 300)
                        ? conn.getInputStream()
                        : conn.getErrorStream();

                String response = readStream(is);

                if (callback != null) {
                    callback.onSuccess(status, response);
                }

            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        });
    }

    private static String readStream(InputStream is) throws IOException {
        if (is == null) return "";

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        return response.toString();
    }

    // ===== CALLBACK INTERFACE =====

    public interface HttpCallback {
        void onSuccess(int statusCode, String response);
        void onError(Exception e);
    }
}