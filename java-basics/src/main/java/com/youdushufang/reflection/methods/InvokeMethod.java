package com.youdushufang.reflection.methods;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Locale;

import static java.lang.System.err;
import static java.lang.System.out;

public class InvokeMethod {

    public static void main(String[] args) {
        invokeMethod(Deet.class, Locale.getDefault());
        invokeMethod(Deet.class, Locale.GERMANY);
        invokeMethod(InvokeMethod.class, Locale.CANADA_FRENCH);
    }

    private static void invokeMethod(Class<?> c, Locale locale) {
        try {
            Object t = c.newInstance();
            Method[] allMethods = c.getDeclaredMethods();
            for (Method m : allMethods) {
                String methodName = m.getName();
                // 调用 test 开头且返回值为 boolean 的函数
                if (!methodName.startsWith("test")
                        || (m.getGenericReturnType() != boolean.class)) {
                    continue;
                }
                // 调用只有一个 Locale 参数的函数
                Class[] pClass = m.getParameterTypes();
                if (pClass.length != 1
                        || !Locale.class.isAssignableFrom(pClass[0])) {
                    continue;
                }
                out.format("invoking %s()%n", methodName);
                try {
                    m.setAccessible(true);
                    Object o = m.invoke(t, locale);
                    out.format("%s() returned %b%n", methodName, o);
                } catch (InvocationTargetException x) {
                    Throwable cause = x.getCause();
                    err.format("invocation of %s failed: %s%n",
                            methodName, cause.getMessage());
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
