package com.heller.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeUtil {

    /**
     * 因为 Unsafe 限制了实例化，所以只能通过反射获取到静态实例
     * @return unsafe 实例
     */
    public static final Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
