package com.concurrent.program.lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    /**
     * begin park
     */
    public static void main(String[] args) {
        System.out.println("begin park");
        LockSupport.park();
        System.out.println("end park");
    }

}
