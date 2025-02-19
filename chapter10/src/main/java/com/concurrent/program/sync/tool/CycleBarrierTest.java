package com.concurrent.program.sync.tool;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2020-08-29
 */
public class CycleBarrierTest {

    // 创建一个CyclicBarrier实例,添加一个所有子线程全部到达屏障后执行的一个任务
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(2,
            () -> System.out.println(Thread.currentThread() + " task1 merge result")
    );

    /**
     * Thread[pool-1-thread-1,5,main] task1-1
     * Thread[pool-1-thread-2,5,main] task1-2
     * Thread[pool-1-thread-2,5,main] enter in barrier
     * Thread[pool-1-thread-1,5,main] enter in barrier
     * Thread[pool-1-thread-1,5,main] task1 merge result
     * Thread[pool-1-thread-1,5,main] enter out barrier
     * Thread[pool-1-thread-2,5,main] enter out barrier
     */
    public static void main(String[] args) {

        // 创建一个线程个数固定为2的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 加入线程A到线程池
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread() + " task1-1");
                System.out.println(Thread.currentThread() + " enter in barrier");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread() + " enter out barrier");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 加入线程B到线程池
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread() + " task1-2");
                System.out.println(Thread.currentThread() + " enter in barrier");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread() + " enter out barrier");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 关闭线程池
        executorService.shutdown();
    }

}
