package com.heller.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 最简单的 NIO SocketServer

 记住 需要设置服务端、客户端 SocketChannel 的模式为 非阻塞，否则 仍然是阻塞模式的 IO

 核心：
    ServerSocketChannel       ->    ServerSocket    代表 服务端 Socket 连接
    SocketChannel             ->    Socket          代表 客户端 Socket 连接

    ByteBuffer                ->    byte[]          读取数据
    (NIO 读取数据需要 Buffer  ，   对应  BIO 直接使用的 字节数组)


 因为不阻塞，所以就是死循环，CPU 空转
 */
public class SimpleNIOSocketServer {

    // 用于保存所有的客户端连接 SocketChannel
    private static final List<SocketChannel> channelList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9002));
        // 设置 ServerSocketChannel 为非阻塞模式，必须要设置这个才能非阻塞模式！！
        serverSocketChannel.configureBlocking(false);

        while (true) {
            // 非阻塞模式下 accept 方法不会阻塞，否则会阻塞
            // NIO 的非阻塞是操作系统内部实现的，底层调用了 Linux 内核的 accept 方法
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {// 如果有客户端连接
                System.out.println("新来一个连接：" + socketChannel.getRemoteAddress());
                // 将连接设置为非阻塞，必须要设置这个才能非阻塞模式！！
                socketChannel.configureBlocking(false);
                // 保存连接到 list 中
                channelList.add(socketChannel);
            }

            // 遍历所有的连接，查看有无数据
            Iterator<SocketChannel> iterator = channelList.iterator();
            while (iterator.hasNext()) {
                SocketChannel sc = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                // 非阻塞模式下 read 方法不会阻塞，否则会阻塞
                int len = sc.read(byteBuffer);
                // 有数据的话，打印出来
                if (len > 0) {
                    System.out.println("接收到消息：" + new String(byteBuffer.array(), 0, len));
                } else if (len == -1) {// 客户端断开连接
                    // 移除
                    iterator.remove();
                    System.out.println("客户端断开连接！移除掉" + sc.getRemoteAddress());
                }
            }
        }
    }

}
