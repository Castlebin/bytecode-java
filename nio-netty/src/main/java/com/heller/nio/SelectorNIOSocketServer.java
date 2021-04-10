package com.heller.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 使用 Selector 来注册连接，使用底层的 epoll 模型   ，   真正的 NIO 使用姿势

 采用事件机制，所以不会浪费 CPU，并且也不用轮询所有的连接

 （ 本程序弊端： 所有的任务都在主线程里处理，连接、读取数据、处理数据等，并发量必定上不去，所以，得引入线程池 ）

 */
public class SelectorNIOSocketServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9003));
        // 设置 ServerSocketChannel 为非阻塞模式，必须要设置这个才能非阻塞模式！！
        serverSocketChannel.configureBlocking(false);

        /** 打开 Selector 来处理 Channel ，即 创建 epoll  */
        Selector selector = Selector.open();
        /** 将 ServerSocketChannel 注册到 Selector 上，
            这个Selector 关注的是 ServerSocketChannel 上的 accept 事件  */
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            /** Selector 以    阻塞模式    等待  连接事件 的发生 */
            selector.select();

            /** 获取 selector 上注册的全部事件的 SelectKey */
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            // 遍历 SelectKey 对事件进行处理
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                /** 如果是连接事件 */
                if (key.isAcceptable()) {
                    /** 看见了没有，这个就是开始注册进来的那个 ServerSocketChannel ，也就是代表着 服务端 */
                    ServerSocketChannel serverChannel = (ServerSocketChannel)key.channel();
                    /** 获取来着客户端的连接 */
                    SocketChannel socketChannel = serverChannel.accept();
                    // 别忘了将连接模式设置为非阻塞
                    socketChannel.configureBlocking(false);

                    // 现阶段我们只关心 接收 客户端发送过来的数据，所以只注册 READ 事件，
                    // 如果要向客户端发送数据，还可以注册 Write 事件
                    /** 同样的 ，和 ServerSocketChannel 一样，注册到 Selector 上，由它进行事件监听 */
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("新的客户端连接进来了，" + socketChannel.getRemoteAddress());
                } else if (key.isReadable()) {
                    // 如果是 OP_READ 事件，读取数据并打印
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    int len = socketChannel.read(byteBuffer);
                    if (len > 0) {
                        System.out.println("接收到消息: " + new String(byteBuffer.array(), 0, len)
                                + ", (来自 " + socketChannel.getRemoteAddress() + ")");
                    } else if (len == -1) {// 客户端断开连接，需要关闭 Socket
                        socketChannel.close();
                        System.out.println("客户端断开连接");
                    }
                }
                /** 处理完毕这个时间，从集合中删除已经处理过的 事件 key，这样就不会重复处理了 */
                iterator.remove();
            }
        }
    }

}
