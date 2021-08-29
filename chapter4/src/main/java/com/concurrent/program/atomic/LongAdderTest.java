package com.concurrent.program.atomic;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

public class LongAdderTest {

    public static final int THREAD_SIZE = 1000;
    private static final AtomicLong ATOMIC_LONG = new AtomicLong(0L);
    private static final LongAdder LONG_ADDER = new LongAdder();
    private static final int BITCH_SIZE = 1000000;

    /**
     * 证明：充分线程竞争情况下，LongAdder 的速度更快
     *
     * first run atomic
     * atomic long cost: 17547, value: 1000000000
     * second run adder
     * long adder long cost: 6054, value: 1000000000
     * main over
     *
     * first run atomic
     * atomic long cost: 20228, value: 1000000000
     * second run adder
     * long adder long cost: 3172, value: 1000000000
     * main over
     */
    public static void main(String[] args) throws InterruptedException {

        Thread atomic = new Thread(() -> {
            long start = System.currentTimeMillis();
            IntStream.range(0, THREAD_SIZE).parallel().forEach(x -> {
                        Thread thread = new Thread(() -> IntStream.range(0, BITCH_SIZE).parallel()
                                .forEach(y -> ATOMIC_LONG.getAndIncrement()));
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
            long cost = System.currentTimeMillis() - start;
            System.out.println("atomic long cost: " + cost + ", value: " + ATOMIC_LONG.get());
        });

        Thread adder = new Thread(() -> {
            long start = System.currentTimeMillis();
            IntStream.range(0, THREAD_SIZE).parallel().forEach(x -> {
                        Thread thread = new Thread(() -> IntStream.range(0, BITCH_SIZE).parallel()
                                .forEach(y -> LONG_ADDER.increment()));
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
            long cost = System.currentTimeMillis() - start;
            System.out.println("long adder long cost: " + cost + ", value: " + LONG_ADDER.longValue());
        });

        System.out.println("first run atomic");
        atomic.start();
        atomic.join();
        Thread.sleep(1000L);

        System.out.println("second run adder");
        adder.start();
        adder.join();
        Thread.sleep(1000L);

        System.out.println("main over");
    }

}
