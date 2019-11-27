package com.youdushufang.dp.proxy;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ImageProxyTest {

    @Test
    public void testOperationsOnImageProxy() {
        ImageProxy imageProxy = new ImageProxy("imageFileName");
        imageProxy.draw(new Point().setX(100).setY(200));
        imageProxy.handleMouse(new Event().setType(0).setWhere(new Point().setX(10).setY(10)));
        System.out.println(imageProxy.getExtent());
    }

    @Test
    public void testSaveAndLoadImageProxy() throws IOException {
        ImageProxy imageProxy = new ImageProxy("imageFileName");
        imageProxy.draw(new Point().setX(100).setY(200));
        System.out.println(imageProxy.getExtent());
        System.out.println(imageProxy);
        // save image proxy
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            imageProxy.save(out);
            // load to another object
            ImageProxy another = new ImageProxy();
            try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
                another.load(in);
                System.out.println(another);
            }
            another.draw(new Point().setX(400).setY(500));
            System.out.println(another);
        }
    }
}
