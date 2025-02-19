package com.concurrent.program.thread.base;

/**
 * Created on 2020-08-29
 */
public class InterruptJoinThreadDemo {

    /**
     * threadOne begin run!
     * main thread:java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // 1.线程one,模拟任务
        Thread threadOne = new Thread(() -> {
            System.out.println("threadOne begin run!");
            for (; ; ) {
            }
        });

        // 2.获取主线程
        final Thread mainThread = Thread.currentThread();

        // 3.线程two 模拟中断主线程
        Thread threadTwo = new Thread(() -> {
            // 休眠1s
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 中断主线程
            mainThread.interrupt();
        });

        // 4. 启动子线程
        threadOne.start();
        // 延迟1s启动线程
        threadTwo.start();

        // 5. 等待线程one执行完毕
        try {
            threadOne.join();
        } catch (InterruptedException e) {
            System.out.println("main thread:" + e);
        }
    }

}
