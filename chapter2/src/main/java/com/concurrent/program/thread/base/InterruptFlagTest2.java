package com.concurrent.program.thread.base;

/**
 * Created on 2020-08-29
 */
public class InterruptFlagTest2 {

    /**
     * threadOne isInterrupted:false
     * main thread is over
     */
    public static void main(String[] args) throws InterruptedException {
        // 1.创建线程
        Thread threadOne = new Thread(() -> {

            // 中断标志为true时候会退出循环，并且清除中断标志
            while (!Thread.currentThread().interrupted()) {

            }

            System.out.println("threadOne isInterrupted:" + Thread.currentThread().isInterrupted());
        });

        // 2. 启动线程
        threadOne.start();

        // 3. 设置中断标志
        threadOne.interrupt();

        // 3.等待线程one退出
        threadOne.join();
        System.out.println("main thread is over");
    }

}
