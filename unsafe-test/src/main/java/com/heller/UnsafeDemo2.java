package com.heller;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeDemo2 {
    public static void main(String[] args) {
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            Unsafe unsafe = (Unsafe) theUnsafeField.get(null);

            A a = new A();
            System.out.println(a.getA());   // 1

            // 反射，还是会执行初始化
            a = A.class.newInstance();
            System.out.println(a.getA());   // 1

            // Unsafe 直接分配内存，生成对象，不执行对象的初始化
            A o = (A)unsafe.allocateInstance(A.class);
            System.out.println(o.getA());   // 0
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
