package com.youdushufang;

import org.junit.jupiter.api.Test;

class ConstructorTest {

    @Test
    void testExecuteOrder() {
        new Son();
        new Son();
    }


    private static class Parent {
        static {
            System.out.println("I'm your static father, son");
        }

        Parent() {
            System.out.println("I'm your father, son");
        }
    }

    private static class Son extends Parent {
        static {
            System.out.println("I am a static son");
        }

        Son() {
            System.out.println("I am a son");
        }
    }
}
