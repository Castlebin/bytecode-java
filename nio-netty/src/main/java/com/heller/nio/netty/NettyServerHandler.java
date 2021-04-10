package com.heller.nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 自定义Handler需要继承netty规定好的某个HandlerAdapter(规范)
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端发送过来的数据
     *
     * @param ctx 封装了 Channel
     * @param msg 封装了 客户端发送过来的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取数据的线程：" + Thread.currentThread().getName());
        /*
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();// 本质是一个双向队列，出栈入栈
        */
        ByteBuf buf = (ByteBuf) msg;
       System.out.println("收到客户端的消息：" + buf.toString(StandardCharsets.UTF_8) + "   (time: " + new Date() );

        // 实现一个简单的 echo
        ByteBuf echoBuf = Unpooled.copiedBuffer("echo: " + buf.toString(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8);
        ctx.writeAndFlush(echoBuf);
    }

    /**
     * 数据处理完毕后的回调方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // super.channelReadComplete(ctx);
        ByteBuf buf = Unpooled.copiedBuffer("continue:  \n\n\n", StandardCharsets.UTF_8);
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    //    super.channelUnregistered(ctx);
        System.out.println("channelUnregistered"    + "   (time: " + new Date() );
    }

    /**
     * 处理异常，一般是关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }

}
