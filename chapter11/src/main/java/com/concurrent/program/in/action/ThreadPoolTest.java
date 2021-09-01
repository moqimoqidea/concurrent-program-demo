package com.concurrent.program.in.action;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2020-08-29
 */
public class ThreadPoolTest {

    static class LocalVariable {
        private Long[] a = new Long[1024 * 1024];
    }

    // (1)
    final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(100));

    // (2)
    // 必须回收自定义的ThreadLocal变量，尤其在线程池场景下，线程经常会被复用，
    // 如果不清理自定义的 ThreadLocal变量，可能会影响后续业务逻辑和造成内存泄露等问题。
    // 尽量在代理中使用try-finally块进行回收。
    final static ThreadLocal<LocalVariable> localVariable = new ThreadLocal<>();

    /**
     * use local variable
     * use local variable
     * use local variable
     * ...
     */
    public static void main(String[] args) throws InterruptedException {
        // (3)
        for (int i = 0; i < 50; ++i) {
            poolExecutor.execute(() -> {
                // (4)
                localVariable.set(new LocalVariable());
                // (5)
                System.out.println("use local variable");
                // localVariable.remove();

            });

            Thread.sleep(1000);
        }
        // (6)
        System.out.println("pool execute over");
    }

}
