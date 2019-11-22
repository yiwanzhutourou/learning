package com.youdushufang.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class URLConnectionUtils {

    private URLConnectionUtils() {}

    public static void get(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    public static void post(URL url, Map<String, Object> params) throws IOException {
        URLConnection urlConnection = url.openConnection();
        urlConnection.setDoOutput(true);
        try (OutputStreamWriter out = new OutputStreamWriter(
                urlConnection.getOutputStream())) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                out.write(entry.getKey() + "=" + entry.getValue());
            }
        }

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
