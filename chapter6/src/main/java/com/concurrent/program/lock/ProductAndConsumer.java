package com.concurrent.program.lock;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

/**
 * Created on 2020-08-29
 */
public class ProductAndConsumer {

    final static NonReentrantLock lock = new NonReentrantLock();
    final static Condition notFull = lock.newCondition();
    final static Condition notEmpty = lock.newCondition();

    final static Queue<String> queue = new LinkedBlockingQueue<>();
    final static int queueSize = 10;

    /**
     * data time: 1630251962391
     * data time: 1630251963391
     * data time: 1630251964391
     * data time: 1630251965392
     * data time: 1630251966393
     * data time: 1630251967394
     * data time: 1630251968396
     * data time: 1630251969400
     * data time: 1630251970404
     * ......
     */
    public static void main(String[] args) {

        Thread producer = new Thread(() -> {
            for (; ; ) {
                // 获取独占锁
                lock.lock();
                try {
                    Thread.sleep(1000);
                    // 如果队列满了，则等待(1)
                    while (queue.size() == queueSize) {
                        notEmpty.await();
                    }

                    // 添加元素到队列（2）
                    queue.add("data time: " + System.currentTimeMillis());

                    // 唤醒消费线程（3）
                    notFull.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 释放锁
                    lock.unlock();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (; ; ) {
                // 获取独占锁
                lock.lock();
                try {
                    Thread.sleep(1000);
                    // 队列空，则等待
                    while (0 == queue.size()) {
                        notFull.await();
                    }

                    // 消费一个元素
                    System.out.println(queue.poll());
                    // 唤醒生产线程
                    notEmpty.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 释放锁
                    lock.unlock();
                }
            }

        });

        // 启动线程
        producer.start();
        consumer.start();
    }

}
