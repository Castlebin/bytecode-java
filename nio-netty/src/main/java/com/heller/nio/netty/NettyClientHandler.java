package com.heller.nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当客户端连接服务器完成后触发该方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //  super.channelActive(ctx);
        ByteBuf buf = Unpooled.copiedBuffer("Hello, My Netty Server", StandardCharsets.UTF_8);
        ctx.writeAndFlush(buf);
    }

    /**
     * 当通道读取到数据时，会触发该方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("收到服务端的消息：" + buf.toString(StandardCharsets.UTF_8) + "   (time: " + new Date() );
        System.out.println("服务器的地址是：" + ctx.channel().remoteAddress());
    }

    /**
     * 处理异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
