package com.concurrent.program.in.action;

import java.util.concurrent.*;

/**
 * Created on 2020-08-29
 */
public class FutureTest {

    // (1)线程池单个线程，线程池队列元素个数为1
    private final static ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.DiscardPolicy());

    public static class MyRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor e) {
            if (!e.isShutdown()) {
                if(null != runnable && runnable instanceof FutureTask){
                    ((FutureTask) runnable).cancel(true);
                }
            }
        }

    }


    /**
     * start runnable one
     * start runnable two
     * task one null
     * task two null
     * --------------------- hang ----------------------
     */
    public static void main(String[] args) throws Exception {

        // (2)添加任务one
        Future futureOne = executorService.submit(() -> {

            System.out.println("start runnable one");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // (3)添加任务two
        Future futureTwo = executorService.submit(() -> System.out.println("start runnable two"));

        // (4)添加任务three
        Future futureThree = null;
        try {
            futureThree = executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("start runnable three");
                }
            });
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }


        System.out.println("task one " + futureOne.get());// (5)等待任务one执行完毕
        System.out.println("task two " + futureTwo.get());// (6)等待任务two执行完毕
        System.out.println("task three " + (futureThree == null ? null : futureThree.get()));// (7)等待任务three执行完毕

        executorService.shutdown();// (8)关闭线程池，阻塞直到所有任务执行完毕
    }
}
