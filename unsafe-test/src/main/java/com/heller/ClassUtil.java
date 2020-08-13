package com.heller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ClassUtil {

    public static final byte[] getClassBytes(String classFilePath) throws IOException {
        return getClassBytes(new File(classFilePath));
    }

    public static final byte[] getClassBytes(File classFile) throws IOException {
        FileInputStream input = new FileInputStream(classFile);
        byte[] content = new byte[(int)classFile.length()];
        input.read(content);
        input.close();
        return content;
    }

}
