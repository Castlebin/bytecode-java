package com.heller.dump;

import java.util.ArrayList;
import java.util.List;

/**
    运行环境 java 8                                                 <br/>
    Java 运行参数：                                                  <br/>

    -Xms10m -Xmx10m
    -XX:+PrintGCDetails                         输出详细GC日志        <br/>
    -Xloggc:/Users/heller/tmp/oom_gc.log        输出GC日志到文件       <br/>
    -XX:+PrintGCDateStamps                      输出GC的时间戳（以日期的形式，如 2013-05-04T21:53:59.234+0800） <br/>
    -XX:+PrintHeapAtGC                          在进行GC的前后打印出堆的信息     <br/>
    -XX:+PrintReferenceGC                       打印年轻代各个引用的数量以及时长        <br/>
    -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/heller/tmp/oom.dump     <br/>
 */

public class OOMTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(new String(i+""));
        }
    }
}
