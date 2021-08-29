package com.concurrent.program.thread.base;

public class ThreadSafeCount {

    private static int value;

    /**
     * static synchronized void inc();
     *     Code:
     *        0: getstatic     #2                  // Field value:I
     *        3: iconst_1
     *        4: iadd
     *        5: putstatic     #2                  // Field value:I
     *        8: return
     */
    static synchronized void inc() {
        ++value;
    }

    public static void main(String[] args) {
        inc();
        System.out.println(value);
    }

}
