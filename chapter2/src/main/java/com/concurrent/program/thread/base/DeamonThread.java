package com.concurrent.program.thread.base;

/**
 * Created on 2020-08-29
 */
public class DeamonThread {

    /**
     * daemonThread.setDaemon(false); default
     * hello, I am running.
     * hello, I am running.
     * hello, I am running.
     * hello, I am running.
     * ....
     *
     *
     * daemonThread.setDaemon(true);
     * nothing show
     */
    public static void main(String[] args) {
        // 1.创建线程
        Thread daemonThread = new Thread(() -> {
            for (; ; ) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("hello, I am running.");
            }
        });

        // 2.设置为守护线程，并启动
//        daemonThread.setDaemon(true);
        daemonThread.start();
    }

}
