package com.youdushufang.reflection.constructors;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class RestoreAliases {

    private static HashMap<String, String> defaultAliases = new HashMap<>();

    static {
        defaultAliases.put("Duke", "duke@i-love-java");
        defaultAliases.put("Fang", "fang@evil-jealous-twin");
    }

    public static void main(String[] args) {
        try {
            Constructor constructor = EmailAliases.class.getDeclaredConstructor(HashMap.class);
            constructor.setAccessible(true);
            EmailAliases email = (EmailAliases) constructor.newInstance(defaultAliases);
            email.printKeys();
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException x) {
            x.printStackTrace();
        }
    }
}
