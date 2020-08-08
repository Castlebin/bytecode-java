package com.heller.rt.demo;

import java.lang.management.ManagementFactory;
import java.util.Date;

public class Base {
    public static void main(String[] args) {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        //打印当前Pid
        System.out.println("pid:" + pid);
        while (true) {
            try {
                Thread.sleep(5000L);
            } catch (Exception e) {
                break;
            }
            process();
        }
    }

    public static void process() {/*
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        System.out.println("pid: " + pid
               + ", time: "+ new Date() + "   process");*/
        System.out.println("process");
    }
}

/**
 * 希望在运行时能够动态的修改 class 类，并且使用
 */
