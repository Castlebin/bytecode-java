package com.heller;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Arrays;

public class UnsafeDemo1 {
    public static void main(String[] args) {
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            Unsafe unsafe = (Unsafe) theUnsafeField.get(null);

            // 利用 Unsafe 获取到数组元素的内存地址并直接进行赋值
            int[] arr = new int[10];
            int arrayBaseOffset = unsafe.arrayBaseOffset(arr.getClass());
            int arrayIndexScale = unsafe.arrayIndexScale(arr.getClass());
            for (int i = 0; i < arr.length; i++) {
                unsafe.getAndSetInt(arr,
                        // 获取到数组元素的实际内存地址
                        arrayBaseOffset + i * arrayIndexScale,
                        i);
            }
            System.out.println(Arrays.toString(arr));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
