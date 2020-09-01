package com.heller.loader;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 利用全盘委托机制，实现热加载  （用自定义的类加载器，委托执行程序的入口）
 *
 * （SpringBoot 就是这么做的，想一下 SpringApplication.run() ）
 */
public class MyHotDeployApplication {

    public static String rootPath;

    public static void init() {
        System.out.println("初始化项目...");
    }

    public static void start() {
        init();
        new ClassForTest().test();
    }

    public static void run(Class<?> clazz) throws Exception {
        String rootPath = clazz.getResource("/").getPath().replaceAll("%20", "");
        rootPath = new File(rootPath).getPath();
        MyHotDeployApplication.rootPath = rootPath;
        startFileMonitor(rootPath);
        MyClassLoader myClassLoader =
                new MyClassLoader(rootPath, rootPath + "/com");
        start0(myClassLoader);
    }

    /**
     * 设置一个文件目录的监听器并启动监听
     * @param rootPath 监听目录
     * @throws Exception
     */
    private static void startFileMonitor(String rootPath) throws Exception {
        FileAlterationObserver observer = new FileAlterationObserver(rootPath);
        observer.addListener(new FileListener());
        FileAlterationMonitor monitor = new FileAlterationMonitor(1000);
        monitor.addObserver(observer);
        monitor.start();
    }

    public static void start0(MyClassLoader myClassLoader) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> aClass = myClassLoader.loadClass("com.heller.loader.MyHotDeployApplication");
        Object o = aClass.newInstance();
        Method method = aClass.getMethod("start");
        method.invoke(o);
    }

    public static void close() {
        System.out.println("关闭项目...");
        System.runFinalization();
        System.gc();
    }

}
