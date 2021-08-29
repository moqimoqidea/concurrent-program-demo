package com.concurrent.program.lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest3 {

    /**
     * begin park
     * start interrupt with main thread
     * end interrupt with main thread
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
            System.out.println("start interrupt main thread");
            currentThread.interrupt();
            System.out.println("end interrupt main thread");
        });

        unparkThread.start();

        LockSupport.park();

        System.out.println("end park");
    }

}
