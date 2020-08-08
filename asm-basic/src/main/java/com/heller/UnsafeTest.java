package com.heller;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Arrays;

public class UnsafeTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // 利用反射获取到了 Unsafe 单例中的实例字段
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);

        int[] a = new int[20];
        int arrayBaseOffset = unsafe.arrayBaseOffset(int[].class);
        int arrayIndexScale = unsafe.arrayIndexScale(int[].class);
        for (int i = 0; i < a.length; i++) {
            unsafe.getAndSetInt(a, arrayBaseOffset + i * arrayIndexScale, i);
        }
        System.out.println(Arrays.toString(a));
    }
}
