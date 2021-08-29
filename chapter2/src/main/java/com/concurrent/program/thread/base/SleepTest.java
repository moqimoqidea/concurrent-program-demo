package com.concurrent.program.thread.base;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 2020-08-29
 */
public class SleepTest {

    // 1. 创建一个独占锁
    private static final Lock lock = new ReentrantLock();

    /**
     * child threadA is in sleep
     * child threadA is in awake
     * child threadB is in sleep
     * child threadB is in awake
     */
    public static void main(String[] args) throws InterruptedException {

        // 2. 创建线程A
        Thread threadA = new Thread(() -> {
            // 获取独占锁
            lock.lock();
            try {
                System.out.println("child threadA is in sleep");
                Thread.sleep(10000);
                System.out.println("child threadA is in awake");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放锁
                lock.unlock();
            }
        });

        // 2. 创建线程B
        Thread threadB = new Thread(() -> {
            // 获取独占锁
            lock.lock();
            try {
                System.out.println("child threadB is in sleep");
                Thread.sleep(10000);
                System.out.println("child threadB is in awake");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放锁
                lock.unlock();
            }
        });

        // 启动线程
        threadA.start();
        threadB.start();
    }

}