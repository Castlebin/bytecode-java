package com.heller.jmm;

/**
 * http://hollischuang.gitee.io/tobetopjavaer/#/basement/jvm/stack-alloc

   关闭逃逸分析（默认是开启的），用 jmap -histo xxx 查看堆中对象分配个数：
     -Xmx100M -Xms100M -XX:-DoEscapeAnalysis -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError

   开启逃逸分析，正常跑，用 jmap -histo xxx 查看堆中对象分配个数对比：
     -Xmx100M -Xms100M -XX:+DoEscapeAnalysis -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 */
public class StackAllocation {
    public static void main(String[] args) {
        long a1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            alloc();
        }
        // 查看执行时间
        long a2 = System.currentTimeMillis();
        System.out.println("cost " + (a2 - a1) + " ms");
        // 为了方便查看堆内存中对象个数，线程sleep
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private static void alloc() {
        User user = new User();
    }

    static class User {

    }
}
