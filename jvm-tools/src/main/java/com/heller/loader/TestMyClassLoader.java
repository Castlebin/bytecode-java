package com.heller.loader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class TestMyClassLoader {
    public static void main(String[] args) throws IOException,
            ClassNotFoundException, IllegalAccessException,
            InstantiationException, NoSuchMethodException,
            InvocationTargetException, InterruptedException {
        // 获取类的路径，不用写死
        String rootPath = MyClassLoader.class.getResource("/")
                .getPath()
                .replaceAll("%20", " ");
        rootPath = new File(rootPath).getPath();

        while (true) {
            MyClassLoader loader = new MyClassLoader(
                    rootPath,
                    rootPath + "/com/heller/loader"
            );
            loader.loadClasses();

            Class<?> aClass = loader.loadClass("com.heller.loader.ClassForTest");
            Object o = aClass.newInstance();
            aClass.getMethod("test").invoke(o);
            Thread.sleep(3000);
        }

    }
}
