package com.concurrent.program.in.action;

/**
 * Created on 2020-08-29
 */
public class ThreadUseName {

    static final String THREAD_SAVE_ORDER = "THREAD_SAVE_ORDER";
    static final String THREAD_SAVE_ADDR = "THREAD_SAVE_ADDR";

    /**
     * 保存订单的线程
     * 保存收货地址的线程
     * Exception in thread "THREAD_SAVE_ORDER" java.lang.NullPointerException
     * 	at com.concurrent.program.in.action.ThreadUseName.lambda$main$0(ThreadUseName.java:16)
     */
    public static void main(String[] args) {

        // 订单模块
        Thread threadOne = new Thread(() -> {
            System.out.println("保存订单的线程");
            throw new NullPointerException();
        }, THREAD_SAVE_ORDER);
        // 发货模块
        Thread threadTwo = new Thread(() -> System.out.println("保存收货地址的线程"), THREAD_SAVE_ADDR);

        threadOne.start();
        threadTwo.start();

    }

}
