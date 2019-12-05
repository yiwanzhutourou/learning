package com.youdushufang.dp.iterator;

import org.junit.jupiter.api.Test;

public class ArrayListTest {

    @Test
    public void testArrayList() {
        List<String> list = new ArrayList<>(2);
        list.add("abc");
        list.add("def");
        list.add("ghi");
        list.add("123456");
        System.out.println("list size = " + list.size());
        System.out.println("first element = " + list.get(0));
        Iterator<String> iterator = list.iterator();
        System.out.println("elements:");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
