package com.concurrent.program.thread.base;

/**
 * Created on 2020-08-29
 */
public class DeadLockTest {

    // 1. 创建共享资源
    private static final Object resourceA = new Object();
    private static final Object resourceB = new Object();

    /**
     * Thread[Thread-0,5,main] get ResourceA
     * Thread[Thread-1,5,main] get ResourceB
     * Thread[Thread-0,5,main]waiting get ResourceB
     * Thread[Thread-1,5,main]waiting get ResourceA
     */
    public static void main(String[] args) {

        // 2. 创建线程A
        Thread threadA = new Thread(() -> {
            // 2.1获取资源A的锁
            synchronized (resourceA) {
                System.out.println(Thread.currentThread() + " get ResourceA");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 2.1获取资源B的锁
                System.out.println(Thread.currentThread() + "waiting get ResourceB");
                synchronized (resourceB) {
                    System.out.println(Thread.currentThread() + "get ResourceB");
                }
            }
        });

        // 3. 创建线程B
        Thread threadB = new Thread(() -> {
            // 3.1获取资源B的锁
            synchronized (resourceB) {
                System.out.println(Thread.currentThread() + " get ResourceB");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 3.2获取资源A的锁
                System.out.println(Thread.currentThread() + "waiting get ResourceA");
                synchronized (resourceA) {
                    System.out.println(Thread.currentThread() + "get ResourceA");
                }
            }
        });

        // 3. 启动线程
        threadA.start();
        threadB.start();
    }
}
