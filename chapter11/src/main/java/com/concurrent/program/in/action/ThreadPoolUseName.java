package com.concurrent.program.in.action;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 2020-08-29
 */
public class ThreadPoolUseName {

    static ThreadPoolExecutor executorOne =
            new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10),
                    new NamedThreadFactory("ASYNC-ACCEPT-POOL"));
    static ThreadPoolExecutor executorTwo =
            new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10),
                    new NamedThreadFactory("ASYNC-PROCESS-POOL"));

    // 命名线程工厂
    static class NamedThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        NamedThreadFactory(String name) {

            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            if (null == name || name.isEmpty()) {
                name = "pool";
            }

            namePrefix = name + "-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    /**
     * 接受用户链接线程
     * 具体处理业务请求线程
     * Exception in thread "ASYNC-ACCEPT-POOL-1-thread-1" java.lang.NullPointerException
     * 	at com.concurrent.program.in.action.ThreadPoolUseName.lambda$main$0(ThreadPoolUseName.java:56)
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
