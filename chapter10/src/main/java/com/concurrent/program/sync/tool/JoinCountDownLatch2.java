package com.concurrent.program.sync.tool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2020-08-29
 */
public class JoinCountDownLatch2 {

    // 创建一个CountDownLatch实例
    private static final CountDownLatch countDownLatch = new CountDownLatch(2);

    /**
     * wait all child thread over!
     * child threadOne over!
     * all child thread over!
     * child threadTwo over!
     */
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 加入线程A到线程池
        executorService.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println("child threadOne over!");
        });

        // 加入线程B到线程池
        executorService.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println("child threadTwo over!");
        });


        System.out.println("wait all child thread over!");

        // 等待子线程执行完毕，返回
        countDownLatch.await();

        System.out.println("all child thread over!");

        executorService.shutdown();
    }
}
