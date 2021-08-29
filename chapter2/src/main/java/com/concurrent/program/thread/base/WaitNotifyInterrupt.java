package com.concurrent.program.thread.base;

/**
 * Created on 2020-08-29
 * 当一个线程调用共享对象的wait()方法被阻塞挂起后，如果其它线程中断了该线程，
 * 则该线程会抛出InterruptedException异常后返回
 */
public class WaitNotifyInterrupt {
    static Object obj = new Object();

    /**
     * ---begin---
     * Exception in thread "Thread-0" java.lang.IllegalMonitorStateException
     * 	at java.lang.Object.wait(Native Method)
     * 	at java.lang.Object.wait(Object.java:502)
     * 	at com.concurrent.program.thread.base.WaitNotifyInterupt.lambda$main$0(WaitNotifyInterupt.java:18)
     * 	at java.lang.Thread.run(Thread.java:748)
     * ---begin interrupt threadA---
     * ---end interrupt threadA---
     */
    public static void main(String[] args) throws InterruptedException {
        // 1.创建线程
        Thread threadA = new Thread(() -> {
            try {
                System.out.println("---begin---");
                // 阻塞当前线程
                obj.wait();
                System.out.println("---end---");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();

        Thread.sleep(1000);

        System.out.println("---begin interrupt threadA---");
        threadA.interrupt();
        System.out.println("---end interrupt threadA---");
    }

}
