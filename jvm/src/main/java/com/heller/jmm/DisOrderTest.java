package com.heller.jmm;

/**
 * 证明指令重排序的存在
 *
 * 如果没有指令重排序，那么 x == 0 && y == 0 的情况一定不存在，程序永远不会结束
 *
 * 程序运行时间可能会比较长才结束
 *
 * 某次执行结果：
 *  执行到第 527222 次，得到 x == 0 && y == 0
 */
public class DisOrderTest {

    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; ; i++) {
            x = 0; y = 0;
            a = 0; b = 0;

            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    x = b;
                }
            });

            Thread other = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                }
            });
            one.start();other.start();
            one.join();other.join();
            if (x == 0 && y == 0) {
                System.out.println("执行到第 " + i + " 次，得到 x == 0 && y == 0");
                break;
            }
        }
    }

}
