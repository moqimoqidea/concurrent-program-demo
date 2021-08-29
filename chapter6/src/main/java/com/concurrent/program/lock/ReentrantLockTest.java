package com.concurrent.program.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    /**
     * main over.
     * begin await
     * begin signal, and sleep 3 seconds.
     * end signal
     * end await
     */
    public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();


        Thread awaitThread = new Thread(() -> {
            lock.lock();

            try {
                System.out.println("begin await");
                condition.await();
                System.out.println("end await");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread signalThread = new Thread(() -> {
            lock.lock();

            try {
                System.out.println("begin signal, and sleep 3 seconds.");
                Thread.sleep(3000L);
                condition.signal();
                System.out.println("end signal");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        awaitThread.start();
        signalThread.start();

        System.out.println("main over.");
    }

}
