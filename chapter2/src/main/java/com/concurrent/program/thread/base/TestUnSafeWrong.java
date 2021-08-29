package com.concurrent.program.thread.base;

import sun.misc.Unsafe;

/**
 * Created on 2020-08-29
 */
public class TestUnSafeWrong {

    // 获取Unsafe的实例（2.2.1）
    static final Unsafe unsafe = UnsafeUtils.getUnsafe();
    // 记录变量state在类TestUnSafe中的偏移值（2.2.2）
    static final long stateOffset;

    static {
        try {
            // 获取state变量在类TestUnSafe中的偏移值(2.2.4)
            stateOffset = unsafe.objectFieldOffset(TestUnSafeWrong.class.getDeclaredField("state"));
            System.out.println("stateOffset = " + stateOffset);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            throw new Error(ex);
        }
    }

    // 变量(2.2.3)
    private final long state = 0;

    /**
     * stateOffset = 16
     * true
     */
    public static void main(String[] args) {
        // 创建实例，并且设置state值为1(2.2.5)
        TestUnSafeWrong test = new TestUnSafeWrong();
        // (2.2.6)
        Boolean success = unsafe.compareAndSwapInt(test, stateOffset, 0, 1);
        System.out.println(success);
    }

}
