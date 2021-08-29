package com.concurrent.program.thread.base;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Get unsafe instance.
 *
 * @see <a href="baeldung java unsafe">https://www.baeldung.com/java-unsafe</a>
 * @see <a href="UnsafeUtils">https://stackoverflow.com/a/59605217</a>
 */
public final class UnsafeUtils {

    private static Unsafe unsafe;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private UnsafeUtils() {
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }
}