package com.youdushufang.test.network;

import com.youdushufang.network.URLConnectionUtils;
import com.youdushufang.network.URLUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

class SimpleNetworkTest {

    @Test
    void testGetFromURL() throws IOException {
        URL url = new URL("https://www.oracle.com/");
        URLUtils.get(url);
    }

    @Test
    void testGetFromURLConnection() throws IOException {
        URL url = new URL("https://www.oracle.com/");
        URLConnectionUtils.get(url);
    }

    @Test
    void testPostToURLConnection() throws IOException {
        URL url = new URL("http://localhost:8080/test/ReverseServlet");
        Map<String, Object> params = new HashMap<>();
        params.put("string", "string_to_reverse");
        URLConnectionUtils.post(url, params);
    }
}
