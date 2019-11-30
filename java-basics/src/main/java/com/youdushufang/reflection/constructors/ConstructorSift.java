package com.youdushufang.reflection.constructors;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;

import static java.lang.System.out;

public class ConstructorSift {

    public static void main(String[] args) {
        listConstructors(ConstructorSift.class);
        listConstructors(HashMap.class);
    }

    private static void listConstructors(Class<?> c) {
        Constructor[] allConstructors = c.getDeclaredConstructors();
        for (Constructor constructor : allConstructors) {
            out.format("%s%n", constructor.toGenericString());
            Type[] types = constructor.getGenericParameterTypes();
            for (Type type : types) {
                out.format("%s: %s%n", "GenericParameterType", type);
            }
        }
    }
}
