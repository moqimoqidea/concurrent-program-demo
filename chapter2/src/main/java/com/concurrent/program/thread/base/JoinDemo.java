package com.concurrent.program.thread.base;

/**
 * Created on 2020-08-29
 */
public class JoinDemo {

    /**
     * wait all child thread over!
     * child threadOne over!
     * child threadTwo over!
     * all child thread over!
     */
    public static void main(String[] args) throws InterruptedException {
        // 1.创建线程1，模拟执行任务
        Thread threadOne = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("child threadOne over!");

        });

        // 2.创建线程2，模拟执行任务
        Thread threadTwo = new Thread(() -> {

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("child threadTwo over!");


        });


        // 3.两个线程执行
        threadOne.start();
        threadTwo.start();

        System.out.println("wait all child thread over!");

        // 3.等待子线程执行完毕，返回
        threadOne.join();
        threadTwo.join();

        System.out.println("all child thread over!");

    }

}
