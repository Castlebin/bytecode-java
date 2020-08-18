package com.heller.util;

public class PrimeNumberGenerator {

    public static long findPrimeNumber(long nTh) {
        if (nTh < 1) {
            throw new IllegalArgumentException("nTh must bigger then ZERO!");
        }
        int n = 0;
        for (long num = 2; num < Long.MAX_VALUE; num++) {
            if (isPrimeNumber(num)) {
                n++;
                if (n == nTh) {
                    return num;
                }
            }
        }

        throw new RuntimeException("over long num range to find " + nTh + "th prime number");
    }

    public static boolean isPrimeNumber(long num) {
        if (num == 2 || num == 3) {
            return true;
        }

        if (num % 2 == 0) {
            return false;
        }

        for (long divisor = 3; divisor <= Math.sqrt(num); divisor+=2) {
            if (num % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 计算素数，可以用于模拟 长时间 任务
     * @param m 第 m 个素数
     */
    public static void runALongTimeJob(long m) {
        long start = System.currentTimeMillis();
        long prime = findPrimeNumber(m);
        long end = System.currentTimeMillis();
        System.out.println(m + " -> " + prime + ", time: " + (end - start) + " ms, thread: " + Thread.currentThread().getName());
    }

    public static void runALongTimeJob(long m, long n) {
        long start = System.currentTimeMillis();
        for (long nth = m; nth < n; nth++) {
            long prime = findPrimeNumber(nth);
            long end = System.currentTimeMillis();
            System.out.println(nth + " -> " + prime + ", time: " + (end - start) + " ms, thread: " + Thread.currentThread().getName());
            start = end;
        }
    }

}
