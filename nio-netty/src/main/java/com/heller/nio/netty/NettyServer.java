package com.heller.nio.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty 在原生的 Java NIO 基础上做了大量的封装和优化，引入 Reactor 模型，
 * 并且使用的是 Multi-threaded Reactor 模型，能支持的连接和并发会更高
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        // 创建两个线程组 bossGroup 和 workerGroup, 含有的子线程NioEventLoop的个数默认为cpu核数的两倍
        // bossGroup 只是处理连接请求 , 真正的和客户端业务处理，会交给 workerGroup 完成
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);    // parentGroup
        EventLoopGroup workerGroup = new NioEventLoopGroup();   // childGroup

        try {
            // 创建服务器的启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    // 使用 NioServerSocketChannel 作为服务端的 Channel 实现
                    .channel(NioServerSocketChannel.class)
                    // 初始化服务端连接队列的大小，服务端处理客户端进来的连接时顺序处理的，所有同一时间只能处理一个客户端连接
                    // 多个客户端连接进来的时候，服务端将暂不能处理的连接请求放在队列中等待处理
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 创建通道初始化对象，设置初始化参数
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 对 workGroup (childGroup) 的 SocketChannel 设置处理器
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });

            System.out.println("netty server start...");

            // 绑定一个端口并且同步, 生成了一个ChannelFuture异步对象，通过isDone()等方法可以判断异步事件的执行情况
            // 启动服务器(并绑定端口)，bind是异步操作，sync方法是等待异步操作执行完毕
            ChannelFuture cf = bootstrap.bind(9000).sync();
            // 可以 给cf注册监听器，监听我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()) {
                        System.out.println("监听端口9000成功");
                    } else {
                        System.out.println("监听端口9000失败");
                    }
                }
            });

            // 对通道关闭进行监听，closeFuture是异步操作，监听通道关闭
            // 通过sync方法同步等待通道关闭处理完毕，这里会阻塞等待通道关闭完成
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
