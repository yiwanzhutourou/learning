package com.youdushufang.test.io;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.*;

class ObjectStreamTest {

    @Test
    void testObjectOutputStream() throws IOException {
        Person person = new Person("Warrior", 18, "123456");
        try (ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream("io_files/person")))) {
            out.writeObject(person);
        }
    }

    @Test
    void testObjectInputStream() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream("io_files/person")))) {
            Person person = (Person) in.readObject();
            System.out.println(person);
        }
    }

    @Test
    void testObjectStream() throws IOException, ClassNotFoundException {

        Person person = new Person("Warrior", 18, "123456");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream)) {
            out.writeObject(person);
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream(byteArrayOutputStream.toByteArray()))) {
            Person anotherPerson = (Person) in.readObject();
            System.out.println(anotherPerson);
            // will print false
            System.out.println(anotherPerson == person);
        }
    }

    @Test
    void testObjectStreamWriteTwice() throws IOException, ClassNotFoundException {

        Person person = new Person("Warrior", 18, "123456");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream)) {
            out.writeObject(person);
            out.writeObject(person);
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream(byteArrayOutputStream.toByteArray()))) {
            Person anotherPerson1 = (Person) in.readObject();
            Person anotherPerson2 = (Person) in.readObject();
            // will print true
            System.out.println(anotherPerson1 == anotherPerson2);
        }
    }

    @Data
    private static class Person implements Serializable {

        private static final long serialVersionUID = 1L;

        private String name;

        private Integer age;

        private transient String password;

        Person(String name, Integer age, String password) {
            this.name = name;
            this.age = age;
            this.password = password;
        }
    }
}
