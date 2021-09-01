package com.concurrent.program.in.action;

/**
 * Created on 2020-08-29
 */
public class ThreadNoName {

    /**
     * 保存收获地址的线程
     * 保存订单的线程
     * Exception in thread "Thread-0" java.lang.NullPointerException
     * 	at com.concurrent.program.in.action.ThreadNoName.lambda$main$0(ThreadNoName.java:17)
     */
    public static void main(String[] args) {

        // 订单模块
        Thread threadOne = new Thread(() -> {
            System.out.println("保存订单的线程");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new NullPointerException();
        });

        // 发货模块
        Thread threadTwo = new Thread(() -> System.out.println("保存收获地址的线程"));

        threadOne.start();
        threadTwo.start();
    }

}
