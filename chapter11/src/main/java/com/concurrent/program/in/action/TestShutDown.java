package com.concurrent.program.in.action;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2020-08-29
 */
public class TestShutDown {

    static void asyncExecuteOne() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> System.out.println("--async execute one ---"));
        // executor.shutdown();

    }

    static void asynExecuteTwo() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> System.out.println("--async execute two ---"));
        // executor.shutdown();

    }

    /**
     * ---sync execute---
     * --async execute one ---
     * ---execute over---
     * --async execute two ---
     * ----------------------------- hang -----------------------------
     */
    public static void main(String[] args) {
        // (1)同步执行
        System.out.println("---sync execute---");
        // (2)异步执行操作one
        asyncExecuteOne();
        // (3)异步执行操作two
        asynExecuteTwo();
        // (4)执行完毕
        System.out.println("---execute over---");
    }

}
