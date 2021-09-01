package com.concurrent.program.sync.tool;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2020-08-29
 */
public class CycleBarrierTest2 {

    // 创建一个CyclicBarrier实例
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    /**
     * Thread[pool-1-thread-1,5,main] step1
     * Thread[pool-1-thread-2,5,main] step1
     * Thread[pool-1-thread-2,5,main] step2
     * Thread[pool-1-thread-1,5,main] step2
     * Thread[pool-1-thread-1,5,main] step3
     * Thread[pool-1-thread-2,5,main] step3
     */
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 加入线程A到线程池
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread() + " step1");
                cyclicBarrier.await();

                System.out.println(Thread.currentThread() + " step2");
                cyclicBarrier.await();

                System.out.println(Thread.currentThread() + " step3");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 加入线程B到线程池
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread() + " step1");
                cyclicBarrier.await();

                System.out.println(Thread.currentThread() + " step2");
                cyclicBarrier.await();

                System.out.println(Thread.currentThread() + " step3");


            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 关闭线程池
        executorService.shutdown();
    }
}
