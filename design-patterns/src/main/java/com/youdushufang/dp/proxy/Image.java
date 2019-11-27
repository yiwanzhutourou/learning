package com.youdushufang.dp.proxy;

import lombok.ToString;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 角色：RealSubject，实际的被代理对象
 */
@ToString
public class Image implements Graphic {

    private Point extent;

    /**
     * @param fileName 图片存放的文件地址
     */
    public Image(String fileName) {
        this.extent = new Point().setX(300).setY(400);
    }

    @Override
    public void draw(Point where) {
        System.out.println("Image.draw, " + where);
    }

    @Override
    public void handleMouse(Event event) {
        System.out.println("Image.handleMouse, " + event);
    }

    @Override
    public Point getExtent() {
        return this.extent;
    }

    @Override
    public void load(InputStream in) {
        System.out.println("Image.load");
    }

    @Override
    public void save(OutputStream out) {
        System.out.println("Image.save");
    }
}
