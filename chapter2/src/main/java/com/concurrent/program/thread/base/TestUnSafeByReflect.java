package com.concurrent.program.thread.base;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created on 2020-08-29
 */
public class TestUnSafeByReflect {

    static final Unsafe unsafe;

    static final long stateOffset;

    static {

        try {
            // 反射获取Unsafe的成员变量theUnsafe
            Field field = Unsafe.class.getDeclaredField("theUnsafe");

            // 设置为可存取
            field.setAccessible(true);

            // 获取该变量的值
            unsafe = (Unsafe) field.get(null);

            // 获取state在TestUnSafe中的偏移量
            stateOffset = unsafe.objectFieldOffset(TestUnSafeByReflect.class.getDeclaredField("state"));
            System.out.println("stateOffset = " + stateOffset);
        } catch (Exception ex) {

            System.out.println(ex.getLocalizedMessage());
            throw new Error(ex);
        }

    }

    private final long state = 0;

    /**
     * stateOffset = 16
     * true
     */
    public static void main(String[] args) {
        TestUnSafeByReflect test = new TestUnSafeByReflect();
        Boolean success = unsafe.compareAndSwapInt(test, stateOffset, 0, 1);
        System.out.println(success);
    }

}
