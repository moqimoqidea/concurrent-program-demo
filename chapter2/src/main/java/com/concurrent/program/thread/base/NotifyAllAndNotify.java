package com.concurrent.program.thread.base;

/**
 * Created on 2020-08-29
 */
public class NotifyAllAndNotify {

    // 1. 创建共享资源
    private static final Object resourceA = new Object();

    /**
     * threadA get resourceA lock
     * threadA begin wait
     * threadB get resourceA lock
     * threadB begin wait
     * threadC begin notify
     * threadB end wait
     * threadA end wait
     * main over
     */
    public static void main(String[] args) throws InterruptedException {
        // 2. 创建线程A，模拟wait
        Thread threadA = new Thread(() -> {

            // 2.1 获取resourceA共享资源的监视器锁
            synchronized (resourceA) {
                System.out.println("threadA get resourceA lock");
                try {
                    System.out.println("threadA begin wait");
                    resourceA.wait();
                    System.out.println("threadA end wait");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 3. 创建线程B,模拟wait
        Thread threadB = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println("threadB get resourceA lock");
                try {

                    System.out.println("threadB begin wait");
                    resourceA.wait();
                    System.out.println("threadB end wait");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 3. 创建线程C,通知等待的线程
        Thread threadC = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println("threadC begin notify");
                resourceA.notifyAll();
                // resourceA.notify();
            }
        });

        // 4. 启动线程
        threadA.start();
        threadB.start();
        Thread.sleep(1000);
        threadC.start();

        // 4. 等待线程结束
        threadA.join();
        threadB.join();
        threadC.join();
        System.out.println("main over");
    }

}
