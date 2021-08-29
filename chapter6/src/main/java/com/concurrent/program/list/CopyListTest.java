package com.concurrent.program.list;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyListTest {

    private static final CopyOnWriteArrayList<String> LIST = new CopyOnWriteArrayList<>();

    /**
     * tom
     * smith
     * json
     */
    public static void main(String[] args) throws InterruptedException {

        LIST.add("tom");
        LIST.add("smith");
        LIST.add("json");

        Thread changeListThread = new Thread(() -> {
            LIST.set(1, "timo");
            LIST.remove(0);
        });

        // 在子线程更改前获取迭代器，验证迭代器无法接收到更改
        Iterator<String> iterator = LIST.iterator();
        changeListThread.start();
        changeListThread.join();

        iterator.forEachRemaining(System.out::println);
    }

}
