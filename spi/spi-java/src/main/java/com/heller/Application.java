package com.heller;

import com.heller.api.UploadCDN;

import java.util.ServiceLoader;

public class Application {
    public static void main(String[] args) {
        /**
         * ServiceLoader 是实现 SPI 接口与实现类加载的关键，内部有一个自定义的懒加载迭代器（LazyIterator）
         * ServiceLoader 本身也实现了迭代器接口，所以能够使用for循环进行迭代
         *
         * 它固定读取 META-INF/services/ 目录下的文件，文件名固定为要加载的接口的名字全称，文件中每一行为对应的实现类的名字
         *
         * ServiceLoader 在利用迭代器迭代时，创建接口实现类的对象并且缓存下来
         */
        ServiceLoader<UploadCDN> uploadServices = ServiceLoader.load(UploadCDN.class);
        for (UploadCDN cdn : uploadServices) {
            cdn.upload("c://xx/xx.jpg");
        }
    }
}
