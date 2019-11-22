package com.youdushufang.test.network;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

class URLTest {

    @Test
    void testURL() throws URISyntaxException, MalformedURLException {
        URI uri = new URI("https",
                "example.com",
                "/pages/8",
                "id=1&name=foo",
                "BOTTOM");
        URL url = uri.toURL();

        System.out.format("url = %s%n", url.toString());
        System.out.format("protocol = %s%n", url.getProtocol());
        System.out.format("authority = %s%n", url.getAuthority());
        System.out.format("host = %s%n", url.getHost());
        System.out.format("port = %s%n", url.getPort());
        System.out.format("default port = %s%n", url.getDefaultPort());
        System.out.format("path = %s%n", url.getPath());
        System.out.format("query = %s%n", url.getQuery());
        System.out.format("file = %s%n", url.getFile());
        System.out.format("ref = %s%n", url.getRef());
    }
}
