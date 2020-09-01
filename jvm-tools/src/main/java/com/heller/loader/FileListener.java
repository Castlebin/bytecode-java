package com.heller.loader;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;

public class FileListener extends FileAlterationListenerAdaptor {

    @Override
    public void onFileChange(File file) {
        if (file.getName().endsWith(".class")) {
            try {
                /**
                 * 热部署，重启项目
                 */
                MyHotDeployApplication.close();

                MyClassLoader myClassLoader =
                        new MyClassLoader(MyHotDeployApplication.rootPath,
                                MyHotDeployApplication.rootPath + "/com");

                MyHotDeployApplication.start0(myClassLoader);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
