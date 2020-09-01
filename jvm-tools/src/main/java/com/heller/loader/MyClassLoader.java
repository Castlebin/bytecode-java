package com.heller.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyClassLoader extends ClassLoader {

    private String rootPath;
    private List<String> classPaths = new ArrayList<>();
    private List<String> clazzs = new ArrayList<>();

    /**
     * 定义一个自己的类加载器，用于加载指定路径下的class文件
     *
     * @param rootPath   指定路径
     * @param classPaths 类路径
     */
    public MyClassLoader(String rootPath, String... classPaths) throws IOException {
        this.rootPath = rootPath;
        for (String classPath : classPaths) {
            this.classPaths.add(classPath);
            loadClassPath(new File(classPath));
        }
    }

    /**
     * @see ClassLoader.findLoadedClass()
     * @param file
     * @throws IOException
     */
    private void loadClassPath(File file) throws IOException {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                loadClassPath(f);
            }
        } else {
            String filename = file.getName();
            if (filename.endsWith(".class")) {
                String filePath = file.getPath();
                InputStream inputStream = new FileInputStream(file);
                byte[] bytes = new byte[(int) file.length()];
                inputStream.read(bytes);
                String className = filePathToClassName(filePath);
                clazzs.add(className);
                // 这里 defineClass 了，因此，loadClass时，
                // 调用 findLoadedClass ，能够得到 Class，所以无需重载 loadClass 方法
                defineClass(className, bytes, 0, bytes.length);
            }
        }
    }

    /**
     * 重载 loadClass 方法
     * @param
     * @return
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass == null) {
            // 自定义的类加载器，只加载指定路径下的类，其余，交给系统类加载器
            if (!clazzs.contains(name)) {
                loadedClass = getSystemClassLoader().loadClass(name);
            } else {
                throw new ClassNotFoundException(name);
            }
        }
        return loadedClass;
    }

    private String filePathToClassName(String filePath) {
        String className = filePath.replace(rootPath, "")
                .replaceAll("/", ".");
        className = className.substring(0, className.lastIndexOf("."));
        className = className.substring(1);
        return className;
    }

}
