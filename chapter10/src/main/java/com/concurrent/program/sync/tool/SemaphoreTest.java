package com.concurrent.program.sync.tool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created on 2020-08-29
 */
public class SemaphoreTest {

    // 创建一个Semaphore实例
    private static final Semaphore semaphore = new Semaphore(0);

    /**
     * Thread[pool-1-thread-1,5,main] over
     * Thread[pool-1-thread-2,5,main] over
     * all child thread over!
     */
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 加入任务A到线程池
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread() + " over");
                semaphore.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 加入任务B到线程池
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread() + " over");
                semaphore.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        // 等待子线程执行完毕，返回
        semaphore.acquire(2);
        System.out.println("all child thread over!");

        // 关闭线程池
        executorService.shutdown();
    }
}
