package com.youdushufang.dp.proxy;

import java.io.*;

/**
 * 角色：Proxy，代理 Image 对象
 */
public class ImageProxy implements Graphic {

    private String fileName;

    // 缓存的图片大小
    private Point extent;

    // 实际的被代理对象，初始化时并不需要真的 new 出来
    private Image image;

    public ImageProxy() {
        this.extent = Point.ZERO;
    }

    public ImageProxy(String fileName) {
        this.fileName = fileName;
        this.extent = Point.ZERO;
    }

    @Override
    public void draw(Point where) {
        getImage().draw(where);
    }

    @Override
    public void handleMouse(Event event) {
        getImage().handleMouse(event);
    }

    @Override
    public Point getExtent() {
        if (Point.ZERO.equals(this.extent)) {
            this.extent = getImage().getExtent();
        }
        return this.extent;
    }

    @Override
    public void load(InputStream in) throws IOException {
        // 代理类按照自己的格式加载
        DataInputStream objectInputStream = in instanceof DataInputStream
                ? (DataInputStream) in : new DataInputStream(in);
        this.fileName = objectInputStream.readUTF();
        this.extent = new Point()
                .setX(objectInputStream.readInt())
                .setY(objectInputStream.readInt());

    }

    @Override
    public void save(OutputStream out) throws IOException {
        // 代理类实际上输出的是图片的文件名和缓存的图片大小
        DataOutputStream objectOutputStream = out instanceof DataOutputStream
                ? (DataOutputStream) out : new DataOutputStream(out);
        objectOutputStream.writeUTF(fileName);
        objectOutputStream.writeInt(extent.getX());
        objectOutputStream.writeInt(extent.getY());
    }

    private Image getImage() {
        if (this.image == null) {
            this.image = new Image(this.fileName);
        }
        return this.image;
    }

    @Override
    public String toString() {
        return "ImageProxy(fileName=" + fileName + ", extent=" + extent + ", image=" + image + ")";
    }
}
