package com.concurrent.program.thread.base;

/**
 * Created on 2020-08-29
 */
public class InterruptSleepThread {

    /**
     * child thread is in sleep
     * java.lang.InterruptedException: sleep interrupted
     */
    public static void main(String[] args) throws InterruptedException {
        // 1.创建子线程,并休眠10s
        Thread thread = new Thread(() -> {
            try {
                System.out.println("child thread is in sleep");
                Thread.sleep(10000);
                System.out.println("child thread is in awake");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 2.启动线程
        thread.start();

        // 3.主线程休眠2s
        Thread.sleep(2000);

        // 4.主线程中断子线程
        thread.interrupt();
    }

}
