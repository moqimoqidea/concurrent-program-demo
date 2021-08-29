package com.concurrent.program.thread.base;

/**
 * Created on 2020-08-29
 */
public class YieldTest implements Runnable {

    /**
     * yield close:
     * Thread[Thread-0,5,main]yield cpu...
     * Thread[Thread-2,5,main]yield cpu...
     * Thread[Thread-2,5,main] is over
     * Thread[Thread-1,5,main]yield cpu...
     * Thread[Thread-1,5,main] is over
     * Thread[Thread-0,5,main] is over
     * <p>
     * yield open:
     * Thread[Thread-0,5,main]yield cpu...
     * Thread[Thread-1,5,main]yield cpu...
     * Thread[Thread-2,5,main]yield cpu...
     * Thread[Thread-1,5,main] is over
     * Thread[Thread-0,5,main] is over
     * Thread[Thread-2,5,main] is over
     */
    public static void main(String[] args) {
        // 创建并启动线程
        new Thread(new YieldTest()).start();
        new Thread(new YieldTest()).start();
        new Thread(new YieldTest()).start();
    }

    public void run() {

        for (int i = 0; i < 5; i++) {
            // 当i=0时候出让cpu执行权，放弃时间片，进行下一轮调度
            if (i == 0) {
                System.out.println(Thread.currentThread() + "yield cpu...");
                // 当前 出让cpu执行权，放弃时间片，进行下一轮调度
                Thread.yield();
            }
        }

        System.out.println(Thread.currentThread() + " is over");
    }
}

