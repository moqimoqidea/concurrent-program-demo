package com.concurrent.program.thread.base;

/**
 * Created on 2020-08-29
 */
public class TestThreadLocalNoInheritable {

    // (1) 创建线程变量
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * main:hello world
     * thread:null
     */
    public static void main(String[] args) {
        // (2)  设置线程变量
        threadLocal.set("hello world");
        // (3) 启动子线程
        Thread thread = new Thread(() -> {
            // (4)子线程输出线程变量的值
            System.out.println("thread:" + threadLocal.get());
        });
        thread.start();

        // (5)主线程输出线程变量值
        System.out.println("main:" + threadLocal.get());
    }

}
