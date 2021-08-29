package com.concurrent.program.thread.base;

public class TestForContent {

    public static final int LARGE_NUMBER = 10240;

    /**
     * main over
     * with cache time: 163
     * no cache time: 1716
     * <p>
     * 两个线程只有 array[i][j] 和 array[j][i] 的区别
     * 说明 缓存与内存交换数据的单位就是缓存行
     */
    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            long[][] array = new long[LARGE_NUMBER][LARGE_NUMBER];
            long start = System.currentTimeMillis();
            for (int i = 0; i < LARGE_NUMBER; i++) {
                for (int j = 0; j < LARGE_NUMBER; j++) {
                    array[i][j] = i * 2 + j;
                }
            }
            long cost = System.currentTimeMillis() - start;
            System.out.println("with cache time: " + cost);
        }).start();

        new Thread(() -> {
            long[][] array = new long[LARGE_NUMBER][LARGE_NUMBER];
            long start = System.currentTimeMillis();
            for (int i = 0; i < LARGE_NUMBER; i++) {
                for (int j = 0; j < LARGE_NUMBER; j++) {
                    array[j][i] = i * 2 + j;
                }
            }
            long cost = System.currentTimeMillis() - start;
            System.out.println("no cache time: " + cost);
        }).start();

        System.out.println("main over");
    }

}
