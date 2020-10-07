package com.heller.jmm;

import org.openjdk.jol.info.ClassLayout;

/**
 * 使用 JOL （Java Object Layout）查看 Java 对象布局
 * （OpenJDK 提供的工具）
 */
public class HelloJOL {
    public static void main(String[] args) {
        Object object = new Object();
        System.out.println(ClassLayout.parseInstance(object).toPrintable());

        synchronized (object) {
            System.out.println(ClassLayout.parseInstance(object).toPrintable());
        }
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
    }
}
