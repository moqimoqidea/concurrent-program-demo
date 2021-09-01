package com.concurrent.program.in.action;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2020-08-29
 */
public class TestScheduledThreadPoolExecutor {

    static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    /**
     * ---one Task---
     * ---two Task---
     * ---two Task---
     */
    public static void main(String[] args) {

        executor.schedule((Runnable) () -> {
            System.out.println("---one Task---");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("error ");
        }, 500, TimeUnit.MICROSECONDS);

        executor.schedule(() -> {
            for (int i = 0; i < 2; ++i) {
                System.out.println("---two Task---");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, 1000, TimeUnit.MICROSECONDS);

        executor.shutdown();
    }
}
