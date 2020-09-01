package com.heller.loader;

/**
 * 利用全盘委托机制，实现热加载  （用自定义的类加载器，委托执行程序的入口）
 *
 * （SpringBoot 就是这么做的，想一下 SpringApplication.run() ）
 */
public class Application {

    public static void main(String[] args) throws Exception {
        MyHotDeployApplication.run(Application.class);
    }

}
