package com.youdushufang.reflection.classes;

import com.youdushufang.reflection.SuperClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static java.lang.System.out;

public class GetClass extends SuperClass {

    public static void main(String[] args) {
        // 通过 Object.getClass() 方法获取 Class 对象
        out.println("foo".getClass());
        out.println(Type.A.getClass());
        byte[] bytes = new byte[1024];
        out.println(bytes.getClass());

        // 通过 .class 获取 Class 对象
        out.println(boolean.class);
        out.println(Integer[].class);
        out.println(Integer[][].class);

        // 通过 Class.forName() 获取 Class 对象
        try {
            out.println(Class.forName("com.youdushufang.reflection.classes.GetClass"));
            out.println(Class.forName("[[Ljava.lang.String;"));
            out.println(Class.forName("[D"));
            out.println(Class.forName("[D") == double[].class);
            Class<?> clazz1 = Class.forName("com.youdushufang.reflection.classes.GetClass",
                    true, new MyClassLoader());
            Class<?> clazz2 = Class.forName("com.youdushufang.reflection.classes.GetClass");
            // 不同的类加载器加载的类的 Class 对象不同
            out.format("clazz1 == clazz2? %b%n", clazz1 == clazz2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        out.println(void.class);
        out.println(void.class == Void.TYPE);
        out.println(int.class == Integer.TYPE);

        // 获取父类
        out.println(GetClass.class.getSuperclass());
        // 获取公共内部类、接口、enum（包括继承的）
        out.println(Arrays.toString(GetClass.class.getClasses()));
        // 获取所有内部类、接口、enum（不包括继承的）
        out.println(Arrays.toString(GetClass.class.getDeclaredClasses()));
        try {
            // 返回定义这个方法、成员变量、类的类
            out.println(GetClass.class.getField("o").getDeclaringClass());
            out.println(GetClass.class.getMethod("main", String[].class).getDeclaringClass());
            out.println(PrivateInnerClass.class.getDeclaringClass());
            // 内部匿名类无法获取 declaring class，下一句输出为 null
            out.println(o.getClass().getDeclaringClass());
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        // 返回直接外部类
        out.println(PublicInnerClass.class.getEnclosingClass());
        // 内部匿名类可以获取直接外部类
        out.println(o.getClass().getEnclosingClass());
    }

    public static Object o = new Object() { };

    public static class PublicInnerClass { }

    private static class PrivateInnerClass { }

    private enum Type {
        A
    }

    private static class MyClassLoader extends ClassLoader {
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            try {
                String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                InputStream is = getClass().getResourceAsStream(fileName);
                if (is == null) {
                    return super.loadClass(name);
                }
                byte[] bytes = new byte[is.available()];
                is.read(bytes);
                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                throw new ClassNotFoundException(name);
            }
        }
    }
}
