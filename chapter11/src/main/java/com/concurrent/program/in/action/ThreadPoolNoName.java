package com.concurrent.program.in.action;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2020-08-29
 */
public class ThreadPoolNoName {

    static ThreadPoolExecutor executorOne =
            new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(10));
    static ThreadPoolExecutor executorTwo =
            new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(10));

    /**
     * 接受用户链接线程
     * 具体处理业务请求线程
     * Exception in thread "pool-1-thread-1" java.lang.NullPointerException
     * 	at com.concurrent.program.in.action.ThreadPoolNoName.lambda$main$0(ThreadPoolNoName.java:23)
     */
    public static void main(String[] args) {

        // 接受用户链接模块
        executorOne.execute(() -> {
            System.out.println("接受用户链接线程");
            throw new NullPointerException();
        });
        // 具体处理用户请求模块
        executorTwo.execute(() -> System.out.println("具体处理业务请求线程"));

        executorOne.shutdown();
        executorTwo.shutdown();
    }
}
