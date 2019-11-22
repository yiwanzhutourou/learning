package com.youdushufang.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class URLUtils {

    private URLUtils() {}

    public static void get(URL url) throws IOException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
