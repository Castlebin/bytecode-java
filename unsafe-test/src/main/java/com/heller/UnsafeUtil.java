package com.heller;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeUtil {
    public static final Unsafe getUnsafe() {
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            return (Unsafe) theUnsafeField.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
