package com.concurrent.program.lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest2 {

    /**
     * begin park
     * start unpark with main thread
     * end unpark with main thread
     * end park
     */
    public static void main(String[] args) {
        System.out.println("begin park");
        final Thread currentThread = Thread.currentThread();

        Thread unparkThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("start unpark with main thread");
            LockSupport.unpark(currentThread);
            System.out.println("end unpark with main thread");
        });

        unparkThread.start();

        LockSupport.park();

        System.out.println("end park");
    }

}
