package com.youdushufang.reflection.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static java.lang.System.out;

public class MethodModifierSpy {

    private static int count;
    private static synchronized void clear() { count = 0; }
    private static synchronized void inc() { count++; }
    private static synchronized int cnt() { return count; }

    public static void main(String[] args) {
        printMethodModifiers(Object.class, "wait");
        printSeparator();
        printMethodModifiers(MethodModifierSpy.class, "inc");
        printSeparator();
        printMethodModifiers(String.class, "compareTo");
    }

    private static void printMethodModifiers(Class<?> c, String methodName) {
        clear();
        Method[] allMethods = c.getDeclaredMethods();
        for (Method m : allMethods) {
            if (!methodName.equals(m.getName())) {
                continue;
            }
            out.format("%s%n", m.toGenericString());
            out.format("  Modifiers:  %s%n",
                    Modifier.toString(m.getModifiers()));
            out.format("  [ synthetic=%-5b var_args=%-5b bridge=%-5b ]%n",
                    m.isSynthetic(), m.isVarArgs(), m.isBridge());
            inc();
        }
        out.format("%d matching overload%s found%n", cnt(),
                (cnt() == 1 ? "" : "s"));
    }

    private static void printSeparator() {
        out.println();
        out.println("**********");
        out.println();
    }
}
