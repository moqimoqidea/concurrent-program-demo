package com.concurrent.program.lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest4 {

    /**
     * begin park
     * end park
     */
    public static void main(String[] args) {
        System.out.println("begin park");
        final Thread currentThread = Thread.currentThread();
        LockSupport.unpark(currentThread);

        LockSupport.park();
        System.out.println("end park");
    }

}
